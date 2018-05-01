package Model.Statements;

import Model.Expressions.Exp;
import Model.Expressions.NotExp;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class RepeatStmt implements IStmt{
    private Exp expression;
    private IStmt statement;

    public RepeatStmt(IStmt statement,Exp expression){
        this.statement=statement;
        this.expression=expression;
    }

    public IStmt getStatement() {
        return statement;
    }

    public Exp getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "repeat("+this.statement.toString()+")until("+this.expression.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try {
            Exp newExpression = new NotExp(this.expression);
            IStmt newStatement = new CompStmt(this.statement, new WhileStmt(newExpression, this.statement));
            state.getExeStack().push(newStatement);
        }
        catch(MyADTsException a){throw new MyStmtExecException("Cannot execute RepeatStatement.\n"+a.getMessage());}
        //we cannot get an ExpException here because it's resolved in WhileStmt
        return null;
    }
}
