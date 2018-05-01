package Model.MyADTs;

//import java.util.Dictionary;
import Model.MyExceptions.MyADTsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2> {
    private Map<T1,T2> dictionary;//

    public MyDictionary(){
        dictionary=new HashMap<T1,T2>();
    }

    /*public HashMap<T1, T2> getDictionary() {
        return dictionary;
    }*/

    public void add(T1 k, T2 v)throws MyADTsException {
        if(!dictionary.containsKey(k))
            dictionary.put(k,v);
        else throw new MyADTsException("Cannot add. Key already exists.");
    }

    /*public T2 get(T1 k){
        return dictionary.get(k);
    }*/
    public T2 lookup(T1 k) throws MyADTsException{
        if(!dictionary.containsKey(k)) {
            throw new MyADTsException("Variable not defined.");
            //dictionary.put(k, null); wrong because if lookup(x,{a->2;v->3;}) it shouldn't put x in the table
        }
        return dictionary.get(k);
        //return dictionary.putIfAbsent(k,null);
    }
    public boolean isEmpty(){
        if(dictionary.isEmpty()) return true;
        return false;
    }

    public void remove(T1 k){
        dictionary.remove(k);
    }
    public void update(T1 k,T2 v) throws MyADTsException{
        if(dictionary.containsKey(k))
            dictionary.replace(k,v);
        else throw new MyADTsException("Cannot update.");
    }
    public boolean isDefined(T1 k){
        if(dictionary.containsKey(k)) return true;
        return false;
    }
    public String toString(){
        String s="";
        Set<T1> setOfKeys=dictionary.keySet();
        for(T1 k:setOfKeys){
            s=s+k+"-->"+dictionary.get(k)+"\n";
        }
        return s;
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    public void setContent(Map<T1,T2> dictionary) {
        this.dictionary=dictionary;
    }

    @Override
    public MyIDictionary<T1, T2> copySymTable() {
        MyIDictionary<T1,T2> symTable=new MyDictionary<>();
        for(Map.Entry<T1,T2> t : this.dictionary.entrySet()){
            try{symTable.add(t.getKey(),t.getValue());}
            catch(Exception e){}
        }
        return symTable;
    }
}

