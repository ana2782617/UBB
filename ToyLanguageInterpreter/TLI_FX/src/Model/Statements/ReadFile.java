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

public class ReadFile implements IStmt {
    private Exp exp_file_id;
    private String var_name;

    public ReadFile(Exp expression,String variable){
        exp_file_id=expression;
        var_name=variable;
    }
    public Exp getExp_file_id(){return exp_file_id;}

    public String getVar_name() {
        return var_name;
    }
    public String toString(){
        return "readFile( "+exp_file_id.toString()+", "+var_name+") ";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try {
            //• pop the statement
            // • evaluate exp_file_id to a value
            MyIDictionary<String,Integer> symTable=state.getSymTable();
            int val=exp_file_id.eval(symTable,state.getHeap());
            // • using the previous step value we get the BufferedReader object associated in the FileTable.
            // If there is not any entry associated to this value in the FileTable we stop the execution with
            // an appropriate error message.
            MyIFileTable<String,BufferedReader> fileTable=state.getFileTable();
            BufferedReader reader= fileTable.lookupBR(val);

            // • Reads a line from the file using readLine method of the BufferedReader object. If line is null
            // create a zero int value. Otherwise translate the returned String into an int value (using
            // Integer.parseInt(String)).

            //while(reader.)//shouldn't we read the whole file, not just a line??//no because we put the result in 1 variable
            String line= reader.readLine();
            int value;
            if(line.equals(""))
                value=0;
            else
                value=Integer.parseInt(line);
            // • Add a new mapping (var_name, int value computed at the previous step) into the SymTable.
            //      If var_name exists in SymTable update its associated value instead of adding a new
            //      mapping.

            if(symTable.isDefined(var_name))
                symTable.update(var_name,value);
            else
                symTable.add(var_name,value);
            return null;
        }
        catch (IOException e){
            throw new MyStmtExecException("Cannot read file.\n"+e.getMessage());
        }
        catch (MyADTsException e){
            throw new MyStmtExecException("Cannot execute ReadFile statement.\n"+e.getMessage());
        }
        catch (MyExpException e){
            throw new MyStmtExecException("Cannot execute ReadFile statement.\n"+e.getMessage());
        }
        catch (NumberFormatException e){
            throw new MyStmtExecException("Cannot execute ReadFile statement.\n\tProblem with the format of the numbers in the file: "+e.getMessage());
        }


    }
}
