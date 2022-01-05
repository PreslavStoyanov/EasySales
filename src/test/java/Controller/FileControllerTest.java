package Controller;

import Model.User;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Constants.BasicConstants.USERS_JSON;
import static Controller.FileController.*;
import static Controller.UserController.users;
import static org.junit.Assert.*;

public class FileControllerTest {

    @Test
    public void testReadFiles() throws IOException {
        Map<String, Object> currMap = readFiles(User.class, USERS_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            users.put(obj.getKey(), (User) obj.getValue());
        }
        assertFalse(users.isEmpty());
    }

    @Test
    public void testUpdateFails () throws IOException {
        Map<String, Object> currMap = readFiles(User.class, USERS_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            users.put(obj.getKey(), (User) obj.getValue());
        }
        Map<String, User> map = new LinkedHashMap<>();
        User user = new User("User", "Password");
        map.put(user.getUsername(), user);
        currMap = readFiles(User.class, USERS_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            users.put(obj.getKey(), (User) obj.getValue());
        }
        updateFiles("C:\\Users\\paf_s\\IdeaProjects\\EasySales\\src\\test\\Test.json", map);
        assertNotEquals(users, map);
    }
}
