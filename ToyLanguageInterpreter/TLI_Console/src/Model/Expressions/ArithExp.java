package Model.Expressions;

import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyException;
import Model.MyADTs.MyIDictionary;
import Model.MyExceptions.MyExpException;

public class ArithExp extends Exp{
    //exp1 operator exp2
    private Exp exp1;
    private Exp exp2;
    private String operator;// 1 = + ; 2 = - ; 3 = * ; 4 = /
    public ArithExp(Exp e1,String op,Exp e2){
        exp1=e1;
        exp2=e2;
        operator=op;
    }
    public Exp getExp1() {
        return exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) throws MyExpException {
        if (operator=="/" && exp2.eval(tbl,heap)==0)
            throw new MyExpException("Division by 0.");
        if(operator=="+") return exp1.eval(tbl,heap)+exp2.eval(tbl,heap);
        else if(operator=="-") return exp1.eval(tbl,heap)-exp2.eval(tbl,heap);
        else if(operator=="*") return exp1.eval(tbl,heap)*exp2.eval(tbl,heap);
        else return exp1.eval(tbl,heap)/exp2.eval(tbl,heap);
    }

    @Override
    public String toString() {
        return exp1.toString()+operator+exp2.toString();
    }
}
