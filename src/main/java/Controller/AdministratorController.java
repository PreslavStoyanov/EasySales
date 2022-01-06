package Controller;

import Model.Administrator;
import Model.User;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.ADMINISTRATORS_JSON;
import static Constants.BasicConstants.USERS_JSON;
import static Utilities.FileHandler.*;
import static Controller.UserController.users;

public class AdministratorController {
    public static Map<String, Administrator> administrators = new LinkedHashMap<>();


    public static boolean containsAdministrator(String username) {
        return administrators.containsKey(username);
    }

    public static void changeAdministratorPassword(Administrator administrator, String newPassword) throws IOException {
        administrators.get(administrator.getUsername()).setPassword(newPassword);
        updateFiles(ADMINISTRATORS_JSON, users);
    }

    public static void changeAdministratorName(String oldName, String newName) throws IOException {
        administrators.get(oldName).setUsername(newName);
        setAdministratorKey(newName, oldName);
        updateFiles(ADMINISTRATORS_JSON, administrators);
    }

    public static void setAdministratorKey(String newKey, String oldKey) {
        Administrator administrator = administrators.get(oldKey);
        administrators.remove(oldKey);
        administrators.put(newKey, new Administrator(newKey, administrator.getPassword()));
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

    public static void deleteAdministrator(String username) throws IOException {
        administrators.remove(username);
        updateFiles(ADMINISTRATORS_JSON, administrators);
    }
}
