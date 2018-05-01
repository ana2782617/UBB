package Model.Expressions;


import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyExpException;


public abstract class Exp {
    public abstract int eval(MyIDictionary<String,Integer> tbl, MyIHeap<Integer> heap) throws MyExpException;
    public abstract String toString();
}