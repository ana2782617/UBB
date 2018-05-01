package Model.Statements;


import Model.*;
import Model.Expressions.Exp;

import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;

public class AssignStmt implements IStmt {
    // id = expression
    private String id;
    private Exp expression;
    public AssignStmt(String i,Exp e){
        id=i;
        expression=e;
    }
    public String getId() {
        return id;
    }

    public Exp getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return id+" = "+expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        // we put the variable id and the value of the expression in the symbols table
        try {
            MyIStack<IStmt> stack = state.getExeStack(); //don't know why we need this here
            MyIDictionary<String, Integer> table = state.getSymTable();
            int value = expression.eval(table,state.getHeap());
            if (table.isDefined(id)) table.update(id, value);
            else table.add(id, value);
            return null;
        }
        catch (MyExpException ex){
            throw new MyStmtExecException("Cannot assign."+ex.getMessage());
        }
        catch (MyADTsException ea){
            throw new MyStmtExecException("Cannot assign."+ea.getMessage());
        }
    }
}
