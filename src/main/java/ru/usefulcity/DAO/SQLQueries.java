package ru.usefulcity.DAO;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public class SQLQueries {
    public static final String PRAGMA_FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON;";
    public static final String ADD_ROOT_MENU = "INSERT INTO menu(name, root_id) VALUES (?, 0);";
    public static final String ADD_SUBMENU = "INSERT INTO menu(name, root_id) VALUES (?, ?);";
    public static final String ADD_CARD = "INSERT INTO cards(menu_id, name, type, desc, tel, link, pic, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String REMOVE_MENU = "DELETE FROM menu WHERE root_id = ?; DELETE FROM cards WHERE menu_id = ?;";
    public static final String REMOVE_CARD = "DELETE FROM cards WHERE id = ?;";

    public static final String GET_ALL_CARDS = "SELECT menu_id, name, type, desc, tel, link, pic, rating FROM cards;";
    public static final String GET_ALL_MENU = "SELECT * FROM menu ORDER BY root_id;";
    public static final String GET_CARDS = "SELECT name, type, desc, tel, link, pic, rating FROM cards WHERE menu_id = ?;";
    public static final String GET_MENU = "SELECT name FROM menu WHERE root_id = ?;";

    public static final String DROP_TABLES_QUERY = "DROP TABLE if exists menu; DROP TABLE if exists cards;";
    public static final String CREATE_TABLES_QUERY =
            "CREATE TABLE if not exists menu (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "root_id INTEGER, " +
                    "name text);" +

                    "CREATE TABLE if not exists cards (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "menu_id INTEGER," +
                    "name TEXT," +
                    "type INTEGER," +
                    "desc TEXT, " +
                    "tel TEXT, " +
                    "link TEXT, " +
                    "pic TEXT, " +
                    "rating INTEGER," +
                    "FOREIGN KEY(menu_id) REFERENCES menu(id) ON DELETE CASCADE);";
}
