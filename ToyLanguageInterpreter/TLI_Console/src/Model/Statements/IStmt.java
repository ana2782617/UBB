package Model.Statements;

import Model.MyExceptions.MyStmtExecException;
import Model.PrgState;

public interface IStmt {
    public String toString();
    public PrgState execute(PrgState state) throws MyStmtExecException;
}
