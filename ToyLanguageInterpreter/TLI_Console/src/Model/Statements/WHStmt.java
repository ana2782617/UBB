package Model.Statements;

import Model.Expressions.Exp;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class WHStmt implements IStmt {
    String var_name;
    Exp expression;

    public WHStmt(String var_name,Exp expression){ this.var_name=var_name;this.expression=expression;}

    public String getVar_name() {
        return var_name;
    }

    public Exp getExpression() {
        return expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try{
            MyIHeap<Integer> heap=state.getHeap();
            MyIDictionary<String,Integer> symTable=state.getSymTable();
            int address=symTable.lookup(var_name);
            int value=expression.eval(symTable,heap);
            if(heap.isDefined(address))
                heap.update(address,value);
            else
                throw new MyStmtExecException("Cannot write to heap.\nInvalid address.\n");
            return null;
        }
        catch (MyADTsException e){throw new MyStmtExecException("Cannot write to heap.\n"+e.getMessage());}
        catch (MyExpException e){throw new MyStmtExecException("Cannot write to heap.\n"+e.getMessage());}


    }

    @Override
    public String toString() {
        return "wh("+var_name+","+expression.toString()+")";
    }
}
