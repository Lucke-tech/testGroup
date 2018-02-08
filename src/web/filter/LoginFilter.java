package web.filter;

import dao.CookieUntil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import static dao.CookieUntil.recordLoginDate;

@WebFilter("/DoLogin")
public class LoginFilter implements Filter {

    private static String userNmae = "userName";
    private static String password = "password";
    static int count= 0;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        if (CookieUntil.isAlreadyLogin(request, request.getParameter(userNmae))) {
            request.setAttribute("userName", request.getParameter(userNmae));
            recordLoginDate(request);
            String str = CookieUntil.getUserName(request);
            if (str != null) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect("/testPage/index/web/homePage.jsp");
            }
        } else {
            chain.doFilter(request,response);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }


}
