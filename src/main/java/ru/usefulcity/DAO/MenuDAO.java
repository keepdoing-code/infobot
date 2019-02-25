package ru.usefulcity.DAO;

import ru.usefulcity.DAO.Interface.IConnectionFacade;
import ru.usefulcity.DAO.Interface.IMenuDAO;
import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.usefulcity.DAO.SQLQueries.*;
import static ru.usefulcity.Controller.Constants.*;

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
        return connectionFacade.getIndexInserted(ADD_SUBMENU, name, parentId);
    }

    @Override
    public void addCards(List<Card> cards, int menuId) {
        for (Card card : cards) {
            connectionFacade.execPrepared(ADD_CARD, menuId, card.getName(), card.getText());
        }
    }

    @Override
    public void updateCard(Card card, String newName, String newText) {
        connectionFacade.execPrepared(UPDATE_CARD, newName, newText, card.getId());
    }

    @Override
    public void removeCard(Card card) {
        connectionFacade.execPrepared(REMOVE_CARD, card.getId());
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
    public void saveMenu(Menu menu) {
        dropTables();
        int id = connectionFacade.getIndexInserted(ADD_SUBMENU, menu.getName(), 0);

        saveMenuRecursive(menu, id);
    }

    @Override
    public void updateItem(Menu menuItem, String newName) {
        connectionFacade.execPrepared(UPDATE_MENU_ITEM, newName, menuItem.getId());
    }

    @Override
    public void deleteItem(Menu menuItem) {
        String id = menuItem.getId();
        connectionFacade.execPrepared(REMOVE_MENU, id, id, id);
    }

    @Override
    public List<Card> getCards(int id) {
        List<String[]> data = connectionFacade.getManyPrepared(GET_CARDS, id);
        List<Card> list = new ArrayList<>();
        for (String[] item : data) {
            String cardId = item[0];
            String name = item[1];
            String text = item[2];
            list.add(new Card(name).addText(text).setId(cardId));
        }
        return list;
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
                String menuName = connectionFacade.getOnePrepared(GET_MENU, id);
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
            this.addCards(menuItem.getCards(), id);

            if (menuItem.iterator().hasNext()) {
                saveMenuRecursive(menuItem, id);
            }
        }
    }

    public void debugPrint(List<String[]> list) {
        System.out.println();
        for (String[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }
}
