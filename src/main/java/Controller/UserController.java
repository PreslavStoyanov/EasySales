package Controller;

import Model.User;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.USERS_JSON;
import static Utilities.FileHandler.*;

public class UserController {
    public static Map<String, User> users = new LinkedHashMap<>();

    public static boolean containsUser(String username) {
        return users.containsKey(username);
    }

    public static void changeUserPassword(User user, String newPassword) throws IOException {
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
        users.put(username, new User(username, password));
        updateFiles(USERS_JSON, users);
    }

    public static void deleteUser(String username) throws IOException {
        users.remove(username);
        updateFiles(USERS_JSON, users);
    }

}
