package ru.usefulcity.Controller;

import ru.usefulcity.Log;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker {

    private final String filename;

    DBWorker(String file) {
        this.filename = file;
    }

    public int prepExec(final String query, final Object ... params) {
        int status = -1;
        try {
            Connection cn = this.connect();
            PreparedStatement ps = cn.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            status = ps.executeUpdate();
            Log.out(query + "  " + status);
            closeConnection(cn);
        } catch (SQLException e) {
            Log.out(e.getMessage());
            System.err.println(e.getMessage());
        }
        return status;
    }

    public ArrayList<Object[]> prepExecMany(String query, final Object ... params) {
        try {
            Connection cn = this.connect();
            PreparedStatement ps = cn.prepareStatement(query);

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

            closeConnection(cn);
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void exec(String query) {
        try {
            Connection cn = this.connect();
            Statement st = cn.createStatement();
            st.setQueryTimeout(30);
            boolean status = st.execute(query);

            Log.out(query + "  " + status);

            this.closeConnection(cn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execUpdate(String query) {
        try {
            Connection cn = this.connect();
            Statement st = cn.createStatement();
            st.setQueryTimeout(30);

            int status = st.executeUpdate(query);
            Log.out(query + "  " + status);

            closeConnection(cn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object[]> execMany(String query) {
        try {
            Connection cn = this.connect();
            Statement st = cn.createStatement();
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

            closeConnection(cn);
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection connect() throws SQLException {
        Connection con;
        String connectionString = "jdbc:sqlite:";
        con = DriverManager.getConnection(connectionString + filename);
        return con;
    }

    private void closeConnection(Connection cn) throws SQLException {
        if (cn != null)
            cn.close();
    }

}
