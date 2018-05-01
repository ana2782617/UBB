package Repository;

import Model.PrgState;

import java.util.List;

public interface IRepository {
    //public PrgState getCrtPrg();
    public void add(PrgState p);
    public void logPrgStateExec(PrgState p);
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> newstates);

}
