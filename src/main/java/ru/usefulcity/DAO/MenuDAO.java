package ru.usefulcity.DAO;

import ru.usefulcity.DAO.Interface.IMenuDAO;
import ru.usefulcity.Model.Menu;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class MenuDAO implements IMenuDAO {
    @Override
    public void init(DBConnection connection) {

    }

    @Override
    public void createMenu(String name) {

    }

    @Override
    public void createMenu(String name, Menu parent) {

    }

    @Override
    public void addItem(String name, Menu menu) {

    }

    @Override
    public void addCard(String text, Menu item) {

    }
}
