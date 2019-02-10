package ru.usefulcity.DAO;

import ru.usefulcity.Log;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class SQLQueries {
    public static final String CREATE_TABLES_QUERY =
            "CREATE TABLE IF NOT EXISTS menu (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "parent_id INTEGER, " +
                    "name text);" +

                    "CREATE TABLE IF NOT EXISTS cards (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "menu_id INTEGER," +
                    "name TEXT, " +
                    "text TEXT," +
                    "FOREIGN KEY(menu_id) REFERENCES menu(id) ON DELETE CASCADE);";

    public static final String DROP_TABLES_QUERY = "DROP TABLE if exists menu; DROP TABLE if exists cards;";
    public static final String PRAGMA_FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON;";
    public static final String ADD_SUBMENU = "INSERT INTO menu(name, parent_id) VALUES (?, ?);";
    public static final String ADD_CARD = "INSERT INTO cards(menu_id, name, text) VALUES (?, ?, ?);";
    public static final String REMOVE_MENU = "DELETE FROM menu WHERE id = ?; DELETE FROM menu WHERE parent_id = ?; DELETE FROM cards WHERE menu_id = ?;";
    public static final String REMOVE_CARD = "DELETE FROM cards WHERE id = ?;";
    public static final String GET_ALL_MENU = "SELECT * FROM menu ORDER BY parent_id;";
    public static final String GET_CARDS = "SELECT id, name, text FROM cards WHERE menu_id = ?;";
    public static final String GET_MENU = "SELECT name FROM menu WHERE id = ?;";
    public static final String GET_CHILDREN = "SELECT id FROM menu WHERE parent_id = ?";
    public static final String DISTINCT_PARENT = "SELECT distinct parent_id FROM menu ORDER BY parent_id asc";
    public static final String UPDATE_MENU_ITEM = "UPDATE menu SET name = ? WHERE id = ?;";
    public static final String UPDATE_CARD = "UPDATE cards SET name = ?, text = ? WHERE id = ?;";
}
