package Model.Statements;

import Model.Expressions.Exp;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIFileTable;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private Exp exp_file_id;

    public CloseRFile(Exp expression){
        exp_file_id=expression;
    }

    public Exp getExp_file_id() {
        return exp_file_id;
    }
    public String toString(){
        return "closeRfile("+exp_file_id.toString()+") ";
    }
    public PrgState execute(PrgState state)throws MyStmtExecException{
        try{
            //pop the statement
            // • evaluate exp_file_id to a value. If any error occurs then terminate the execution with an
            // appropriate error message.
            MyIDictionary<String,Integer> symTable=state.getSymTable();
            int value=exp_file_id.eval(symTable,state.getHeap());
            // • Use the value (computed at the previous step) to get the entry into the FileTable and get the
            // associated BufferedReader object. If there is not any entry in FileTable for that value we
            // stop the execution with an appropriate error message.
            MyIFileTable<String,BufferedReader> fileTable=state.getFileTable();
            BufferedReader reader=fileTable.lookupBR(value);
            // • call the close method of the BufferedReader object
            reader.close();
            // • delete the entry from the FileTable
            fileTable.delete(value);

            return null;
        }
        catch (MyExpException e){
            throw new MyStmtExecException("Cannot execute CloseRFile statement.\n"+e.getMessage());
        }
        catch (IOException e){
            throw new MyStmtExecException("Cannot close file.\n"+e.getMessage());
        }
        catch (MyADTsException e){
            throw new MyStmtExecException("Cannot close file.\n"+e.getMessage());
        }
    }
}