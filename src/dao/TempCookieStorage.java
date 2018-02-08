package dao;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class TempCookieStorage {
    private List<Cookie> cookieList = new ArrayList<>();

    private boolean isSucceed = false;

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
    }

    public void addNewCookie(Cookie cookie) {
        cookieList.add(cookie);
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }
}
