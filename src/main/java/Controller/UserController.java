package Controller;

import Exceptions.InvalidPasswordException;
import Model.User;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.USERS_JSON;
import static Controller.FileController.*;
import static Utilities.PasswordValidator.isValidPassword;

public class UserController {
    public static Map<String, User> users = new LinkedHashMap<>();

    public static void setUserKey (String newKey, String oldKey) throws IOException, InvalidPasswordException {
        User user = users.get(oldKey);
        users.remove(oldKey);
        users.put(newKey, new User(newKey, user.getPassword(), user.getFavorites()));
        updateFiles(USERS_JSON, users);
    }

    public static boolean checkUserPassword(String password, User user) {
        return user.getPassword().equals(password);
    }

    public static void registerUser(String username, String password) throws IOException, InvalidPasswordException {
            isValidPassword(password);
            users.put(username, new User(username, password));
            updateFiles(USERS_JSON, users);
    }

    public static void deleteUser(String username) throws IOException {
        users.remove(username);
        updateFiles(USERS_JSON, users);
    }

}
