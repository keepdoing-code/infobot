package ru.usefulcity.DAO.Interface;

import ru.usefulcity.Model.Card;
import ru.usefulcity.Model.Menu;

import java.util.List;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public interface IMenuDAO {

    /**
     * Get connection for selected db and
     * create db structure if it is empty
     *
     * @param connection
     */
    void init(IConnectionFacade connection);

    int addSubmenu(String name, int parentId);

    void updateItem(Menu menuItem, String newName);

    void deleteItem(Menu menuItem);

    void addCards(List<Card> cards, int menuId);

    void updateCard(Card card, String newName, String newText);

    void removeCard(Card card);

    List<Card> getCards(int id);

    void saveMenu(Menu menu);

    Menu loadMenu();

}
