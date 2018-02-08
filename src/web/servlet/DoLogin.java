package web.servlet;

import dao.C3P0XmlSimplify;
import dao.CookieUntil;
import dao.DatabaseUntil;
import dao.TempCookieStorage;
import service.LoginManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.CookieUntil.recordLoginDate;

//TODO 注册 注销 登录量统计
@WebServlet("/DoLogin")
public class DoLogin extends HttpServlet {
    private static String saveLoginCookies = "saveCookie";
    private static String userNmae = "userName";
    private static String password = "password";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userLogin(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String userName = request.getParameter(userNmae);
        String userPassword = request.getParameter(password);
        String saveLoginCookie = request.getParameter(saveLoginCookies);

        TempCookieStorage loginCookieStorage = LoginManager.processLogin(userName, userPassword, saveLoginCookie);
        if (loginCookieStorage.isSucceed()) {
            for (Cookie cookie : loginCookieStorage.getCookieList()) {
                response.addCookie(cookie);
            }
            request.getSession().setAttribute("userName", userName);
            request.getSession().removeAttribute("userHeadPic");
            recordLoginDate(request);
            response.setContentType("text/html");
            response.getWriter().write("true");
        } else {
            response.setContentType("text/html");
            response.getWriter().write("false");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

}
