package ru.usefulcity.DAO;

import ru.usefulcity.DAO.Interface.IConnectionFacade;
import ru.usefulcity.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 07/02/19.
 *
 * @author Yuri Lupandin
 * @version 1.0
 */

public class ConnectionFacade implements IConnectionFacade {
    private final boolean SHOW_TABLE_HEAD = false;
    private Connection connection = null;
    private String connectionString = "jdbc:sqlite:infobot.db";


    public ConnectionFacade() {
    }

    public ConnectionFacade(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void initConnection() throws SQLException {
        connection = DriverManager.getConnection(connectionString);
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null)
            connection.close();
    }

    @Override
    public boolean exec(String query) {
        boolean status = false;
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            status = st.execute(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int execUpdate(String query) {
        int status = -1;
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            status = st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int execPrepared(String query, Object... params) {
        int status = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps = setParams(ps, params);
            status = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            Log.out(e.getMessage());
            System.err.println(e.getMessage());
        }
        return status;
    }

    @Override
    public ArrayList<String[]> getMany(String query) {
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            ResultSet rs = st.executeQuery(query);
            ArrayList<String[]> data = new ArrayList<>();
            setHead(data, rs);
            setData(data, rs);
            st.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String[]> getManyPrepared(String query, Object... params) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps = setParams(ps, params);
            ResultSet rs = ps.executeQuery();
            ArrayList<String[]> data = new ArrayList<>();
            setHead(data, rs);
            setData(data, rs);
            ps.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getIndexInserted(String query, Object... params) {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps = setParams(ps, params);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getOnePrepared(String query, Object... params) {
        List<String[]> data = getManyPrepared(query, params);
        return data.get(0)[0];
    }


    private void setData(List<String[]> data, ResultSet rs) throws SQLException {
        final int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String[] obj = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                obj[i - 1] = rs.getString(i);
            }
            data.add(obj);
        }
    }

    private void setHead(List<String[]> data, ResultSet rs) throws SQLException {
        if (!SHOW_TABLE_HEAD) return;
        final int columnCount = rs.getMetaData().getColumnCount();
        String[] head = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            head[i] = rs.getMetaData().getColumnName(i + 1);
        }
        data.add(head);
    }

    private PreparedStatement setParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps;
    }
}
