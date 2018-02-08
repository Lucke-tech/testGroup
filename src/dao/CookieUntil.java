package dao;

import org.dom4j.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CookieUntil {
    private static String loginFailed = "/loginFailed.html";
    private static String confirmYes = "Y";

    public static String codeUserInfo(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        return name + "-" + id;
    }

    public static String[] encodeUserInfo(Cookie cookie) {
        String[] strings = cookie.getValue().split("-");
        return strings;
    }

    public static void saveUSS(String uss) {
        Document doc = DocumentHelper.createDocument();

        Element rootElement = doc.addElement("Users");

    }

    public static boolean isAlreadyLogin(HttpServletRequest request, String userName) {
        Cookie[] cookies = request.getCookies();
        String userInfo = null;
        if (cookies == null) {
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("UserInfo")) {
                userInfo = cookie.getValue();
            }
        }


        String sql = "SELECT cookies FROM usercookies WHERE cookies=?";
        Object[][] verify = DatabaseUntil.getDate(sql, new Object[]{userInfo}, 1);
        if (verify.length != 0) {
            return true;
        } else {
            return false;
        }
    }


    public static String getUserName(HttpServletRequest request) {
        String userName = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("UserInfo")) {
                    userName = CookieUntil.encodeUserInfo(cookie)[0];
                }
            }
        }

        if (userName == null) {
            userName = request.getParameter("userName");
        }
        return  userName;
    }

    public static boolean verifyCookie(HttpServletRequest request, Map<String, List<String>> data) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        String uInfo = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("UserInfo")) {
                    uInfo = cookie.getValue();
                    userName = CookieUntil.encodeUserInfo(cookie)[0];
                }
            }

        } else {
            return false;
        }

        if (uInfo == null) {
            return false;
        }

        for (int i = 0; i < data.get(userName).size(); i++) {
            if (uInfo.equals(data.get(userName).get(i))) {
                return true;
            }
        }
        return false;
    }

    public static void recordLoginDate(HttpServletRequest request) throws IOException {
        String sql = "UPDATE users set lastLogin=currentLogin,currentLogin=CURRENT_TIME where userName=?;";
        List<String> sqlList = new ArrayList<>();
        sqlList.add(sql);

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = C3P0XmlSimplify.getInstance().getConnection();
            DatabaseUntil.executeUpdateSentences(sqlList, ps, conn, new Object[][]{{request.getParameter("userName")}});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0XmlSimplify.releaseSources(conn, ps);
        }
    }

    public static Cookie[] addCookies(String saveLoginCookies, String name, int userId, Connection conn, PreparedStatement ps) throws IOException, SQLException {
        String cUserInfo = codeUserInfo(name);
        Cookie cookie = new Cookie("UserInfo", cUserInfo);
        Cookie cookie1 = new Cookie("UserName", name);
        List<String> sqlList = new ArrayList<>(1);

        cookie.setPath("/");
        cookie1.setPath("/");

        if (saveLoginCookies == null) {
            cookie.setMaxAge(-1);
            cookie1.setMaxAge(-1);
        } else if (confirmYes.equals(saveLoginCookies)) {
            cookie.setMaxAge(60);
            cookie1.setMaxAge(60);
        }

        String sql = "INSERT INTO usercookies(userId, cookies) VALUES(?,?)";
        sqlList.add(sql);
        DatabaseUntil.executeUpdateSentences(sqlList, ps, conn, new Object[][]{{userId, cUserInfo}});

        UserCookieStorage.removeOldCookieData(name);
        UserCookieStorage.addNewCookieData(name, cUserInfo);

        return new Cookie[]{cookie, cookie1};
    }


}
