package Model.Statements;

import Model.Expressions.Exp;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class NewStmt implements IStmt {
    private String var_name;
    private Exp expression;

    public NewStmt(String var_name,Exp expression){
        this.var_name=var_name;
        this.expression=expression;
    }

    public Exp getExpression() {
        return expression;
    }

    public String getVar_name() {
        return var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try {
            int value = expression.eval(state.getSymTable(),state.getHeap());
            int location=state.getHeap().add(value);
            if(state.getSymTable().isDefined(var_name))
                state.getSymTable().update(var_name,location);
            else
                state.getSymTable().add(var_name,location);
        }
        catch(MyExpException e) {}
        catch(MyADTsException e) {}
        return null;
    }

    @Override
    public String toString() {
        return "new("+this.var_name+", "+this.expression.toString()+")";
    }
}
