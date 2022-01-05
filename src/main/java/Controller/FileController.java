package Controller;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.*;
import static Controller.AdministratorController.*;
import static Controller.CatalogueController.catalogue;
import static Controller.UserController.users;

public class FileController {

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

    public static Map<String, Object> readFiles(Class<?> classType, String filePath) throws IOException {
        Map<String, Object> mapToReturn = new LinkedHashMap<>();
        File file = new File(filePath);
        Gson gson;
        FileReader fr = null;
        Type type = TypeToken.getParameterized(Map.class, String.class, classType).getType();
        try {
            gson = new Gson();
            fr = new FileReader(file);
            mapToReturn = gson.fromJson(fr, type);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found: " + filePath);
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
        return mapToReturn;
    }


}
