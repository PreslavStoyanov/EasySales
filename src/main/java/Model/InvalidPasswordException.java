package Model;

public class InvalidPasswordException extends Exception{
    String invalidPassword;

    public InvalidPasswordException(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    @Override
    public String getMessage() {
        return "Invalid password!";
    }
}