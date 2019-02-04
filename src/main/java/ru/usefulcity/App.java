package ru.usefulcity;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.usefulcity.Controller.InfoBot;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

public class App 
{
    public static void main( String[] args )
    {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try{
            botsApi.registerBot(new InfoBot(createMenu()));

        }catch (TelegramApiException e){
            e.printStackTrace();
        }
        Log.out("Bot successfully started");
    }

    //TODO SQLite loader
    public static Menu createMenu(){
        Card card1 = new Card().
                add(Card.Field.name , "Yuri").
                add(Card.Field.desc , "Coder").
                add(Card.Field.phone, "+7(911)500-00-00");

        Card card2 = new Card().
                add(Card.Field.name , "Ivan").
                add(Card.Field.desc , "Builder").
                add(Card.Field.phone, "+7(911)500-00-00").
                add(Card.Field.link , "https://vk.com/stritron");

        Card card3 = new Card().
                add(Card.Field.name , "Sergei").
                add(Card.Field.desc , "Engineer").
                add(Card.Field.phone, "+7(911)500-00-00").
                add(Card.Field.link , "https://vk.com/stritron");

        Card card4 = new Card().
                add(Card.Field.name , "Alex").
                add(Card.Field.desc , "Economist").
                add(Card.Field.phone, "+7(911)500-00-00").
                add(Card.Field.link , "https://vk.com/stritron");

        Menu itemCoder   = new Menu(card1);
        Menu itemBuilder = new Menu(card2);
        Menu itemEngin   = new Menu(card3);
        Menu itemEcon    = new Menu(card4);

        Menu economistsMenu = new Menu("Economists");
        Menu engineerMenu   = new Menu("Engineers");
        Menu otherSpec      = new Menu("Other");
        Menu rootMenu       = new Menu("Specialists");

        rootMenu.addSubMenu(economistsMenu);
        rootMenu.addSubMenu(engineerMenu);
        rootMenu.addSubMenu(otherSpec);

        economistsMenu.addItem(itemEcon);
        engineerMenu.addItem(itemEngin);
        otherSpec.addItem(itemCoder);
        otherSpec.addItem(itemBuilder);

        return rootMenu;
    }
}
