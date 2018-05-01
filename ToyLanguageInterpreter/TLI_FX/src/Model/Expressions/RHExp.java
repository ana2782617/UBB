package Model.Expressions;

import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;

public class RHExp extends Exp {
    private String var_name;
    public RHExp(String var_name){
        this.var_name=var_name;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) throws MyExpException {
        try{
            int address=tbl.lookup(var_name);
            int value=heap.lookup(address);
            return value;
        }
        catch(MyADTsException e){throw new MyExpException("Cannot evaluate readHeap expression.\n"+e.getMessage());}

    }

    public String getVar_name() {
        return var_name;
    }

    @Override
    public String toString() {
        return "rH("+var_name+")";
    }
}
