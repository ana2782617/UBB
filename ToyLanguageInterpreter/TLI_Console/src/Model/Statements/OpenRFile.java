package Model.Statements;

import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIFileTable;
import Model.MyADTs.MyStack;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt{
    private String var_file_id;
    private String filename;

    public OpenRFile(String var,String fname){
        var_file_id=var;
        filename=fname;
    }

    public String getFilename() {
        return filename;
    }

    public String getVar_file_id() {
        return var_file_id;
    }

    public String toString(){
        return "openRfile( "+var_file_id+", "+filename+") ";
    }
    public PrgState execute(PrgState state) throws MyStmtExecException{
        //Its execution on the ExeStack is the following:
        //• pop the statement //already popped
        try{

        //• check whether the filename is not already in the FileTable. If it exists stopped the execution
        // with an appropriate error message.
            MyIFileTable<String,BufferedReader>fileTable=state.getFileTable();
            if(fileTable.isDefined(filename)) throw new MyStmtExecException("Cannot open. Filename already in fileTable.\n");
            // • open the file filename in Java using an instance of the BufferedReader class. If the file does
        // not exist or other IO error occurs stopped the execution with an appropriate error message.
            BufferedReader file=new BufferedReader(new FileReader(filename));
        //• create a new entrance into the FileTable which maps a new unique integer key to the
        // (filename, instance of the BufferedReader class created before).
            int key=fileTable.add(filename,file);
        //• set the var_file_id to that new unique integer key (created at the previous step) into the
        // SymTable
            MyIDictionary<String,Integer> symTable=state.getSymTable();
            symTable.add(var_file_id,key);
            return null;
        }
        catch (IOException e) {
            throw new MyStmtExecException("Cannot open. File does not exist.\n"+e.getMessage());
        }
        catch (MyADTsException e){
            throw new MyStmtExecException("Cannot execute OpenRFile statement.\n"+e.getMessage());
        }

    }
}
