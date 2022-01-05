package Exceptions;

public class InvalidPasswordException extends RuntimeException{
    String invalidPassword;

    public InvalidPasswordException(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    @Override
    public String getMessage() {
        return "Invalid password!";
    }

}
