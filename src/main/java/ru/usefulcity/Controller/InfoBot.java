package ru.usefulcity.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.usefulcity.BotSettings;
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
    Logger log = LoggerFactory.getLogger(InfoBot.class);
    private final Menu menu;
    private Map<Long, Dialog> dialogList = new HashMap<>();

    public InfoBot(Menu menu) {
        this.menu = menu;
    }


    /**
     * All bot functions processed here.
     * Process callback query with selected menu item.
     */
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long msgId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            Dialog dialog = dialogList.get(chatId);


            if (dialog == null) {
                dialog = newDialog(chatId);
            }

            dialog.processItem(callbackData);
            EditMessageText editMessageText = dialog.getEditMessage();
            InlineKeyboardMarkup inlineKeyboardMarkup = dialog.getMenuItems();
            sendMessage(chatId, msgId, editMessageText, inlineKeyboardMarkup);

        } else if (update.hasMessage() && update.getMessage().hasText()) {
            /**
             * If you went here - you at first time meet bot or you send wrong command
             * If at first time - we store your chat id and create <code>Dialog</code>
             * instance for you.
             */
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String user = update.getMessage().getChat().getUserName(); //this only for logger
            log.info("User: {}", user);

            if (!dialogList.containsKey(chatId) || "/start".equals(messageText)) {
                newDialog(chatId);
                return;
            }

            sendMessage(chatId, WRONG_INPUT);
            sendMessage(chatId, MAIN_MENU, dialogList.get(chatId).getMenuItems());
        }
    }

    /**
     * If new user connected we create Dialog instance to store his position in menu.
     * First of all we send him Main menu.
     * @param chatId - new user id
     */
    private Dialog newDialog(long chatId) {
        Dialog dialog = new Dialog(menu);
        dialogList.put(chatId, dialog);
        sendMessage(chatId, MAIN_MENU, dialog.getMenuItems());
        return dialog;
    }


    /**
     * Here we send updated menu according to user choice of previous menu item
     *
     * @param chatId - Concrete chat with user
     * @param message_id - message to update
     * @param editText - new text to update message
     * @param inlineKeyboard - submenu or parent menu according selected item
     */
    private void sendMessage(long chatId, long message_id, EditMessageText editText, InlineKeyboardMarkup inlineKeyboard) {
        editText
                .setReplyMarkup(inlineKeyboard)
                .setChatId(chatId)
                .setMessageId((int) message_id);
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

    /**
     * Standard method for this Telegram bot API that uses bot token
     *
     * @return
     */
    @Override
    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }


    /**
     * Standard method for this Telegram bot API that uses bot username
     *
     * @return
     */
    @Override
    public String getBotUsername() {
        return BotSettings.BOT_USERNAME;
    }
}
