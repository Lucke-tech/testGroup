package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUntil {


    static {
    }


    /**
     * 用来得到查询数据的集合
     *
     * @param sql     所需要的查询语句
     * @param ps      PreparedStatement 对象
     * @param conn    Connection 对象
     * @param objects 参数化查询的参数数组
     * @return ResultSet集合
     * @throws SQLException
     */
    public static ResultSet excuteQuerySentence(String sql, PreparedStatement ps, Connection conn, Object[] objects) throws SQLException {

        ps = C3P0XmlSimplify.setStatement(conn, "USE webdate");
        ps = C3P0XmlSimplify.setSQLParameters(ps, new Object[]{});
        ps.execute();

        ps = C3P0XmlSimplify.setStatement(conn, sql);
        ps = C3P0XmlSimplify.setSQLParameters(ps, objects);

        return ps.executeQuery();
    }

    public static ResultSet excuteQuerySentence(String sql, PreparedStatement ps, Connection conn, List<String> paramsList) throws SQLException {

        ps = C3P0XmlSimplify.setStatement(conn, "USE webdate");
        ps = C3P0XmlSimplify.setSQLParameters(ps, new Object[]{});
        ps.execute();

        ps = C3P0XmlSimplify.setStatement(conn, sql);
        ps = C3P0XmlSimplify.setSQLParameters(ps, paramsList);

        return ps.executeQuery();
    }

    /**
     * 用来执行一系列的数据库操作语句
     *
     * @param sqlList 所要执行的有序的语句集合
     * @param ps      PreparedStatement 对象
     * @param conn    Connection 对象
     * @param objects 参数化查询的参数二维数组,object[i][j],i为第几局sql语句的参数数组,j为当前数组的第几个字段
     * @return 最后一步查询到条目的总数
     * @throws SQLException
     */
    public static int executeUpdateSentences(List<String> sqlList, PreparedStatement ps, Connection conn, Object[][] objects) throws SQLException {

        ps = C3P0XmlSimplify.setStatement(conn, "USE webdate");
        ps = C3P0XmlSimplify.setSQLParameters(ps, new Object[]{});
        ps.execute();

        for (int i = 0; i < sqlList.size() - 1; i++) {
            ps = C3P0XmlSimplify.setStatement(conn, sqlList.get(i));
            ps = C3P0XmlSimplify.setSQLParameters(ps, objects[i]);
            ps.execute(sqlList.get(i));
        }

        ps = C3P0XmlSimplify.setStatement(conn, sqlList.get(sqlList.size() - 1));
        ps = C3P0XmlSimplify.setSQLParameters(ps, objects[sqlList.size() - 1]);
        int sum = ps.executeUpdate();

        return sum;
    }

    /**
     * 用来获取查询的数据
     *
     * @param sql     所需要的查询语句
     * @param objects 参数化查询的参数数组
     * @param size    查询的每个记录的字段总数
     * @return date[i][j] i为第几个数据，j为该数据的第几个字段,i,j 均从1开始
     */
    public static Object[][] getDate(String sql, Object[] objects, int size) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[][] data = null;

        int index = 0;

        try {
            conn = C3P0XmlSimplify.getInstance().getConnection();
            rs = excuteQuerySentence(sql, ps, conn, objects);
            rs.last();
            data = new Object[rs.getRow()][size];
            rs.beforeFirst();

            while (rs.next()) {
                for (int i = 0; i < size; i++) {
                    data[index][i] = rs.getObject(i + 1);
                }
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0XmlSimplify.releaseSources(conn, ps, rs);
        }

        return data;
    }
    //TODO 下标越界问题，如何获得resultSet最大下标问题；

    public static void modifySql(List<String> sqlList, Object[][] objects) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = C3P0XmlSimplify.getInstance().getConnection();
            DatabaseUntil.executeUpdateSentences(sqlList, ps, conn, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0XmlSimplify.releaseSources(conn, ps);
        }
    }

    public static List<Map<String, Object>> getData(String sql, List<String> paramsList) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> dataList = new ArrayList<>();

        try {
            conn = C3P0XmlSimplify.getInstance().getConnection();
            rs = excuteQuerySentence(sql, ps, conn, paramsList);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columCount = rsmd.getColumnCount();

            while (rs.next()) {
                System.out.println("inin");
                Map<String, Object> data = new HashMap<>();

                for (int i = 1; i <= columCount; i++) {
                    data.put(rsmd.getColumnName(i), rs.getObject(i));
                    System.out.println(rsmd.getColumnName(i));
                    System.out.println(rs.getObject(i));
                    dataList.add(data);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0XmlSimplify.releaseSources(conn, ps, rs);
        }

        return dataList;
    }
}
