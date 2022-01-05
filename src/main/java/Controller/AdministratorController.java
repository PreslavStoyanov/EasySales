package Controller;

import Model.Administrator;
import Model.User;
import Utilities.PasswordHashing;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.ADMINISTRATORS_JSON;
import static Constants.BasicConstants.USERS_JSON;
import static Controller.FileController.*;
import static Controller.UserController.users;

public class AdministratorController {
    public static Map<String, Administrator> administrators = new LinkedHashMap<>();
    public static Map<String, String> categories = new LinkedHashMap<>();

    public static boolean checkAdministratorPassword(String password, Administrator administrator) {
        password = PasswordHashing.getHashPassword(password);
        return administrator.getPassword().equals(password);
    }

    public static void setAdministratorKey(String newKey, String oldKey) throws IOException {
        Administrator administrator = administrators.get(oldKey);
        administrators.remove(oldKey);
        administrators.put(newKey, new Administrator(newKey, administrator.getPassword()));
        updateFiles(ADMINISTRATORS_JSON, administrators);
    }

    public static void addAdministrator(User user) throws IOException {
        administrators.put(user.getUsername(), new Administrator(user.getUsername(), user.getPassword()));
        users.remove(user.getUsername());
        updateFiles(ADMINISTRATORS_JSON, administrators);
        updateFiles(USERS_JSON, users);
    }

    public static void removeAdministrator(Administrator admin) throws IOException {
        users.put(admin.getUsername(), new User(admin.getUsername(), admin.getPassword(), new LinkedHashMap<>()));
        administrators.remove(admin.getUsername());
        updateFiles(ADMINISTRATORS_JSON, administrators);
        updateFiles(USERS_JSON, users);
    }

    public static void deleteAdministrator (String username) throws IOException {
        administrators.remove(username);
        updateFiles(ADMINISTRATORS_JSON, administrators);
    }
}
