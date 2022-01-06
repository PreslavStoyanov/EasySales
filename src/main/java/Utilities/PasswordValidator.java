package Utilities;

import Exceptions.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    public static final Pattern validPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?.])[A-Za-z\\d#$@!%&*?.]{8,15}$");

    public static boolean checkPassword(String password) {
        Matcher matcher = validPassword.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) throws InvalidPasswordException {
        if (!PasswordValidator.checkPassword(password)) {
            throw new InvalidPasswordException(password);
        }
        return true;
    }

    public static boolean isRightPassword(String currentPassword, String expectedPassword) {
        currentPassword = PasswordHashing.getHashPassword(currentPassword);
        return currentPassword.equals(expectedPassword);
    }
}