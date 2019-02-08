package ru.usefulcity.DAO.Interface;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public interface IDBFacade {
    int execPrepared(final String query, final Object... params);

    ArrayList<Object[]> getManyPrepared(String query, final Object... params);

    boolean exec(String query);

    int execUpdate(String query);

    ArrayList<Object[]> getMany(String query);

    void initConnection() throws SQLException;

    void closeConnection() throws SQLException;
}
