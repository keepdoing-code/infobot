package ru.usefulcity.DAO.Interface;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */
public interface IConnectionFacade {
    int execPrepared(final String query, final Object... params);

    ArrayList<String[]> getManyPrepared(String query, final Object... params);

    boolean exec(String query);

    int execUpdate(String query);

    ArrayList<String[]> getMany(String query);

    void initConnection() throws SQLException;

    void closeConnection() throws SQLException;

    int getIndexInserted(String query, Object... params);

    String getOnePrepared(String query, Object... params);
}
