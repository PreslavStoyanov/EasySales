package Controller;

import Model.Administrator;
import Model.InvalidPasswordException;
import Model.User;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Controller.FileController.*;
import static Controller.UserController.users;

public class AdministratorController {
    public static Map<String, Administrator> administrators = new LinkedHashMap<>();
    public static Map<String, String> categories = new LinkedHashMap<>();

    public static boolean checkAdministratorPassword(String password, Administrator administrator) {
        return administrator.getPassword().equals(password);
    }

    public static void setAdministratorKey(String newKey, String oldKey) throws IOException {
        Administrator administrator = administrators.get(oldKey);
        administrators.remove(oldKey);
        administrators.put(newKey, new Administrator(newKey, administrator.getPassword()));
        updateFiles("Administrators.json", administrators);
    }

    public static void addAdministrator(User user) throws IOException {
        administrators.put(user.getUsername(), new Administrator(user.getUsername(), user.getPassword()));
        users.remove(user.getUsername());
        updateFiles("Administrators.json", administrators);
        updateFiles("Users.json", users);
    }

    public static void removeAdministrator(Administrator admin) throws IOException, InvalidPasswordException {
        users.put(admin.getUsername(), new User(admin.getUsername(), admin.getPassword(), new LinkedHashMap<>()));
        administrators.remove(admin.getUsername());
        updateFiles("Administrators.json", administrators);
        updateFiles("Users.json", users);
    }

    public static void deleteAdministrator (String username) throws IOException {
        administrators.remove(username);
        updateFiles("Administrators.json", administrators);
    }
}
