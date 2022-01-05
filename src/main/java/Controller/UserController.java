package Controller;

import Model.User;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static Constants.BasicConstants.USERS_JSON;
import static Controller.FileController.*;
import static Utilities.PasswordHashing.*;
import static Utilities.PasswordValidator.getValidPassword;

public class UserController {
    public static Map<String, User> users = new LinkedHashMap<>();

    public static void changeUserPassword(User user, Scanner sc) throws IOException {
        String newPassword = getValidPassword(sc);
        newPassword = getHashPassword(newPassword);
        users.get(user.getUsername()).setPassword(newPassword);
        updateFiles(USERS_JSON, users);
    }

    public static void changeUserName(String oldName, String newName) throws IOException {
        users.get(oldName).setUsername(newName);
        setUserKey(newName, oldName);
        updateFiles(USERS_JSON, users);
    }

    public static void setUserKey(String newKey, String oldKey) {
        User user = users.get(oldKey);
        users.remove(oldKey);
        users.put(newKey, new User(newKey, user.getPassword(), user.getFavorites()));
    }

    public static void registerUser(String username, String password) throws IOException {
        password = getHashPassword(password);
        users.put(username, new User(username, password));
        updateFiles(USERS_JSON, users);
    }

    public static void deleteUser(String username) throws IOException {
        users.remove(username);
        updateFiles(USERS_JSON, users);
    }

}
