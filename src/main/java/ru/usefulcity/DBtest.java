package ru.usefulcity;

import ru.usefulcity.DAO.ConnectionFacade;
import ru.usefulcity.DAO.MenuDAO;
import ru.usefulcity.DAO.SQLQueries;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import java.util.Arrays;

import static ru.usefulcity.DAO.SQLQueries.*;

/**
 * Created on 08/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class DBtest {
    public static void main(String[] args) {
        ConnectionFacade connectionFacade = new ConnectionFacade("jdbc:sqlite:infobot.db");
        MenuDAO menuDAO = new MenuDAO();
        menuDAO.init(connectionFacade);
        menuDAO.saveMenu(createTestMenu());
//        writeTestData();

        Menu newMenu = menuDAO.loadMenu();

        System.out.println("\r\n" + newMenu.toString() + "\r\n");
        menuDAO.debugPrint(connectionFacade.getMany(GET_ALL_MENU));
    }


    public static void writeTestData() {
        ConnectionFacade connectionFacade = new ConnectionFacade("jdbc:sqlite:infobot.db");
        MenuDAO menuDAO = new MenuDAO();
        menuDAO.init(connectionFacade);
        menuDAO.dropTables();
        menuDAO.addSubmenu("Main menu", 0);
        menuDAO.addSubmenu("First menu", 1);
        menuDAO.addSubmenu("Second menu", 1);
        menuDAO.addSubmenu("Third menu", 1);
        menuDAO.addSubmenu("First sub menu", 2);
        menuDAO.addSubmenu("Second First sub menu", 2);
        menuDAO.addSubmenu("Fourth sub menu", 4);
        int subId = menuDAO.addSubmenu("Fourth sub menu", 4);
        Card card = new Card("Test card").addText("This is test card");
        menuDAO.addCards(Arrays.asList(card), subId);

//        menuDAO.debugPrint(connectionFacade.getMany(GET_ALL_MENU));
//        menuDAO.debugPrint(connectionFacade.getMany(DISTINCT_PARENT));
    }


    public static Menu createTestMenu() {
        Card card1 = new Card("Yuri").
                addText("Coder").
                addText("+7(911)500-43-65").
                addText("https://vk.com/g78uyv");

        Card card2 = new Card("Ivan").
                addText("Builder").
                addText("+7(911)235-12-54").
                addText("https://vk.com/56487t87ighj");

        Card card3 = new Card("Sergei").
                addText("Engineer").
                addText("+7(911)452-00-54").
                addText("https://vk.com/76giuyfut");

        Card card4 = new Card("Alex").
                addText("Economist").
                addText("+7(911)987-23-00").
                addText("https://vk.com/yuhbkuyg");

        Menu itemCoder = new Menu("Coder", card1);
        Menu itemBuilder = new Menu("Builder", card2);
        Menu itemEngin = new Menu("Engineer", card3);
        Menu itemEcon = new Menu("Economist", card4);

        Menu rootMenu = new Menu("Specialists");
        Menu economistsMenu = new Menu("Economists");
        Menu engineerMenu = new Menu("Engineers");
        Menu otherSpec = new Menu("Other");
        Menu utilsMenu = new Menu("Utils");
        Menu sysUtilsMenu = new Menu("Sysutils");

        rootMenu.addSubmenu(economistsMenu);
        rootMenu.addSubmenu(engineerMenu);
        rootMenu.addSubmenu(otherSpec);
        rootMenu.addSubmenu(utilsMenu);

        utilsMenu.addSubmenu(sysUtilsMenu);
        economistsMenu.addSubmenu(itemEcon);
        engineerMenu.addSubmenu(itemEngin);
        otherSpec.addSubmenu(itemCoder);
        otherSpec.addSubmenu(itemBuilder);

        return rootMenu;
    }
}
