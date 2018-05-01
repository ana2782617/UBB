package Model.Expressions;

import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyExpException;

public class NotExp extends Exp {
    private Exp expression;
    public NotExp(Exp expression){
        this.expression=expression;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) throws MyExpException {
        int value=expression.eval(tbl,heap);
        if(value==0) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "not("+expression.toString()+")";
    }
}
