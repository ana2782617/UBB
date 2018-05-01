package Model.MyADTs;

public interface MyIList<T> {
    public void add(T t);
    public T get(int i);
    public boolean isEmpty();
    public String toString();
}
