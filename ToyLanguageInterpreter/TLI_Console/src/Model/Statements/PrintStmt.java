package Model.Statements;

import Model.Expressions.Exp;
import Model.MyADTs.MyIList;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class PrintStmt implements IStmt {
    // print(expression)
    private Exp expression;
    public PrintStmt(Exp exp){
        expression=exp;
    }
    public Exp getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "print("+expression.toString()+") ";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException{
        //puts the expression in the out list
        try {
            MyIList<Integer> list = state.getOut();
            list.add(expression.eval(state.getSymTable(),state.getHeap()));
            return null;
        }
        catch(MyExpException e){
            throw new MyStmtExecException("Cannot print."+e.getMessage());
        }
    }
}
