package Model.MyADTs;

import Model.MyExceptions.MyADTsException;
import Model.Utils.Tuple;

import java.util.Map;

public interface MyIFileTable<T1,T2> {
    // same as MyIDictionary
    public int add(T1 t1,T2 t2) throws MyADTsException;
    public T2 lookupBR(int fd) throws MyADTsException;
    public T1 lookupFN(int fd) throws MyADTsException;
    //public int update(T1 t1,T2 t2) throws MyADTsException;
    public boolean isDefined(T1 k);
    public boolean isEmpty();
    public void delete(int fd)throws MyADTsException;
    public String toString();
    public void setContent(Map<Integer,Tuple<T1,T2>> newFileTable);
    public Map<Integer,Tuple<T1,T2>> getContent();
}
