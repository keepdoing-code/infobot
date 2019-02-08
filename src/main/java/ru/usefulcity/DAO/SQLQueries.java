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
            "CREATE TABLE if not exists menu (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "parent_id INTEGER, " +
                    "name text);" +

                    "CREATE TABLE if not exists cards (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "menu_id INTEGER," +
                    "text TEXT," +
                    "FOREIGN KEY(menu_id) REFERENCES menu(id) ON DELETE CASCADE);";

    public static final String DROP_TABLES_QUERY = "DROP TABLE if exists menu; DROP TABLE if exists cards;";
    public static final String PRAGMA_FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON;";
    public static final String ADD_ROOT_MENU = "INSERT INTO menu(name, parent_id) VALUES (?, 0);";
    public static final String ADD_SUBMENU = "INSERT INTO menu(name, parent_id) VALUES (?, ?);";

    public static final String ADD_CARD = "INSERT INTO cards(menu_id, text) VALUES (?, ?);";
    public static final String REMOVE_MENU = "DELETE FROM menu WHERE parent_id = ?; DELETE FROM cards WHERE menu_id = ?;";

    public static final String REMOVE_CARD = "DELETE FROM cards WHERE id = ?;";
    public static final String GET_ALL_CARDS = "SELECT menu_id, text FROM cards;";
    public static final String GET_ALL_MENU = "SELECT * FROM menu ORDER BY parent_id;";
    public static final String GET_CARDS = "SELECT text FROM cards WHERE menu_id = ?;";

    public static final String GET_MENU = "SELECT name FROM menu WHERE parent_id = ?;";

    public static final String DISTINCT_PARENT = "select distinct parent_id from menu order by parent_id asc";
    public static final String GET_CHILDREN = "select * from menu where parent_id = ?";
}
