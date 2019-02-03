package ru.usefulcity.Controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.usefulcity.BotSettings;
import ru.usefulcity.Model.Menu;
import ru.usefulcity.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManBot extends TelegramLongPollingBot {
    private Menu menu;
    private Map<Long, MenuWrapper> dialogList = new HashMap<>();


    public DBManBot(Menu menu) {
        this.menu = menu;
    }


    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            MenuWrapper menuWrapper = dialogList.get(chat_id);

            if(menuWrapper.isNumber(call_data)){

            } else {

            }

            if (call_data.equals("key")) {
                String answer = "Updated message text";
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId((int)message_id)
                        .setText(answer)
                        .setReplyMarkup(null);
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String user = update.getMessage().getChat().getUserName();

            Log.out("Received: ", messageText, " From: ", user);



            if (isNewChat(chatId) || "/start".equals(messageText)) {
                startChat(chatId);
                sendInlineKeyboard(chatId);
                return;
            }

            String answer = dialogList.get(chatId).dialog(messageText);
            sendMessage(chatId, answer);
        }
    }





    private void sendInlineKeyboard(final long chatId) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText("Here is your keyboard");


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        Menu menu = dialogList.get(chatId).getMenuPointer();

        for(Menu m: menu){
            List<InlineKeyboardButton> colInline = new ArrayList<>();
            colInline.add(new InlineKeyboardButton().setText(m.getName()).setCallbackData(m.getId()));
            rowsInline.add(colInline);
        }

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            execute(message);
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


    private boolean isNewChat(long chatId) {
        return !dialogList.containsKey(chatId);
    }


    private void startChat(final long chatId) {
        dialogList.put(chatId, new MenuWrapper(this.menu, chatId));
        //sendMessage(chatId, MenuWrapper.printMenu(this.menu));
    }


    private void sendReplyKeyboard(final long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add("Button 1");
        row1.add("Button 2");
        row2.add("Button 3");
        row2.add("Button 4");
        keyboard.add(row1);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText("Here is your keyboard");
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
