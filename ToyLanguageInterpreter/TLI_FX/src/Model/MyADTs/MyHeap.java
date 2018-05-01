package Model.MyADTs;

import Model.MyExceptions.MyADTsException;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<T2> implements MyIHeap<T2> {
    private Map<Integer,T2> mappings;
    private int newFreeLocation;
    public MyHeap(){
        mappings=new HashMap<>();
        newFreeLocation=1;
    }

    private int getNewFreeLocation() {
        int f=1;
        while(mappings.containsKey(f))
            f++;
        return f;
    }

    public int add(T2 v) throws MyADTsException{
        newFreeLocation=getNewFreeLocation();
        if(mappings.containsKey(newFreeLocation)) throw new MyADTsException("Cannot add to heap. Key already in map.\n");
        mappings.put(newFreeLocation,v);


        return newFreeLocation;
    }
    public T2 lookup(int k) throws MyADTsException{
        if(!mappings.containsKey(k)) throw new MyADTsException("Cannot lookup in heap. Key not in map.\n");
        return mappings.get(k);
    }
    public void update(int k,T2 v) throws MyADTsException{
        if(!mappings.containsKey(k)) throw new MyADTsException("Cannot update to heap. Key not in map.\n");
        mappings.replace(k,v);
    }
    public boolean isDefined(int k){
        return this.mappings.containsKey(k);
    }
    public boolean isEmpty(){
        return this.mappings.isEmpty();
    }
    public String toString(){
        String s="";
        for(int k:mappings.keySet()){
            s=s+k+"->"+mappings.get(k).toString()+"\n";
        }
        return s;
    }

    @Override
    public void setContent(Map<Integer, T2> newheap) {
        mappings=newheap;
    }

    @Override
    public Map<Integer, T2> getContent() {
        return mappings;
    }
}
