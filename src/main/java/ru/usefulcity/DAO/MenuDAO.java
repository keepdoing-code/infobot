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

    /**
     * addSubmenu returns inserted item id
     *
     * @param name     - Name for inserted menu item
     * @param parentId - Parent menu id for inserted menu item
     * @return - return id of new record
     */
    @Override
    public int addSubmenu(String name, int parentId) {
        return connectionFacade.insertOneGetId(ADD_SUBMENU, name, parentId);
    }

    @Override
    public void saveMenu(Menu menu) {
        dropTables();
        saveMenuRecursive(menu, 0);
    }

    @Override
    public void writeCards(List<Card> cards, int menuId) {
        for (Card card : cards) {
            connectionFacade.execPrepared(ADD_CARD, menuId, card.getName(), card.getText());
        }
    }

    @Override
    public void deleteCard(Card card) {

    }

    /**
     * First of all get descent list of parent id's
     * for each parent id get child items
     * create every parent and recursively addText children
     */
    @Override
    public Menu loadMenu() {
        Menu rootMenu = new Menu(MAIN_MENU, 0);
        rootMenu = loadMenuRecursive(rootMenu, connectionFacade, 0, "");
        return rootMenu;
    }

    @Override
    public void updateItem(Menu menuItem) {

    }

    @Override
    public void deleteItem(Menu menuItem) {

    }

    public List<Card> getCards(int id) {
        List<String[]> data = connectionFacade.getManyPrepared(GET_CARDS, id);
        List<Card> list = new ArrayList<>();
        for (String[] item : data) {
            Card card = new Card(item[0]).addText(item[1]);
            list.add(card);
        }
        return list;
    }


    public void debugPrint(List<String[]> list) {
        System.out.println();
        for (String[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    public void dropTables() {
        try {
            connectionFacade.execUpdate(DROP_TABLES_QUERY);
            connectionFacade.initConnection();
            connectionFacade.execUpdate(CREATE_TABLES_QUERY);
            connectionFacade.exec(PRAGMA_FOREIGN_KEYS_ON);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Menu loadMenuRecursive(Menu parentMenu, IConnectionFacade connectionFacade, int parentId, String tab) {
        ArrayList<String[]> list = connectionFacade.getManyPrepared(GET_CHILDREN, parentId);

        if (list.size() > 0) {
            for (String[] obj : list) {
                int id = Integer.valueOf(obj[0]);
                String menuName = MenuDAO.extractString(connectionFacade.getManyPrepared(GET_MENU, id));
                Menu readMenu = new Menu(menuName, id);
                readMenu.addCard(getCards(id));
                parentMenu.addSubmenu(readMenu);
                System.out.println(tab + id + " : " + menuName);
                loadMenuRecursive(readMenu, connectionFacade, id, tab + "\t");
            }
        }
        return parentMenu;
    }


    private void saveMenuRecursive(Menu menu, int parentId) {
        for (Menu menuItem : menu) {
            String name = menuItem.getName();
            int id = this.addSubmenu(name, parentId);
            this.writeCards(menuItem.getCards(), id);

            if (menuItem.iterator().hasNext()) {
                saveMenuRecursive(menuItem, id);
            }
        }
    }


    public static String extractString(List<String[]> list) {
        if (list.size() > 0) {
            return list.get(0)[0];
        }
        return "EMPTY";
    }

    public static int extractInt(List<String[]> list) {
        if (list.size() > 0) {
            int i = Integer.valueOf(list.get(0)[0]);
            return i;
        }
        return -1;
    }


}
