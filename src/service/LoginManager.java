package service;

import dao.C3P0XmlSimplify;
import dao.CookieUntil;
import dao.DatabaseUntil;
import dao.TempCookieStorage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static dao.CookieUntil.recordLoginDate;

public class LoginManager {
    /**
     * 因为要完成一次连接数据库，请求两张不同的表，获取多个表的不相关数据，因此在这个service层函数中写了dao层的操作
     * @param userName
     * @param userPassword
     * @param saveLoginCookies
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static TempCookieStorage processLogin(String userName, String userPassword, String saveLoginCookies) throws IOException, SQLException {
        String userPass = null;
        int userId = 000000;
        String sql = "SELECT userId,userPassword FROM users WHERE userName=?";
        TempCookieStorage loginCookieStorage = new TempCookieStorage();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = C3P0XmlSimplify.getInstance().getConnection();
            rs = DatabaseUntil.excuteQuerySentence(sql, ps, conn, new Object[]{userName});

            if (rs.next()) {
                userId =  rs.getInt(1);
                userPass = (String)rs.getObject(2);
            }

            if (userPass != null) {
                if (userPass.equals(userPassword)) {
                    Cookie cookieToAdd[] = CookieUntil.addCookies(saveLoginCookies, userName, userId, conn, ps);
                    loginCookieStorage.setCookieList(Arrays.asList(cookieToAdd));
                    loginCookieStorage.setSucceed(true);
                    return  loginCookieStorage;
                } else {
                    loginCookieStorage.setSucceed(false);
                    return loginCookieStorage;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0XmlSimplify.releaseSources(conn, ps, rs);
        }

        loginCookieStorage.setSucceed(false);
        return loginCookieStorage;
    }
}
