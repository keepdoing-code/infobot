package ru.usefulcity.Helpers;

import ru.usefulcity.DAO.ConnectionFacade;
import ru.usefulcity.DAO.MenuDAO;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import static ru.usefulcity.DAO.SQLQueries.*;

/**
 * Created on 08/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class DBtest {
    public static void main(String[] args) {
        writeTest2();
    }

    public static void writeTest2() {
        ConnectionFacade connectionFacade = new ConnectionFacade("jdbc:sqlite:infobot.db");
        MenuDAO menuDAO = new MenuDAO();
        menuDAO.init(connectionFacade);
        menuDAO.saveMenu(createMenu());

        Menu newMenu = menuDAO.loadMenu();
        System.out.println("\r\n" + printAllMenu(newMenu, ""));


    }

    public static void writeTestDB() {
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
        menuDAO.addSubmenu("Fourth sub menu", 4);

        menuDAO.debugPrint(connectionFacade.getMany(GET_ALL_MENU));
        menuDAO.debugPrint(connectionFacade.getMany(DISTINCT_PARENT));
    }


    public static Menu createMenu() {
        Card card1 = new Card("Yuri").
                addText("Coder").
                addText("+7(911)500-00-00");

        Card card2 = new Card("Ivan").
                addText("Builder").
                addText("+7(911)500-00-00").
                addText("https://vk.com/stritron");

        Card card3 = new Card("Sergei").
                addText("Engineer").
                addText("+7(911)500-00-00").
                addText("https://vk.com/stritron");

        Card card4 = new Card("Alex").
                addText("Economist").
                addText("+7(911)500-00-00").
                addText("https://vk.com/stritron");

        Menu itemCoder = new Menu("Coder",card1);
        Menu itemBuilder = new Menu("Builder",card2);
        Menu itemEngin = new Menu("Engineer",card3);
        Menu itemEcon = new Menu("Economist",card4);

        Menu economistsMenu = new Menu("Economists");
        Menu engineerMenu = new Menu("Engineers");
        Menu otherSpec = new Menu("Other");
        Menu rootMenu = new Menu("Specialists");
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

    public static String printAllMenu(Menu menu, String tab) {
        StringBuilder sb = new StringBuilder();

        if (!menu.haveParent()) {
            sb
                    .append(menu.getId())
                    .append(":")
                    .append("\t")
                    .append(menu.getName())
                    .append("\r\n");
        }

        for (Menu m : menu) {
            sb
                    .append(tab)
                    .append(m.getId())
                    .append(":")
                    .append("\t")
                    .append(m.getName())
                    .append("\r\n");
            if (m.haveCard()) {
                for(Card c: m.getCards()) {
                    sb
                            .append(tab)
                            .append("\t")
                            .append(c.getName())
                            .append("\r\n");
                }
            } else {
                sb.append(printAllMenu(m, tab + '\t'));
            }
        }
        return sb.toString();
    }
}
