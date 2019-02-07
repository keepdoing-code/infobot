package ru.usefulcity.DAO.Interface;
import ru.usefulcity.DAO.DBConnection;
import ru.usefulcity.Model.Menu;

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
    void init(DBConnection connection);

    void createMenu(String name);

    void createMenu(String name, Menu parent);

    void addItem(String name, Menu menu);

    void addCard(String text, Menu item);

}
