package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCookieStorage
{
    public static Map<String, List<String>> getCookieStorage() {
        return cookieStorage;
    }

    private static Map<String, List<String>> cookieStorage = new HashMap<>();

    public static void modifyCookieData(String key, List newCookiesList) {
        cookieStorage.put(key, newCookiesList);
    }

    public static void addNewCookieData(String key, String newCookie) {
        List<String> cookieList = cookieStorage.get(key);
        if (cookieList != null) {
            cookieList.add(newCookie);
            cookieStorage.put(key, cookieList);
        } else {
            cookieList = new ArrayList<>();
            cookieList.add(newCookie);
            cookieStorage.put(key, cookieList);
        }
    }

    public static void removeOldCookieData(String key) {
        List<String> cookieList = cookieStorage.get(key);
        if (cookieList != null && cookieList.size() > 20) {
            for (int i = 0; i < 10 && cookieList.size() > 20; i++) {
                cookieList.remove(i);
            }
        }
    }

    public static void removeCookieData(String key) {
        cookieStorage.remove(key);
    }
}
