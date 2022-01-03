package Model;

import Exceptions.InvalidPasswordException;

import java.io.IOException;
import java.util.Map;

import static Constants.BasicConstants.ADMINISTRATORS_JSON;
import static Constants.BasicConstants.CATEGORIES_JSON;
import static Controller.AdministratorController.administrators;
import static Controller.AdministratorController.categories;
import static Controller.FileController.updateFiles;
import static Controller.UserController.isValidPassword;

public class Administrator {
    private String username;
    private String password;

    public Administrator(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public static void showCategories() {
        for (Map.Entry<String, String> category : categories.entrySet()) {
            System.out.printf("%s %n", category.getValue());
        }
    }

    public boolean containsCategory(String nameOfCategory) {
        return categories.containsKey(nameOfCategory);
    }

    public void addCategory(String nameOfCategory) throws IOException {
        categories.put(nameOfCategory, nameOfCategory);
        updateFiles(CATEGORIES_JSON, categories);
    }

    public void removeCategory(String nameOfCategory) throws IOException {
        categories.remove(nameOfCategory, nameOfCategory);
        updateFiles(CATEGORIES_JSON, categories);
    }

    public void setCategoryName(String newName, String oldName) throws IOException {
        categories.remove(oldName);
        categories.put(newName, newName);
        updateFiles(CATEGORIES_JSON, categories);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) throws IOException {
        this.username = username;
        updateFiles(ADMINISTRATORS_JSON, administrators);
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) throws InvalidPasswordException, IOException {
        isValidPassword(password);
        this.password = password;
        updateFiles(ADMINISTRATORS_JSON, administrators);
    }
}
