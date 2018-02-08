package dao;


import java.util.HashMap;

public class UserLoginInformation {
    private static HashMap<String, Integer> userLoginInformation = new HashMap<>();

    public UserLoginInformation() {
        initMap();
    }

    public UserLoginInformation getInstance() {
        return UserLoginInformationInstance.INSTANCE;
    }

    private static class UserLoginInformationInstance {
        private static final UserLoginInformation INSTANCE = new UserLoginInformation();
    }

    /**
     * 每次服务器启动，将已注册的用户列表加载入内存中，包括用户名以及密码
     * 这个做法，牺牲了一定的内存，而减少了对数据库的读写次数
     */
    public static void initMap() {
        String sql = "SELECT userId,userName FROM users";
        Object[][] data = DatabaseUntil.getDate(sql, new Object[]{}, 2);
        for (Object[] aData : data) {
            userLoginInformation.put(String.valueOf(aData[1]), Integer.parseInt(String.valueOf(aData[0])));
        }

    }

    /**
     * 用于新用户注册以及老用户更改密码时刷新登录列表
     * @param userName
     */
    public static void refreshUserInfo(String userName) {

    }


    public static HashMap<String, Integer> getDate() {
        return userLoginInformation;
    }

}