package Model.MyADTs;
import Model.MyExceptions.MyADTsException;

import java.util.Map;

public interface MyIDictionary<T1,T2> {
    public void add(T1 k,T2 v) throws MyADTsException;
    //public T2 get(T1 k);
    public T2 lookup(T1 k) throws MyADTsException;
    public void update(T1 k,T2 v) throws MyADTsException;
    public boolean isDefined(T1 k);
    public boolean isEmpty();
    public String toString();
    public Map<T1,T2> getContent();
    public MyIDictionary<T1,T2> copySymTable();
    public void setContent(Map<T1,T2> dictionary);
}
