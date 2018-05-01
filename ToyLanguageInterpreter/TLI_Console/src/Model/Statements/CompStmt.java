package Model.Statements;


import Model.MyADTs.MyIStack;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public class CompStmt implements IStmt {
    // first;second
    private IStmt first;
    private IStmt second;
    public CompStmt(IStmt fst,IStmt snd){
        first=fst;
        second=snd;
    }
    public IStmt getFirst() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }

    public String toString(){
        return "( "+first.toString()+"; "+second.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyStmtExecException{
        //doesn't evaluate nothing so we don't need to catch MyExpExceptions
        //we put the two statements onto the execution stack
        MyIStack<IStmt> stack=state.getExeStack();
        try {
            stack.push(second);
            stack.push(first);
            return null;
        }
        catch (MyADTsException e){
            throw new MyStmtExecException("Cannot execute compound statement."+e.getMessage());
        }
    }

}
