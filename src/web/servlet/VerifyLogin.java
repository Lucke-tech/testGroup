package web.servlet;

import dao.CookieUntil;
import dao.DatabaseUntil;
import dao.UserCookieStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/verifyCookies")
public class VerifyLogin extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = CookieUntil.getUserName(request);
        Map<String, List<String>> data = UserCookieStorage.getCookieStorage();

        String sql = "SELECT cookies from usercookies where userId=(SELECT userId FROM users WHERE userName=?)";
        Object[][] cookies = DatabaseUntil.getDate(sql, new Object[]{userName}, 1);
        for (Object[] cooky : cookies) {
            UserCookieStorage.removeOldCookieData(userName);
            UserCookieStorage.addNewCookieData(userName, (String) cooky[0]);
        }
        System.out.println(request.getServerName());

        if (userName == null || data.size() == 0 || data.get(userName) == null || data.get(userName).size() == 0) {
            request.setAttribute("verifyLogin","No");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } else {
            if (CookieUntil.verifyCookie(request, data)) {
                alreadyLogin(request, response);
            } else {
                notLoginYet(request, response);
            }
        }
    }

    private void notLoginYet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("verifyLogin", "No");
        request.getRequestDispatcher("/index/index.jsp").forward(request, response);
    }

    private void alreadyLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("verifyLogin","Yes");
        request.setAttribute("userName", CookieUntil.getUserName(request));
        response.sendRedirect("/testPage/index/web/homePage.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
