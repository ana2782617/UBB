package Model.MyADTs;

import Model.MyExceptions.MyADTsException;

import java.util.Map;

public interface MyIHeap<T2> {
    public int add(T2 v) throws MyADTsException;
    //public T2 get(T1 k);
    public T2 lookup(int k) throws MyADTsException;
    public void update(int k,T2 v) throws MyADTsException;
    public boolean isDefined(int k);
    public boolean isEmpty();
    public String toString();
    public void setContent(Map<Integer,T2> newheap);
    public Map<Integer,T2> getContent();
}
