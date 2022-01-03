package Utilities;

import static Controller.AdministratorController.administrators;
import static Controller.UserController.users;

public class ContainsUsername {
    public static boolean containsUsername(String username) {
        return users.containsKey(username) || administrators.containsKey(username);
    }
}
