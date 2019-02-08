package ru.usefulcity.Helpers;

import ru.usefulcity.DAO.ConnectionFacade;
import ru.usefulcity.DAO.MenuDAO;

import static ru.usefulcity.DAO.SQLQueries.*;

/**
 * Created on 08/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class DBtest {
    public static void main(String[] args) {

    }

    public static void writeTestDB() {
        ConnectionFacade connectionFacade = new ConnectionFacade("jdbc:sqlite:infobot.db");
        MenuDAO menuDAO = new MenuDAO();
        menuDAO.init(connectionFacade);
        menuDAO.dropTables();

        menuDAO.addItem("Main menu", 0);
        menuDAO.addItem("First menu", 1);
        menuDAO.addItem("Second menu", 1);
        menuDAO.addItem("Third menu", 1);
        menuDAO.addItem("First sub menu", 2);
        menuDAO.addItem("Second First sub menu", 2);
        menuDAO.addItem("Fourth sub menu", 4);
        menuDAO.addItem("Fourth sub menu", 4);

        menuDAO.debugPrint(connectionFacade.getMany(GET_ALL_MENU));
        menuDAO.debugPrint(connectionFacade.getMany(DISTINCT_PARENT));
    }
}
