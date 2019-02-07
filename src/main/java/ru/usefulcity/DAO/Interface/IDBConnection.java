package ru.usefulcity.DAO.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public interface IDBConnection {
    int prepExec(final String query, final Object... params);

    ArrayList<Object[]> prepExecMany(String query, final Object... params);

    void exec(String query);

    void execUpdate(String query);

    ArrayList<Object[]> execMany(String query);

    void initConnection() throws SQLException;

    void closeConnection(Connection cn) throws SQLException;
}
