package ru.usefulcity.Controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.usefulcity.BotSettings;
import ru.usefulcity.Log;
import ru.usefulcity.Model.Menu;

import java.util.HashMap;
import java.util.Map;

import static ru.usefulcity.Controller.Constants.*;

/**
 * Created on 04/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class InfoBot extends TelegramLongPollingBot {
    private final Menu menu;
    private Map<Long, Dialog> dialogList = new HashMap<>();

    public InfoBot(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void onUpdateReceived(Update update) {
        /**
         * All bot functions processed here.
         * Process callback query with selected menu item.
         */
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long msgId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            Log.out("here callback message ",callbackData);
            Dialog dialog = dialogList.get(chatId);
            dialog.processItem(callbackData);
            sendMessage(chatId, msgId, dialog.getEditMessage(), dialog.getMenuItems());


        } else if (update.hasMessage() && update.getMessage().hasText()) {
            /**
             * If you went here - you at first time meet bot or you send wrong command
             * If at first time - we store your chat id and create <code>Dialog</code>
             * instance for you.
             */
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String user = update.getMessage().getChat().getUserName(); //this only for logger

            if (!dialogList.containsKey(chatId) || "/start".equals(messageText)) {
                Dialog dialog = new Dialog(menu);
                dialogList.put(chatId, dialog);
                sendMessage(chatId, MAIN_MENU, dialog.getMenuItems());
                return;
            }

            sendMessage(chatId, WRONG_INPUT);
            sendMessage(chatId, MAIN_MENU, dialogList.get(chatId).getMenuItems());
        }
    }


    /**
     * Here we send update menu according to user choice
     *
     * @param editText - submenu or root menu
     */
    private void sendMessage(long chatId, long message_id, EditMessageText editText, InlineKeyboardMarkup inlineKeyboard) {
        editText
                .setReplyMarkup(inlineKeyboard)
                .setChatId(chatId)
                .setMessageId((int) message_id)
                .setText(" - ");
        try {
            execute(editText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(long chatId, String msg, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(msg)
                .setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(long chatId, String msg) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(msg);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }


    @Override
    public String getBotUsername() {
        return BotSettings.BOT_USERNAME;
    }
}
