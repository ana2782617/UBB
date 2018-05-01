package Model.MyADTs;

import Model.MyExceptions.MyADTsException;

import java.util.Map;

public interface MyILatchTable<T2> {
    int add(T2 t) throws MyADTsException;
    public String toString();
    public void setContent(Map<Integer,T2> newlatchTable);
    public Map<Integer,T2> getContent();
    public boolean isDefined(int k);
    public T2 lookup(int k) throws MyADTsException;
    public void update(int k,T2 v) throws MyADTsException;
    public void lockLatchTable();
    public void unlockLatchTable();
}
