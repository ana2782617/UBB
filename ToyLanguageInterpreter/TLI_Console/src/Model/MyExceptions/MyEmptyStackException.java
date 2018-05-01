package Model.MyExceptions;

public class MyEmptyStackException extends Exception {
    private String message;

    public MyEmptyStackException(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }
}