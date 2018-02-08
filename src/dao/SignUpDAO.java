package dao;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUpDAO {
    public static boolean isUserExists(String signUpName) {
       HashMap UserMap = UserLoginInformation.getDate();
       return UserMap.containsKey(signUpName);
    }

    public static void addNewUser(String userName, String password) {
        String sql = "INSERT INTO users(userName,userPassword) VALUES (?,?)";
        List<String> sqlList = new ArrayList<>();
        sqlList.add(sql);

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = C3P0XmlSimplify.getInstance().getConnection();
            DatabaseUntil.executeUpdateSentences(sqlList, ps, conn, new Object[][]{{userName, password}});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0XmlSimplify.releaseSources(conn, ps);
        }
    }

    public static void refreshUserMap(String userName) {
        HashMap userMap = UserLoginInformation.getDate();
        String sql = "SELECT userId FROM users WHERE userName=?";
        Object[][] data = DatabaseUntil.getDate(sql, new Object[]{userName}, 1 );
        userMap.put(userName, Integer.parseInt(String.valueOf(data[0][0])));
    }
}
