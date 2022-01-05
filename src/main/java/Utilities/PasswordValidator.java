package Utilities;

import Exceptions.InvalidPasswordException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    public static final Pattern validPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?.])[A-Za-z\\d#$@!%&*?.]{8,15}$");

    public static boolean checkPassword(String password) {
        Matcher matcher = validPassword.matcher(password);
        return matcher.matches();
    }

    public static void isValidPassword(String password) throws InvalidPasswordException {
        if (!PasswordValidator.checkPassword(password)) {
            throw new InvalidPasswordException(password);
        }
    }

    public static String getValidPassword(Scanner sc) {
        System.out.println("Requirements: 8-15 characters, at least 1 number, 1 upper case, 1 lower case and 1 special symbol");
        String password = sc.next();
        boolean whileInvalid = true;
        while (whileInvalid) {
            try {
                PasswordValidator.isValidPassword(password);
                whileInvalid = false;
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
                System.out.println("Requirements: 8-15 characters, at least 1 number, 1 upper case, 1 lower case and 1 special symbol");
                System.out.print("Try again: ");
                password = sc.next();
            }
        }
        return password;
    }

    public static boolean isRightPassword(String currentPassword, String expectedPassword) {
        currentPassword = PasswordHashing.getHashPassword(currentPassword);
        return currentPassword.equals(expectedPassword);
    }
}