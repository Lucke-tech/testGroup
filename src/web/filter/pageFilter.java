package web.filter;

import dao.CookieUntil;
import dao.DatabaseUntil;
import dao.UserCookieStorage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebFilter(filterName="PageFilter",urlPatterns={"/index/user/userCenter.jsp","/index/web/*"})
public class pageFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String userName = CookieUntil.getUserName(request);
//        Object[][] data = (Object[][]) request.getSession().getAttribute("usercookies");
        Map<String, List<String>> data = UserCookieStorage.getCookieStorage();
        List<Map<String, Object>> userHeadPicData = (List<Map<String, Object>>)request.getSession().getAttribute("userHeadPic");


        if (userHeadPicData == null || userHeadPicData.size() == 0 || userHeadPicData.get(0).size() == 0) {
            String sql2 = "SELECT userHeadPic FROM users WHERE userName=?";
            List<String> paramList = new ArrayList<>();
            paramList.add(userName);
            List<Map<String, Object>> data2 = DatabaseUntil.getData(sql2, paramList);
            request.getSession().setAttribute("userHeadPic", data2);
        }

        if (userName != null && (data.get(userName) == null || data.get(userName).size() == 0)) {
            String sql = "SELECT cookies from usercookies where userId=(SELECT userId FROM users WHERE userName=?)";
            Object[][] cookies = DatabaseUntil.getDate(sql, new Object[]{userName}, 1);
            for (Object[] cooky : cookies) {
                UserCookieStorage.removeOldCookieData(userName);
                UserCookieStorage.addNewCookieData(userName, (String) cooky[0]);
            }
        }

        if (userName == null || data.size() == 0 || data.get(userName) == null || data.get(userName).size() == 0) {
            System.out.println("dataProblem");
            response.sendRedirect("/testPage/index/index.jsp");
        } else {
            if (CookieUntil.verifyCookie(request, data)) {
                System.out.println("verifySucceed");
                chain.doFilter(req, resp);
            } else {
                System.out.println("verifyFailed");
                response.sendRedirect("/testPage/index/index.jsp");
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
