package web.filter;

import dao.CookieUntil;
import dao.DatabaseUntil;
import dao.UserCookieStorage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebFilter("/index.jsp")
public class IndexFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String userName = CookieUntil.getUserName(request);
        Map<String, List<String>> data = UserCookieStorage.getCookieStorage();

        String sql = "SELECT cookies from usercookies where userId=(SELECT userId FROM users WHERE userName=?)";
        Object[][] cookies = DatabaseUntil.getDate(sql, new Object[]{userName}, 1);
        for (Object[] cooky : cookies) {
            UserCookieStorage.removeOldCookieData(userName);
            UserCookieStorage.addNewCookieData(userName, (String) cooky[0]);
        }

        if (userName == null || data.size() == 0 || data.get(userName) == null || data.get(userName).size() == 0) {
            chain.doFilter(request, response);
        } else {
            if (CookieUntil.verifyCookie(request, data)) {
                alreadyLogin(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    private void alreadyLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setAttribute("verifyLogin","Yes");
//        request.setAttribute("userName", CookieUntil.getUserName(request));
        response.sendRedirect("/testPage/index/web/homePage.jsp");
    }

}
