package Model.MyExceptions;

public class MyStmtExecException extends Exception {
    private String message;
    public MyStmtExecException(String msg){
        message=msg;
    }
    public String getMessage(){
        return "\nstmt: "+message;
    }
}
