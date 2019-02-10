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

    void saveMenu(Menu menu);

    void writeCards(List<Card> cards, int menuId);

    void deleteCard(Card card);

    Menu loadMenu();

    void updateItem(Menu menuItem);

    void deleteItem(Menu menuItem);

}
