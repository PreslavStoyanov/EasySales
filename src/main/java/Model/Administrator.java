package Model;

import java.io.IOException;
import java.util.Map;

import static Constants.BasicConstants.CATEGORIES_JSON;
import static Controller.AdministratorController.categories;
import static Controller.FileController.updateFiles;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
