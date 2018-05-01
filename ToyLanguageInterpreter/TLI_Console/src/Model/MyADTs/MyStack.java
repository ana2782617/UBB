package Model.MyADTs;

import Model.MyExceptions.MyADTsException;
import Model.Statements.IStmt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){//?
        stack=new Stack<T>();
    }

    public T pop() throws MyADTsException{
        if(stack.empty()) throw new MyADTsException("Stack is empty. Cannot pop.");
            return stack.pop();

    }

    public void push(T t) throws MyADTsException{
        if(stack.size()==stack.capacity()) throw new MyADTsException("Stack is full. Cannot push.");
        stack.push(t);
    }

    public String toString(){
        String s="";
        Stack<T> stk=new Stack<>();
        for(T t:stack)
            stk.push(t);
        while(!stk.isEmpty()){
            T t=stk.pop();
            s=s+t.toString()+"\n";
        }
        return s;
    }

    @Override
    public boolean isEmpty() {
        if(stack.isEmpty()) return true;
        return false;
    }
}
