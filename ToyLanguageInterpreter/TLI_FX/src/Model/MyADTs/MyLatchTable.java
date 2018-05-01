package Model.MyADTs;

import Model.MyExceptions.MyADTsException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLatchTable<T2> implements MyILatchTable<T2> {
    private Map<Integer,T2> mappings;
    private int newFreeLocation;
    private Lock lock=new ReentrantLock();
    public MyLatchTable(){
        mappings=new HashMap<>();
        newFreeLocation=1;
    }
    private int getNewFreeLocation() {
        int f=1;
        while(mappings.containsKey(f))
            f++;
        return f;
    }

    @Override
    public int add(T2 t) throws MyADTsException{
        //need to add synchronization
        newFreeLocation=getNewFreeLocation();
        if(mappings.containsKey(newFreeLocation)) throw new MyADTsException("Cannot add to heap. Key already in map.\n");
        mappings.put(newFreeLocation,t);


        return newFreeLocation;
    }
    public String toString(){
        String s="";
        for(int k:mappings.keySet()){
            s=s+k+"->"+mappings.get(k).toString()+"\n";
        }
        return s;
    }
    public void lockLatchTable(){
        lock.lock();
    }
    public void unlockLatchTable(){
        lock.unlock();
    }

    @Override
    public void setContent(Map<Integer, T2> newlatchTable) {
        mappings=newlatchTable;
    }

    @Override
    public Map<Integer, T2> getContent() {
        return mappings;
    }
    public boolean isDefined(int k){
        return this.mappings.containsKey(k);
    }
    public T2 lookup(int k) throws MyADTsException{
        if(!mappings.containsKey(k)) throw new MyADTsException("Cannot lookup in heap. Key not in map.\n");
        return mappings.get(k);
    }
    public void update(int k,T2 v) throws MyADTsException{
        if(!mappings.containsKey(k)) throw new MyADTsException("Cannot update to heap. Key not in map.\n");
        mappings.replace(k,v);
    }
}
