package Model.MyADTs;

import Model.MyExceptions.MyADTsException;
import Model.Utils.Tuple;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class MyFileTable<T1,T2> implements MyIFileTable<T1,T2> {
    //the hash map contains the pairs of filenames and buffer readers
    private Map<Integer,Tuple<T1,T2>> table;
    private int fileDescriptor;

    public MyFileTable(){
        table=new HashMap<>();
        fileDescriptor=-1;
    }
    public int getFileDescriptor(){
        return fileDescriptor;
    }

    public int add(T1 t1,T2 t2) throws MyADTsException{
        //???

        if(table.containsValue(new Tuple<>(t1,t2))) throw new MyADTsException("Cannot add. Pair already exists in table.\n");
        fileDescriptor=0;
        while(table.containsKey(fileDescriptor))
            fileDescriptor++;

        table.put(fileDescriptor,new Tuple<>(t1,t2));
        return fileDescriptor;
    }

    /*public int update(T1 t1, T2 t2) throws MyADTsException {
        //???
        //if(!table.containsValue(new Pair<>(t1,t2))) throw new MyADTsException("Cannot update. Pair is not in table.\n");
        //table.replace()
        return fileDescriptor;
    }*/

    public T2 lookupBR(int fd) throws MyADTsException{
        //???
        if(!table.containsKey(fd)) throw new MyADTsException("Cannot lookup buffer. Table doesn't contain the given key.\n");
        return table.get(fd).getValue();
    }
    public T1 lookupFN(int fd) throws MyADTsException{
        //???
        if(!table.containsKey(fd)) throw new MyADTsException("Cannot lookup filename. Table doesn't contain the given key.\n");
        return table.get(fd).getKey();
    }
    public boolean isDefined(T1 k){
        //need to see if a filename is in filetable
        for(Tuple p:table.values()){
            if(p.getKey()== k) return true;

        }
        return false;
    }
    public boolean isEmpty(){
        return table.isEmpty();
    }
    public String toString(){
        // id-->filename
        String s="";
        //int i=0;
        for(Integer t:table.keySet()){

            s=s+t+"-->"+table.get(t).getKey()+"\n";
        }
        return s;
    }

    @Override
    public void delete(int fd) throws MyADTsException {
        if(!table.containsKey(fd)) throw new MyADTsException("Cannot remove.Key doesn't exist in table.\n");
        table.remove(fd);
    }
    @Override
    public void setContent(Map<Integer,Tuple<T1,T2>> newFileTable) {
        this.table=newFileTable;
    }

    @Override
    public Map<Integer, Tuple<T1,T2>> getContent() {
        return this.table;
    }
}
