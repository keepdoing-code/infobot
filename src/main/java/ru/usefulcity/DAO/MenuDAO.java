package ru.usefulcity.DAO;

import ru.usefulcity.DAO.Interface.IDBFacade;
import ru.usefulcity.DAO.Interface.IMenuDAO;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static ru.usefulcity.Controller.Constants.*;
import static ru.usefulcity.DAO.SQLQueries.*;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class MenuDAO implements IMenuDAO {
    private IDBFacade dbFacade = null;

    @Override
    protected void finalize() throws Throwable {
        dbFacade.closeConnection();
    }

    @Override
    public void init(IDBFacade idbFacade) {
        try {
            this.dbFacade = idbFacade;
            this.dbFacade.initConnection();
            this.dbFacade.execUpdate(CREATE_TABLES_QUERY);
            this.dbFacade.exec(PRAGMA_FOREIGN_KEYS_ON);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createMenu(String name, Menu parent) {
        dbFacade.execPrepared(ADD_SUBMENU, name, parent.getId());
    }

    @Override
    public void addItem(String name, Menu menu) {
        dbFacade.execPrepared(ADD_SUBMENU, name, menu);
    }

    @Override
    public void addCard(Card card, Menu item) {

    }

    @Override
    public void addItem(String name, int parentId) {

    }

    @Override
    public void deleteCard(Card card) {

    }

    @Override
    public Menu getMenu() {
        /**
         * First of all get descent list of parent id's
         * for each parent id get child items
         * create every parent and recursively add children
         *
         */

        Menu rootMenu = new Menu(MAIN_MENU, Integer.valueOf(MAIN_MENU_ID));


        Menu readMenu = new Menu("");
        rootMenu.addSubmenu(null);

        return null;
    }

    @Override
    public void updateItem(Menu menuItem) {

    }

    @Override
    public void deleteItem(Menu menuItem) {

    }


    public void debugPrint(List<Object[]> list) {
        System.out.println();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }


}
