package Model.Statements;

import Model.Expressions.Exp;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class NewLatchStmt implements IStmt{
    private String var;
    private Exp exp;
    public NewLatchStmt(String var,Exp exp){
        this.var=var;
        this.exp=exp;
    }

    @Override
    public String toString() {
        return "newLatch("+var+","+exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try{
            int number=exp.eval(state.getSymTable(),state.getHeap());
            state.getLatchTable().lockLatchTable();
            int newFreeLocation=state.getLatchTable().add(number);
            state.getLatchTable().unlockLatchTable();
            if(state.getSymTable().isDefined(var))
                state.getSymTable().update(var,newFreeLocation);
            else
                state.getSymTable().add(var,newFreeLocation);
        }
        catch(MyExpException e)
        {
            throw new MyStmtExecException("Cannot create new Latch.\n"+e.getMessage());
        }
        catch(MyADTsException e)
        {
            throw new MyStmtExecException("Cannot create new Latch.\n"+e.getMessage());
        }
        return null;
    }
}
