package Controller;

import Model.User;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static Controller.FileController.*;
import static org.junit.Assert.*;

public class FileControllerTest {

    public static final String TEST_JSON = "C:\\Users\\paf_s\\IdeaProjects\\EasySales\\src\\test\\Test.json";

    @Test
    public void testReadFiles() throws IOException {
        Map<String, User> map = setUp();
        assertFalse(map.isEmpty());
    }

    @Test
    public void testUpdateFails () throws IOException {
        Map<String, User> map = setUp();
        Map<String, User> map2 = setUp();
        User user = new User("User", "Password");
        map.put(user.getUsername(), user);
        updateFiles(TEST_JSON, map);
        map = setUp();
        assertNotEquals(map2, map);
    }

    private Map<String, User> setUp() throws IOException {
        Map<String, User> map = new LinkedHashMap<>();
        Map<String, Object> currMap = readFiles(User.class, TEST_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            map.put(obj.getKey(), (User) obj.getValue());
        }
        return map;
    }
}
