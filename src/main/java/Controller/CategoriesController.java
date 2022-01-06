package Controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.CATEGORIES_JSON;
import static Utilities.FileHandler.updateFiles;

public class CategoriesController {
    public static Map<String, String> categories = new LinkedHashMap<>();

    public static void showCategories() {
        for (Map.Entry<String, String> category : categories.entrySet()) {
            System.out.printf("%s %n", category.getValue());
        }
    }

    public static boolean containsCategory(String nameOfCategory) {
        return categories.containsKey(nameOfCategory);
    }

    public static void addCategory(String nameOfCategory) throws IOException {
        categories.put(nameOfCategory, nameOfCategory);
        updateFiles(CATEGORIES_JSON, categories);
    }

    public static void removeCategory(String nameOfCategory) throws IOException {
        categories.remove(nameOfCategory, nameOfCategory);
        updateFiles(CATEGORIES_JSON, categories);
    }

    public static void setCategoryName(String newName, String oldName) throws IOException {
        categories.remove(oldName);
        categories.put(newName, newName);
        updateFiles(CATEGORIES_JSON, categories);
    }
}
