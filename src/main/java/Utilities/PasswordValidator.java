package Utilities;

import Exceptions.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    public static final Pattern validPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,15}$");

    public static boolean checkPassword (String password) {
        Matcher matcher = validPassword.matcher(password);
        return matcher.matches();
    }

    public static void isValidPassword(String password) throws InvalidPasswordException {
        if (!PasswordValidator.checkPassword(password)) {
            throw new InvalidPasswordException(password);
        }
    }
}