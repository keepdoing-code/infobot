package ru.usefulcity.BotTempale;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.usefulcity.BotSettings;
import ru.usefulcity.Log;

/**
 * Created by yuri on 22.11.18.
 */
public class Echobot extends TelegramLongPollingBot {
    private static final String BOT_USERNAME = BotSettings.BOT_USERNAME;
    private static final String BOT_TOKEN = BotSettings.BOT_TOKEN;

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String user = update.getMessage().getChat().getUserName();
            Log.out(user, messageText);

            SendMessage sendMessage = new SendMessage()
                    .setChatId(chatId)
                    .setText(new StringBuilder().append("You wrote: ").append(messageText).toString());

            try {
                execute(sendMessage);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }
}
