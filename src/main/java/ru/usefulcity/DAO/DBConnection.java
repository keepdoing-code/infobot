package ru.usefulcity.DAO;

import ru.usefulcity.DAO.Interface.IDBConnection;
import ru.usefulcity.Log;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */

public class DBConnection  implements IDBConnection {
    private Connection connection = null;

    private String connectionString = "jdbc:sqlite:infobot.db";


    public DBConnection() {
    }

    public DBConnection(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void initConnection() throws SQLException {
        connection = DriverManager.getConnection(connectionString);

    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }

    @Override
    public int prepExec(String query, Object... params) {
        int status = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            status = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            Log.out(e.getMessage());
            System.err.println(e.getMessage());
        }
        return status;
    }

    @Override
    public ArrayList<Object[]> prepExecMany(String query, Object... params) {
        try {

            PreparedStatement ps = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            final int columnCount = rs.getMetaData().getColumnCount();

            Object[] head = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                head[i] = rs.getMetaData().getColumnName(i + 1);
            }

            ArrayList<Object[]> data = new ArrayList<>();
            data.add(head);

            while (rs.next()) {
                Object[] obj = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    obj[i - 1] = rs.getObject(i);
                }
                data.add(obj);
            }

            ps.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void exec(String query) {
        try {

            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            boolean status = st.execute(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execUpdate(String query) {
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            int status = st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Object[]> execMany(String query) {
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            ResultSet rs = st.executeQuery(query);
            final int columnCount = rs.getMetaData().getColumnCount();

            Object[] head = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                head[i] = rs.getMetaData().getColumnName(i + 1);
            }

            ArrayList<Object[]> data = new ArrayList<>();
            data.add(head);

            while (rs.next()) {
                Object[] obj = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    obj[i - 1] = rs.getObject(i);
                }
                data.add(obj);
            }

            st.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
