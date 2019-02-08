package ru.usefulcity;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import ru.usefulcity.Controller.InfoBot;
import ru.usefulcity.DAO.SQLQueries;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;
import ru.usefulcity.Model.MenuLoader;

public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        Menu menu = MenuLoader.createMenu();
        System.out.println(MenuLoader.printAllMenu(menu, "\t"));

//        try {
//            botsApi.registerBot(new InfoBot(menu));
//
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        Log.out("Bot successfully started");
    }
}
