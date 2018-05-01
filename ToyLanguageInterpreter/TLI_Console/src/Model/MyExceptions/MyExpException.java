package Model.MyExceptions;

public class MyExpException extends Exception {
    private String message;
    public MyExpException(String msg){
        message=msg;
    }
    public String getMessage(){
        return "\nexp: "+message;
    }
}
