package ru.usefulcity.DAO;

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
        DBFacade dbFacade = new DBFacade("jdbc:sqlite:infobot.db");
        MenuDAO menuDAO = new MenuDAO();
        menuDAO.init(dbFacade);
        menuDAO.createMenu("Main menu", new Menu("Main menu", 0));
//        dbFacade.insertOneGetId(ADD_SUBMENU, "Submenu", 0);
        int i = dbFacade.insertOneGetId(ADD_SUBMENU, "Submenu", 10);
        System.out.println(i);
        menuDAO.debugPrint(dbFacade.getMany(GET_ALL_MENU));
        menuDAO.debugPrint(dbFacade.getMany(DISTINCT_PARENT));
        menuDAO.debugPrint(dbFacade.getManyPrepared(GET_CHILDREN, 10));

    }
}
