package Model.Statements;

import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class AwaitStmt implements IStmt{
    private String var;
    public AwaitStmt(String var){
        this.var=var;
    }

    public String getVar() {
        return var;
    }

    @Override
    public String toString() {
        return "await("+var+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try{
            int foundIndex=state.getSymTable().lookup(var);
            if(!state.getLatchTable().isDefined(foundIndex)){
                throw new MyStmtExecException("Cannot await.Index not defined.\n");
            }
            else {
                state.getLatchTable().lockLatchTable();
                if(state.getLatchTable().lookup(foundIndex)==0) {}
                else
                {
                    state.getExeStack().push(new AwaitStmt(var));
                }
                state.getLatchTable().unlockLatchTable();
            }
        }
        catch(MyADTsException e)
        {
            throw new MyStmtExecException("Cannot execute Await statement.\n"+e.getMessage());
        }
        return null;
    }
}
