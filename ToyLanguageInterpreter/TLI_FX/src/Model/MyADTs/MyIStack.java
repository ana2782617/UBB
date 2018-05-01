package Model.MyADTs;

import Model.MyExceptions.MyADTsException;
import Model.Statements.IStmt;

import java.util.Stack;

public interface MyIStack<T> {
    public T pop() throws MyADTsException;//throw exception
    public void push(T t)throws MyADTsException;
    public boolean isEmpty();
    public String toString();
    Stack<T> getContent();
}
