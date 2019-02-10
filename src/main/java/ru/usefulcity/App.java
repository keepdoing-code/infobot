package ru.usefulcity;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.usefulcity.Controller.InfoBot;
import ru.usefulcity.DAO.ConnectionFacade;
import ru.usefulcity.DAO.MenuDAO;
import ru.usefulcity.Model.Menu;

public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        MenuDAO menuDAO = new MenuDAO();
        menuDAO.init(new ConnectionFacade());
        Menu menu = menuDAO.loadMenu();

        try {
            botsApi.registerBot(new InfoBot(menu));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Log.out("Bot successfully started");
    }
}
