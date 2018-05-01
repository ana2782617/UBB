package Model.Statements;

import Model.Expressions.Exp;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class WhileStmt implements IStmt {
    private Exp expression;
    private IStmt statement;

    public WhileStmt(Exp expression, IStmt statement){
        this.expression=expression;
        this.statement=statement;
    }

    public Exp getExpression() {
        return expression;
    }

    public IStmt getStatement() {
        return statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try{
            int value= expression.eval(state.getSymTable(),state.getHeap());
            if (value!=0) {
                state.getExeStack().push(new WhileStmt(expression, statement));
                state.getExeStack().push(statement);
            }

            return null;
        }
        catch (MyExpException e) {throw new MyStmtExecException("Cannot execute while statement.\n"+e.getMessage());}
        catch (MyADTsException e) {throw new MyStmtExecException("Cannot execute while statement.\n"+e.getMessage());}
    }

    @Override
    public String toString() {
        return "( while("+expression.toString()+") "+statement.toString()+" ) ";
    }

}
