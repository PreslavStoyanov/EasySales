package Controller;

import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static Constants.BasicConstants.USERS_JSON;
import static Controller.FileController.readFiles;
import static Controller.UserController.*;
import static org.junit.Assert.*;

public class UserControllerTest {

    private void setUpUsers() throws IOException {
        Map<String, Object> currMap = readFiles(User.class, USERS_JSON);
        for (Map.Entry<String, Object> obj : currMap.entrySet()) {
            users.put(obj.getKey(), (User) obj.getValue());
        }
    }

    @Before
    @Test
    public void testRegisterUser() throws IOException, NoSuchAlgorithmException {
        setUpUsers();
        registerUser("Username", "Password");
        assertTrue(users.containsKey("Username"));
    }

    @Test
    public void testCheckUserPassword() {
        assertFalse(checkUserPassword("WrongPassword", users.get("Username")));
        assertTrue(checkUserPassword("Password", users.get("Username")));
    }

    @Test
    public void testSetUserKey() throws IOException {
        setUpUsers();
        setUserKey("NewUsername", "Username");
        assertTrue(users.containsKey("NewUsername"));
    }

    @After
    @Test
    public void testDeleteUser() throws IOException {
        setUpUsers();
        deleteUser("NewUsername");
        assertFalse(users.containsKey("NewUsername"));
    }
}
