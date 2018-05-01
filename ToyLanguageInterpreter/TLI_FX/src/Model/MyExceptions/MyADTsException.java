package Model.MyExceptions;


public class MyADTsException extends Exception {
    private String message;
    public MyADTsException(String msg){
        message=msg;
    }
    public String getMessage(){
        return "\nadt: "+ message;
    }
}
