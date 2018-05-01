package Model;

import Model.MyADTs.*;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyEmptyStackException;
import Model.MyExceptions.MyException;
import Model.MyExceptions.MyStmtExecException;
import Model.Statements.IStmt;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String,Integer> symTable;
    private MyIList<Integer> out;
    private MyIFileTable<String,BufferedReader> fileTable;
    private MyIHeap<Integer> heap;
    private IStmt originalProgram; //not actually the program
    private int id;

    public PrgState(int id,MyIStack<IStmt> e, MyIDictionary<String,Integer> s, MyIList<Integer> o,
                    MyIFileTable<String,BufferedReader> f,MyIHeap<Integer> h, IStmt p){
        this.id=id;
        exeStack=e;
        symTable=s;
        out=o;
        fileTable=f;
        heap=h;
        originalProgram=p;
        try {
            e.push(p);
        }
        catch (MyADTsException ea){}
    }
    public MyIStack<IStmt> getExeStack(){
        return exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer> getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String s="-------------------------------";
        s=s
                +"\nProg State - "+id+": "
                +"\nExe stack:\n"+exeStack.toString()
                +"\nSymbol Table:\n"+symTable.toString()
                +"\nOutput:\n"+out.toString()
                +"\nFile Table:\n"+fileTable.toString()
                +"\nHeap:\n"+heap.toString()
                +"\n";
        return s;
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException,MyEmptyStackException{
        if(exeStack.isEmpty()) throw new MyEmptyStackException("Execution Stack is empty.");
        try {
            IStmt crtStmt = exeStack.pop();
            return crtStmt.execute(this);
        }
        catch (MyStmtExecException ex){
            throw new MyException("/Cannot execute current statement."+ex.getMessage());
        }
        catch (MyADTsException ea){
            throw new MyException("/Cannot get current statement."+ea.getMessage());
        }
    }
}
