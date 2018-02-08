package service;

import dao.SignUpDAO;

public class SignUpManager {

    public static void addNewUser(String userName, String password) {
        SignUpDAO.addNewUser(userName, password);
    }

    public static void refreshUserMap(String userName) {
        SignUpDAO.refreshUserMap(userName);
    }
}
