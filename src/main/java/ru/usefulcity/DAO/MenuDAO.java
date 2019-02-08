package ru.usefulcity.DAO;

import ru.usefulcity.DAO.Interface.IConnectionFacade;
import ru.usefulcity.DAO.Interface.IMenuDAO;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private IConnectionFacade connectionFacade = null;

    @Override
    protected void finalize() throws Throwable {
        connectionFacade.closeConnection();
    }

    @Override
    public void init(IConnectionFacade IConnectionFacade) {
        try {
            this.connectionFacade = IConnectionFacade;
            this.connectionFacade.initConnection();
            this.connectionFacade.execUpdate(CREATE_TABLES_QUERY);
            this.connectionFacade.exec(PRAGMA_FOREIGN_KEYS_ON);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addItem(String name, int parentId) {
        connectionFacade.execPrepared(ADD_SUBMENU, name, parentId);
    }

    @Override
    public void createMenu(String name, Menu parent) {
        connectionFacade.execPrepared(ADD_SUBMENU, name, parent.getId());
    }

    @Override
    public void addCard(Card card, Menu item) {

    }

    @Override
    public void deleteCard(Card card) {

    }

    @Override
    public Menu loadMenu() {
        /**
         * First of all get descent list of parent id's
         * for each parent id get child items
         * create every parent and recursively add children
         *
         */
        Menu rootMenu = new Menu(MAIN_MENU, Integer.valueOf(MAIN_MENU_ID));
        rootMenu = getChildren(rootMenu, connectionFacade, 0, "");
        return rootMenu;
    }

    @Override
    public void updateItem(Menu menuItem) {

    }

    @Override
    public void deleteItem(Menu menuItem) {

    }


    public void debugPrint(List<String[]> list) {
        System.out.println();
        for (String[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    public void dropTables(){
        try {
            connectionFacade.execUpdate(DROP_TABLES_QUERY);
            connectionFacade.initConnection();
            connectionFacade.execUpdate(CREATE_TABLES_QUERY);
            connectionFacade.exec(PRAGMA_FOREIGN_KEYS_ON);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static Menu getChildren(Menu parentMenu, IConnectionFacade connectionFacade, int parentId, String tab) {
        ArrayList<String[]> list = connectionFacade.getManyPrepared(GET_CHILDREN, parentId);

        if (list.size() > 0) {
            for (String[] obj : list) {
                int id = Integer.valueOf(obj[0]);
                String menuName = MenuDAO.extractString(connectionFacade.getManyPrepared(GET_MENU, id));
                Menu readMenu = new Menu(menuName, id);
                parentMenu.addSubmenu(readMenu);
                getChildren(readMenu, connectionFacade, id, tab + "\t");
                System.out.println(tab + id + " : " + menuName);
            }
        }
        return parentMenu;
    }

    public static String extractString(List<String[]> list){
        if(list.size() > 0){
            return list.get(0)[0];
        }
        return "EMPTY";
    }

    public static int extractInt(List<String[]> list){
        if(list.size() > 0){
            int i = Integer.valueOf(list.get(0)[0]);
            return i;
        }
        return -1;
    }


}
