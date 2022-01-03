package Controller;

import Model.InvalidPasswordException;
import Model.PasswordValidator;
import Model.User;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Controller.AdministratorController.administrators;
import static Controller.FileController.*;

public class UserController {
    public static Map<String, User> users = new LinkedHashMap<>();

    public static void setUserKey (String newKey, String oldKey) throws IOException, InvalidPasswordException {
        User user = users.get(oldKey);
        users.remove(oldKey);
        users.put(newKey, new User(newKey, user.getPassword(), user.getFavorites()));
        updateFiles("Users.json", users);
    }

    public static void isValidPassword(String password) throws InvalidPasswordException {
        if (!PasswordValidator.checkPassword(password)) {
            throw new InvalidPasswordException(password);
        }
    }

    public static boolean checkUserPassword(String password, User user) {
        return user.getPassword().equals(password);
    }

    public static boolean containsUsername(String username) {
        return users.containsKey(username) || administrators.containsKey(username);
    }

    public static void registerUser(String username, String password) throws IOException, InvalidPasswordException {
            isValidPassword(password);
            users.put(username, new User(username, password));
            updateFiles("Users.json", users);

    }

    public static void deleteUser(String username) throws IOException {
        users.remove(username);
        updateFiles("Users.json", users);
    }

}
