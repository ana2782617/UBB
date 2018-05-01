package Model.Expressions;

import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyExpException;

public class BoolExp extends Exp {
    //exp1 bool_operator exp2
    private Exp exp1;
    private Exp exp2;
    private String bool_operator;
    public BoolExp(Exp exp1,String bool_operator,Exp exp2){
        this.exp1=exp1;
        this.exp2=exp2;
        this.bool_operator=bool_operator;
    }
    public Exp getExp1() {
        return exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public String getOperator() {
        return bool_operator;
    }

    private boolean evalBool(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) throws MyExpException {
        if(bool_operator=="==") return exp1.eval(tbl,heap)==exp2.eval(tbl,heap);
        else if(bool_operator=="<") return exp1.eval(tbl,heap)<exp2.eval(tbl,heap);
        else if(bool_operator=="<=") return exp1.eval(tbl,heap)<=exp2.eval(tbl,heap);
        else if(bool_operator==">") return exp1.eval(tbl,heap)>exp2.eval(tbl,heap);
        else if(bool_operator==">=") return exp1.eval(tbl,heap)>=exp2.eval(tbl,heap);
        else if(bool_operator=="!=") return exp1.eval(tbl,heap)!=exp2.eval(tbl,heap);
        else throw new MyExpException("Cannot evaluate boolean expression. Operator not recognized.\n");
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) throws MyExpException {
        if(evalBool(tbl,heap)) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return exp1.toString()+bool_operator+exp2.toString();
    }
}
