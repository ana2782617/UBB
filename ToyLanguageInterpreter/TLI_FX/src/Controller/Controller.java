package Controller;

import Model.MyExceptions.MyADTsException;
import Model.MyExceptions.MyEmptyStackException;
import Model.MyExceptions.MyException;
import Model.MyExceptions.MyStmtExecException;
import Model.Statements.IStmt;
import Model.MyADTs.MyIStack;
import Model.PrgState;
import Model.Utils.Tuple;
import Repository.IRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller {
    private IRepository repo;
    private boolean print_flag;
    private ExecutorService executor;
    public Controller(IRepository r){
        repo=r;
        print_flag=false;
    }
    public void setFlag(boolean t){
        print_flag=t;
    }

    public IRepository getRepo() {
        return repo;
    }
    public void addToRepo(PrgState p){
        repo.add(p);
    }

    /*public PrgState oneStep(PrgState ps) throws MyException,MyEmptyStackException{
        //PrgState ps=repo.getCrtPrg();
        MyIStack<IStmt> stk=ps.getExeStack();
        if(stk.isEmpty()) throw new MyEmptyStackException("Execution Stack is empty.");
        try {
            IStmt crtStmt = stk.pop();
            return crtStmt.execute(ps);
        }
        catch (MyStmtExecException ex){
            throw new MyException("/Cannot execute current statement."+ex.getMessage());
        }
        catch (MyADTsException ea){
            throw new MyException("/Cannot get current statement."+ea.getMessage());
        }
    }*/
    public void oneStepForAllPrgs(List<PrgState> prgList){

        List<Callable<PrgState>> callList= prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)(()->{return p.oneStep();}))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                                try {
                                    return future.get();
                                }
                                catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    return null;
                                }
                            }
                    )
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            repo.setPrgList(prgList);
            prgList.forEach(p->repo.logPrgStateExec(p));
        }
        catch(Exception e) {}

    }
    public void allStep() {//throws MyException{
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrograms(repo.getPrgList());
        while(prgList.size()>0){
            oneStepForAllPrgs(prgList);
            prgList=removeCompletedPrograms(repo.getPrgList());

        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
        /*
        PrgState crtPs=repo.getCrtPrg();
        try{
            while(true){
                oneStep(crtPs);
                crtPs.getHeap().setContent(
                        conservativeGarbageCollector(crtPs.getSymTable().getContent().values(),
                        crtPs.getHeap().getContent()));
                if(print_flag)//print states
                    repo.logPrgStateExec(crtPs);
            }
        }
        catch (MyException e){
            System.err.print(e.getMessage());
        }
        catch (MyEmptyStackException e){}
        finally{
            closeAllOpenFiles(crtPs.getFileTable().getContent());

        }*/
    }
    public Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTable_values, Map<Integer,Integer> heap){
        return heap.entrySet().stream()
                .filter(e->symTable_values.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public void closeAllOpenFiles(Map<Integer,Tuple<String,BufferedReader>> fileTable)  {
        fileTable.entrySet().stream()
                .forEach(e -> {
                            try {
                                System.out.println("Closing file "+e.getValue().getKey()+" ...");
                                e.getValue().getValue().close();
                                System.out.println("File closed.");
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                );
    }
    public List<PrgState> removeCompletedPrograms(List<PrgState> inPrgList){
        return inPrgList.stream().
                filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }
}
