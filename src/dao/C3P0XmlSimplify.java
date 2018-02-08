package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class C3P0XmlSimplify {

    private static C3P0XmlSimplify c3P0XmlSimplify;

    private ComboPooledDataSource cpds;

    static{
        c3P0XmlSimplify = new C3P0XmlSimplify();

    }

    public C3P0XmlSimplify() {
        cpds = new ComboPooledDataSource("webdatebase");
    }

    public static C3P0XmlSimplify getInstance(){
        return c3P0XmlSimplify;
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = cpds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static PreparedStatement setStatement(Connection conn, String sql){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static PreparedStatement setSQLParameters(PreparedStatement ps,List<String> paramsList) {
        try {
            if (null != paramsList) {
                for (int i = 1; i <= paramsList.size(); i++) {
                    ps.setObject(i, paramsList.get(i - 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public static PreparedStatement setSQLParameters(PreparedStatement ps,Object...values){
        try {
            if (null != values) {
                for (int i = 1; i <= values.length; i++) {
                    ps.setObject(i, values[i-1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static void releaseSources(Connection conn, PreparedStatement ps, ResultSet rs){
        if(null != rs){
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(null != ps){
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(null != conn){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void releaseSources(Connection conn, PreparedStatement ps){
        if(null != ps){
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(null != conn){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
