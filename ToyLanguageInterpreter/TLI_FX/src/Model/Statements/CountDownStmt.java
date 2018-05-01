package Model.Statements;

import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class CountDownStmt implements IStmt {
    private String var;
    public CountDownStmt(String var){
        this.var=var;
    }

    public String getVar() {
        return var;
    }

    @Override
    public String toString() {
        return "countDown("+var+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try{
            int foundIndex=state.getSymTable().lookup(var);
            if(!state.getLatchTable().isDefined(foundIndex)){}
            else
            {   state.getLatchTable().lockLatchTable();
                if (state.getLatchTable().lookup(foundIndex)>0){
                    int number=state.getLatchTable().lookup(foundIndex);
                    state.getLatchTable().update(foundIndex,number-1);
                    state.getOut().add(state.getId());
                }
                state.getLatchTable().unlockLatchTable();
            }
        }
        catch(MyADTsException e){
            throw new MyStmtExecException("Cannot countDown.\n"+e.getMessage());
        }
        return null;
    }
}
