package Repository;

import Model.PrgState;

//import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Repository implements IRepository{
    private List<PrgState> states;
    private int crtPrgStateIndex=0;
    private String logFilePath;

    public Repository(String filename){
        states=new ArrayList<>();
        logFilePath=filename;
    }

    public List<PrgState> getPrgList() {
        return states;
    }

    public void setPrgList(List<PrgState> newstates){
        states=newstates;
    }


    public int getCrtPrgStateIndex() {
        return crtPrgStateIndex;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void add(PrgState p){
        states.add(p);
    }

    public void logPrgStateExec(PrgState p){
        try(PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));) {
            //logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.print(p.toString());
        }
        catch (IOException e){}

    }

    public PrgState getCrtPrg() {
        return states.get(crtPrgStateIndex);
    }
}
