package Model.Expressions;


import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;

public class ConstExp extends Exp {
    private int number;//constant

    public ConstExp(int n){
        number=n;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) {
        return number;
    }
}
