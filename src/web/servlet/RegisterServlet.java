package web.servlet;

import dao.CookieUntil;
import dao.SignUpDAO;
import service.SignUpManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.io.IOException;

@WebServlet("/DoRegister")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将用户信息存入新的xml文件中
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        SignUpManager.addNewUser(userName, password);
        SignUpManager.refreshUserMap(userName);
        response.getWriter().write("true");
//        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
