package Model.Statements;

import Model.MyADTs.*;
import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

import java.io.BufferedReader;

public class ForkStmt implements IStmt {
    private IStmt statement;
    public ForkStmt(IStmt statement){
        this.statement=statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIStack<IStmt> exeStack=new MyStack<>();
        MyIDictionary<String,Integer> symTable=state.getSymTable().copySymTable();
        MyIHeap<Integer> heap=state.getHeap();
        MyIFileTable<String,BufferedReader> fileTable=state.getFileTable();
        MyIList<Integer> output=state.getOut();
        int id=state.getId()*10;
        PrgState prog=new PrgState(id,exeStack,symTable,output,fileTable,heap,statement);
        return prog;

    }

    @Override
    public String toString() {
        return "fork("+statement.toString()+")";
    }
}
