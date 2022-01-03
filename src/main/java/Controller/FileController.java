package Controller;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import static Controller.AdministratorController.*;
import static Controller.CatalogueController.catalogue;
import static Controller.UserController.users;

public class FileController<T> {
    T object;

    public FileController(T object) {
        this.object = object;
    }

    public static void updateFiles(String filePath, Map<String, ?> map) throws IOException {
        File file = new File(filePath);
        Gson gson;
        FileWriter fw = null;
        try {
            gson = new Gson();
            fw = new FileWriter(file);
            gson.toJson(map, fw);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    public static Map<String, Object> readFiles(String filePath, Type type) throws IOException {
        Map<String, Object> mapToReturn = new LinkedHashMap<>();
        File file = new File(filePath);
        Gson gson;
        FileReader fr = null;
        try {
            gson = new Gson();
            fr = new FileReader(file);
            mapToReturn = gson.fromJson(fr, type);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
        return mapToReturn;
    }

    public static void readUsers() throws IOException {
        Type type = TypeToken.getParameterized(Map.class, String.class, User.class).getType();
        Map<String, Object> currMap = readFiles("Users.json", type);
        LinkedHashMap<String, User> userMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            userMap.put(obj.getKey(), (User) obj.getValue());
        }
        users = userMap;
    }
    public static void readAdministrators() throws IOException {
        Type type = TypeToken.getParameterized(Map.class, String.class, Administrator.class).getType();
        Map<String, Object> currMap = readFiles("Administrators.json", type);
        LinkedHashMap<String, Administrator> administratorMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            administratorMap.put(obj.getKey(), (Administrator) obj.getValue());
        }
        administrators = administratorMap;
    }
    public static void readArticles() throws IOException {
        Type type = TypeToken.getParameterized(Map.class, String.class, Article.class).getType();
        Map<String, Object> currMap = readFiles("Catalogue.json", type);
        LinkedHashMap<String, Article> articlesMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            articlesMap.put(obj.getKey(), (Article) obj.getValue());
        }
        catalogue = articlesMap;
    }
    public static void readCategories () throws IOException {
        Type type = TypeToken.getParameterized(Map.class, String.class, String.class).getType();
        Map<String, Object> currMap = readFiles("Categories.json", type);
        LinkedHashMap<String, String> categoriesMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            categoriesMap.put(obj.getKey(), (String) obj.getValue());
        }
        categories = categoriesMap;
    }
}
