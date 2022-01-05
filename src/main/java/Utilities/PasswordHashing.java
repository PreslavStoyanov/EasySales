package Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {

    public static String getHashPassword(String passwordToHash) {
        byte[] salt = new byte[15];
        String generatedPassword = null;
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update(salt);

            byte[] b = msgDigest.digest(passwordToHash.getBytes());
            StringBuilder sbObj = new StringBuilder();
            for (byte value : b) {
                sbObj.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sbObj.toString();
        } catch (NoSuchAlgorithmException obj) {
            obj.printStackTrace();
        }
        return generatedPassword;
    }
}
