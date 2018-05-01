package Model.MyADTs;

//import java.util.Vector;
//import java.util.List;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{
    private ArrayList<T> list;

    public MyList(){
        list=new ArrayList<>();
    }
    public boolean isEmpty() {
        if(list.isEmpty()) return true;
        return false;
    }
    public void add(T t){
        list.add(t);
    }
    public T get(int i){
        return list.get(i);
    }
    public String toString(){
        String s="";
        for(T t:list) {
            s = s + t.toString() + "\n";
        }
        return s;
    }

}
