package Model.Statements;
import Model.*;
import Model.Expressions.Exp;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyExceptions.MyStmtExecException;
import Model.MyADTs.MyIStack;

public class IfStmt implements IStmt {
    //if expression then statement1
    //else statement2
    Exp expression;
    IStmt thenS;
    IStmt elseS;//momentan mereu avem else

    public IfStmt(Exp exp,IStmt t,IStmt e){
        expression=exp;
        thenS=t;
        elseS=e;
    }

    public Exp getExpression() {
        return expression;
    }

    public IStmt getElseS() {
        return elseS;
    }

    public IStmt getThenS() {
        return thenS;
    }

    @Override
    public String toString() {
        return "if ("+expression.toString()+") then ("+thenS.toString()+") else ("+elseS.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException{
        //evaluate expression; if value is 0 then then Statement is put onto the Execution Stack
        //else ElseStatement is put onto the ExeStack
        try {
            MyIStack<IStmt> stack = state.getExeStack(); //se modifica si exeStack din state
            int value = expression.eval(state.getSymTable(),state.getHeap());
            if (value != 0) stack.push(thenS);
            else stack.push(elseS);
            return null;
        }
        catch (MyExpException e){
            throw new MyStmtExecException("Cannot execute IF statement."+e.getMessage());
        }
        catch (MyADTsException ea){
            throw new MyStmtExecException("Cannot execute IF statement."+ea.getMessage());
        }
    }
}
