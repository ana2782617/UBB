package Model.Expressions;

import Model.MyADTs.MyIHeap;
import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyExpException;
import Model.MyADTs.MyIDictionary;

public class VarExp extends Exp {
    private String id;//a variable name

    public VarExp(String s){
        id=s;
    }

    public String getId() {
        return id;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> heap) throws MyExpException {
            try{
                return tbl.lookup(id);//same as tbl.get(String id)
            }
            catch(MyADTsException e)
            {
                throw new MyExpException("Variable not in Symbol Table."+e.getMessage());
            }
    }

    @Override
    public String toString() {
        return id;
    }

}
