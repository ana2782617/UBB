package View;


import Controller.Controller;
import Model.Expressions.*;
import Model.MyADTs.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Utils.Tuple;
import Repository.IRepository;
import Repository.Repository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

class HeapEntry
{
    private final SimpleStringProperty value = new SimpleStringProperty("");
    private final SimpleStringProperty address = new SimpleStringProperty("");
    public HeapEntry(Map.Entry<Integer,Integer> me)
    {
        setAddress(me.getKey().toString());
        setValue(me.getValue().toString());
    }

    public void setAddress(String fName) {
        address.set(fName);
    }

    public void setValue(String fName) {
        value.set(fName);
    }
    public SimpleStringProperty valueProperty() {
        return value;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

}

class SymTableEntry
{
    private final SimpleStringProperty var_name = new SimpleStringProperty("");
    private final SimpleStringProperty number = new SimpleStringProperty("");
    public SymTableEntry(Map.Entry<String,Integer> me)
    {
        setVarName(me.getKey().toString());
        setNumber(me.getValue().toString());
    }

    public void setVarName(String fName) {
        var_name.set(fName);
    }

    public void setNumber(String fName) {
        number.set(fName);
    }
    public SimpleStringProperty varNameProperty() {
        return var_name;
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

}

class LatchTableEntry
{
    private final SimpleStringProperty location = new SimpleStringProperty("");
    private final SimpleStringProperty number = new SimpleStringProperty("");
    public LatchTableEntry(Map.Entry<Integer,Integer> me)
    {
        setLocation(me.getKey().toString());
        setNumber(me.getValue().toString());
    }

    public void setLocation(String fName) {
        location.set(fName);
    }

    public void setNumber(String fName) {
        number.set(fName);
    }
    public SimpleStringProperty locationProperty() {
        return location;
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

}

class FileTableEntry
{
    private final SimpleStringProperty identifier = new SimpleStringProperty("");
    private final SimpleStringProperty file_name = new SimpleStringProperty("");
    public FileTableEntry(Map.Entry<Integer,Tuple<String,BufferedReader>> me)
    {
        setIdentifier(me.getKey().toString());
        setFileName(me.getValue().getKey());
    }

    public void setIdentifier(String fName) {
        identifier.set(fName);
    }

    public void setFileName(String fName) {
        file_name.set(fName);
    }
    public SimpleStringProperty identifierProperty() {
        return identifier;
    }

    public SimpleStringProperty fileNameProperty() {
        return file_name;
    }

}

public class MainController implements Initializable{
    @FXML
    private ListView<String> stmtListView= new ListView<>();
    @FXML
    private ListView<String> exeList=new ListView<>();
    //private MyIStack<IStmt> exeStack=new MyStack<>();

    /*void populateExeList(){
        ObservableList<String> items =
        exeList.setItems();
    }*/

    @FXML
    private ListView<String> outList=new ListView<>();
    //private MyIList<Integer> output=new MyList<>();

    @FXML
    private TableView fileTableView=new TableView();
    @FXML
    private TableColumn<FileTableEntry,String> identifierColumn=new TableColumn<>("identifier");
    @FXML
    private TableColumn<FileTableEntry,String> fileNameColumn=new TableColumn<>("file name");

    //private MyIFileTable<String,BufferedReader> fileTable=new MyFileTable<>();

    @FXML
    private TableView heapTableView=new TableView();
    @FXML
    private TableColumn<HeapEntry,String> heapAddressColumn=new TableColumn<>("address");
    @FXML
    private TableColumn<HeapEntry,String> heapValueColumn=new TableColumn<>("value");

    //private MyIHeap<Integer> heapTable= new MyHeap<>();

    @FXML
    private TableView symbTableView=new TableView();
    @FXML
    private TableColumn<SymTableEntry,String> symNameColumn=new TableColumn<>("name");
    @FXML
    private TableColumn<SymTableEntry,String> symValueColumn=new TableColumn<>("value");

    //private MyIDictionary<String,Integer> symTable=new MyDictionary<>();

    @FXML
    private TableView latchTableView=new TableView();
    @FXML
    private TableColumn<LatchTableEntry,String> latchLocationColumn=new TableColumn<>("location");
    @FXML
    private TableColumn<LatchTableEntry,String> latchNumberColumn=new TableColumn<>("number");

    private IRepository repo;
    private Controller ctrl;

    @FXML
    private Button runOneStep=new Button();

    @FXML
    private Button runAll=new Button();

    @FXML
    private Button exitMain=new Button();

    @FXML
    private TextField nrPrgs =new TextField();

    @FXML
    private ListView<String> prgIDs = new ListView<>();

    private ArrayList<IStmt> stmtList= new ArrayList<>();

    private PrgState prg;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateStmtList();
        disableRunMain(true);
        //showRunMain(false);

    }
    private static int stmtid=-1;
    private void init_main(){
        disableRunMain(false);
        //if(stmtid==-1) ->exception

        try(PrintWriter logFile1= new PrintWriter(new BufferedWriter(new FileWriter("logFX.txt")));
        ) {}
        catch (IOException e){System.err.print(e.getMessage());}

        prg=new PrgState(stmtid,new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),

                new MyLatchTable<Integer>(),
                stmtList.get(stmtid-1));
        repo=new Repository("logFX.txt");
        repo.add(prg);
        ctrl=new Controller(repo);

        updateAll(1);
    }
    private void updateAll(int index){
        populateNrPrg();
        populatePrgIDs();
        index=index-1;
        populateExeList(index);
        populateSymTable(index);
        populateHeapTable(index);
        populateFileTable(index);
        populateOutput(index);
        populateLatchTable(index);
    }

    private void populatePrgIDs(){
        ArrayList<String> lst=new ArrayList<>();
        for(PrgState prg:ctrl.getRepo().getPrgList()){
            lst.add(prg.getId()+"");
        }
        ObservableList<String> prgIDsStringList= FXCollections.observableArrayList(lst);
        this.prgIDs.setItems(prgIDsStringList);
    }

    private void populateNrPrg(){
        this.nrPrgs.setText(ctrl.getRepo().getPrgList().size()+"");
    }

    private void populateExeList(int index){
        ArrayList<String> lst=new ArrayList<>();
        for(IStmt s:ctrl.getRepo().getPrgList().get(index).getExeStack().getContent()){
            lst.add(s.toString());
        }
        ObservableList<String> exeStackStringList= FXCollections.observableArrayList(lst);
        this.exeList.setItems(exeStackStringList);
    }
    private void populateSymTable(int index){
        this.symNameColumn.setCellValueFactory(cellData->cellData.getValue().varNameProperty());
        this.symValueColumn.setCellValueFactory(cellData->cellData.getValue().numberProperty());

        ArrayList<SymTableEntry> lst = new ArrayList<>();
        for (Map.Entry<String,Integer> entry:ctrl.getRepo().getPrgList().get(index).getSymTable().getContent().entrySet())
        {
            lst.add(new SymTableEntry(entry));
        }


        ObservableList<SymTableEntry> symTableString = FXCollections.observableArrayList(lst);
        this.symbTableView.getItems().setAll(lst);
    }
    private void populateHeapTable(int index){
        this.heapAddressColumn.setCellValueFactory(cellData->cellData.getValue().addressProperty());
        this.heapValueColumn.setCellValueFactory(cellData->cellData.getValue().valueProperty());

        ArrayList<HeapEntry> lst = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry:ctrl.getRepo().getPrgList().get(index).getHeap().getContent().entrySet())
        {
            lst.add(new HeapEntry(entry));
        }


        ObservableList<HeapEntry> heapTableString = FXCollections.observableArrayList(lst);
        this.heapTableView.setItems(heapTableString);
    }
    private void populateLatchTable(int index){
        this.latchLocationColumn.setCellValueFactory(cellData->cellData.getValue().locationProperty());
        this.latchNumberColumn.setCellValueFactory(cellData->cellData.getValue().numberProperty());

        ArrayList<LatchTableEntry> lst = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry:ctrl.getRepo().getPrgList().get(index).getLatchTable().getContent().entrySet())
        {
            lst.add(new LatchTableEntry(entry));
        }


        ObservableList<LatchTableEntry> latchTableString = FXCollections.observableArrayList(lst);
        this.latchTableView.setItems(latchTableString);
    }
    private void populateFileTable(int index){
        this.fileNameColumn.setCellValueFactory(cellData->cellData.getValue().fileNameProperty());
        this.identifierColumn.setCellValueFactory(cellData->cellData.getValue().identifierProperty());

        ArrayList<FileTableEntry> lst = new ArrayList<>();
        for (Map.Entry<Integer,Tuple<String,BufferedReader>>
                entry:ctrl.getRepo().getPrgList().get(index).getFileTable().getContent().entrySet())
        {
            lst.add(new FileTableEntry(entry));
        }


        ObservableList<FileTableEntry> fileTableString = FXCollections.observableArrayList(lst);
        this.fileTableView.setItems(fileTableString);
    }

    private void populateOutput(int index){
        ArrayList<String> lst=new ArrayList<>();
        for(Integer o:ctrl.getRepo().getPrgList().get(index).getOut().getContent()){
            lst.add(o.toString());
        }
        ObservableList<String> outListString=FXCollections.observableArrayList(lst);
        this.outList.setItems(outListString);
    }

    private void populateStmtList(){
        //v=2;print(v)
        IStmt ex1 = new CompStmt(new AssignStmt(
                "v",
                new ConstExp(2)
        ),
                new PrintStmt(new VarExp("v")));

        //a=2+3*5;b=a+1;print(b)
        IStmt ex2 = new CompStmt(
                new AssignStmt("a",
                        new ArithExp(new ConstExp(2), "+",
                                new ArithExp(new ConstExp(3), "*", new ConstExp(5))
                        )
                )
                , new CompStmt(
                new AssignStmt("b", new ArithExp(new VarExp("a"), "+", new ConstExp(1)))
                , new PrintStmt(new VarExp("b")))
        );


        //a=2-2;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(
                new AssignStmt("a", new ArithExp(new ConstExp(3), "/", new ConstExp(0)))
                , new CompStmt(
                new IfStmt(new VarExp("a")
                        , new AssignStmt("v", new ConstExp(2))
                        , new AssignStmt("v", new ConstExp(3)))
                , new PrintStmt(new VarExp("v")))
        );


        //v=2;a=10;(If (a-v) Then (print(a+1)) Else (print(v+10)));print(a+v)
        IStmt ex4 = new CompStmt(
                new AssignStmt("v", new ConstExp(2))
                , new CompStmt(
                new AssignStmt("a", new ConstExp(10))
                , new CompStmt(
                new IfStmt(new ArithExp(new VarExp("a"), "-", new VarExp("v"))
                        , new PrintStmt(new ArithExp(new VarExp("a"), "+", new ConstExp(1)))
                        , new PrintStmt(new ArithExp(new VarExp("v"), "+", new ConstExp(10)))
                )
                , new PrintStmt(new ArithExp(new VarExp("a"), "+", new VarExp("v")))
        )
        )
        );


        // openRFile(var_f,"test.in");
        // readFile(var_f,var_c);
        // print(var_c);
        // (if var_c
        //      then    readFile(var_f,var_c);
        //              print(var_c)
        //      else print(0)
        // );
        // closeRFile(var_f)
        IStmt ex5 = new CompStmt(
                new OpenRFile("var_f", "test.in")
                , new CompStmt(
                new ReadFile(new VarExp("var_f"), "var_c")
                , new CompStmt(
                new PrintStmt(new VarExp("var_c"))
                , new CompStmt(
                new IfStmt(new VarExp("var_c")
                        , new CompStmt(
                        new ReadFile(new VarExp("var_f"), "var_c")
                        , new PrintStmt(new VarExp("var_c")))
                        , new PrintStmt(new ConstExp(0))
                )
                , new CloseRFile(new VarExp("var_f"))
        )
        )
        )
        );


        //openRFile(var_f,"test.in");
        //readFile(var_f+2,var_c);
        // print(var_c);
        //(if var_c
        //      then    readFile(var_f,var_c);
        //              print(var_c)
        //      else print(0)
        // );
        //closeRFile(var_f)
        IStmt ex6 = new CompStmt(
                new OpenRFile("var_f", "test.in")
                , new CompStmt(
                new ReadFile(new ArithExp(new VarExp("var_f"), "+", new ConstExp(2)), "var_c")
                , new CompStmt(
                new PrintStmt(new VarExp("var_c"))
                , new CompStmt(
                new IfStmt(new VarExp("var_c")
                        , new CompStmt(
                        new ReadFile(new VarExp("var_f"), "var_c")
                        , new PrintStmt(new VarExp("var_c")))
                        , new PrintStmt(new ConstExp(0))
                )
                , new CloseRFile(new VarExp("var_f"))
        )
        )
        )
        );


        //v=10;new(v,20);new(a,22);print(v)
        IStmt ex7 = new CompStmt(
                new AssignStmt("v", new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a", new ConstExp(22)),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );


        //v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
        IStmt ex8 = new CompStmt(
                new AssignStmt("v", new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a", new ConstExp(22)),
                                new CompStmt(
                                        new PrintStmt(
                                                new ArithExp(new ConstExp(100), "+", new RHExp("v"))),
                                        new PrintStmt(
                                                new ArithExp(new ConstExp(100), "+", new RHExp("a"))
                                        )
                                )
                        )
                )
        );


        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a))
        IStmt ex9 = new CompStmt(
                new AssignStmt("v", new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a", new ConstExp(22)),
                                new CompStmt(
                                        new WHStmt("a", new ConstExp(30)),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new RHExp("a"))
                                        )
                                )
                        )
                )
        );


        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
        IStmt ex10 = new CompStmt(
                new AssignStmt("v", new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a", new ConstExp(22)),
                                new CompStmt(
                                        new WHStmt("a", new ConstExp(30)),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("a")),
                                                new CompStmt(
                                                        new PrintStmt(new RHExp("a")),
                                                        new AssignStmt("a", new ConstExp(0))
                                                )
                                        )
                                )
                        )
                )
        );


        // openRFile(var_f,"test.in");
        // readFile(var_f,var_c);
        // print(var_c);
        // (if var_c
        //      then    readFile(var_f,var_c);
        //              print(var_c)
        //      else print(0)
        // )
        IStmt ex11 = new CompStmt(
                new OpenRFile("var_f", "test.in")
                , new CompStmt(
                new ReadFile(new VarExp("var_f"), "var_c")
                , new CompStmt(
                new PrintStmt(new VarExp("var_c"))
                , new IfStmt(new VarExp("var_c")
                , new CompStmt(
                new ReadFile(new VarExp("var_f"), "var_c")
                , new PrintStmt(new VarExp("var_c")))
                , new PrintStmt(new ConstExp(0))
        )

        )
        )
        );


        //a=3;
        // while(a>0) (
        //    print(a);
        //    a=a-1;
        //  );
        //print(-1)
        IStmt ex12 = new CompStmt(
                new AssignStmt("a", new ConstExp(3)),
                new CompStmt(
                        new WhileStmt(new BoolExp(new VarExp("a"), ">", new ConstExp(0)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("a")),
                                        new AssignStmt("a", new ArithExp(new VarExp("a"), "-", new ConstExp(1)))
                                )),
                        new PrintStmt(new ConstExp(-1))
                )
        );


        //v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        IStmt ex13 = new CompStmt(
                new AssignStmt("v", new ConstExp(10)),
                new CompStmt(
                        new NewStmt("a", new ConstExp(22)),
                        new CompStmt(
                                new ForkStmt(new CompStmt(
                                        new WHStmt("a", new ConstExp(30))
                                        , new CompStmt(
                                        new AssignStmt("v", new ConstExp(32))
                                        , new CompStmt(
                                        new PrintStmt(new VarExp("v"))
                                        , new PrintStmt(new RHExp("a"))
                                )
                                )
                                )
                                )
                                , new CompStmt(
                                new PrintStmt(new VarExp("v"))
                                , new PrintStmt(new RHExp("a"))
                        )
                        )
                )
        );
        //v=0;
        //(repeat (fork(print(v);v=v-1);v=v+1) until v==3);
        //x=1;y=2;z=3;w=4;
        //print(v*10)
        //The final Out should be {0,1,2,30}
        IStmt ex14=new CompStmt(new AssignStmt("v",new ConstExp(0)),
                new CompStmt(new RepeatStmt(new CompStmt(
                        new ForkStmt(new CompStmt(
                                new PrintStmt(new VarExp("v"))
                                ,new AssignStmt("v",new ArithExp(new VarExp("v"),"-",new ConstExp(1)))))
                        ,new AssignStmt("v",new ArithExp(new VarExp("v"),"+",new ConstExp(1)))
                ),new BoolExp(new VarExp("v"),"==",new ConstExp(3)))
                        ,new CompStmt(new AssignStmt("x",new ConstExp(1))
                        ,new CompStmt(new AssignStmt("y",new ConstExp(2))
                        ,new CompStmt(new AssignStmt("z",new ConstExp(3))
                        ,new CompStmt(new AssignStmt("w",new ConstExp(4))
                        ,new PrintStmt(new ArithExp(new VarExp("v"),"*",new ConstExp(10)))
                )
                )
                )
                )
                )
        );
    //new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));
    //fork(wh(v1,rh(v1)*10));print(rh(v1));countDown(cnt);
    //fork(wh(v2,rh(v2)*10));print(rh(v2));countDown(cnt);
    //fork(wh(v3,rh(v3)*10));print(rh(v3));countDown(cnt))));
    //await(cnt);
    //print(100);
    //countDown(cnt);
    //print(100)
    //        The final Out should be {20,id-first-child,30,id-second-child,40, id-third-child,
    //                100,100} where id-first-child, id-second-child and id-third-child are the unique
    //        identifiers of those three new threads created by fork.
        IStmt ex15=new CompStmt(
                new NewStmt("v1",new ConstExp(2))
                ,new CompStmt(
                        new NewStmt("v2",new ConstExp(3))
                        ,new CompStmt(
                                 new NewStmt("v3",new ConstExp(4))
                                ,new CompStmt(
                                        new NewLatchStmt("cnt",new RHExp("v2"))
                                        ,new CompStmt(
                                                new ForkStmt(new CompStmt(
                                                        new WHStmt("v1",new ArithExp(new RHExp("v1"),"*",new ConstExp(10)))
                                                        ,new CompStmt(
                                                                new PrintStmt(new RHExp("v1"))
                                                                ,new CompStmt(new CountDownStmt("cnt")
                                                                    ,
                                                        new ForkStmt(new CompStmt(
                                                                new WHStmt("v2",new ArithExp(new RHExp("v2"),"*",new ConstExp(10)))
                                                                ,new CompStmt(
                                                                new PrintStmt(new RHExp("v2"))
                                                                ,new CompStmt(new CountDownStmt("cnt")
                                                                ,
                                                                new ForkStmt(new CompStmt(
                                                                        new WHStmt("v3",new ArithExp(new RHExp("v3"),"*",new ConstExp(10)))
                                                                        ,new CompStmt(
                                                                        new PrintStmt(new RHExp("v3"))
                                                                        ,new CountDownStmt("cnt"))))
                                                                )
                                                                ))

                                                            )
                                                        )
                                                    )
                                                    )
                                                )
                                                ,new CompStmt(
                                                        new AwaitStmt("cnt")
                                                        ,new CompStmt( new PrintStmt(new ConstExp(100))
                                                                ,new CompStmt(
                                                                        new CountDownStmt("cnt")
                                                                        ,new PrintStmt( new ConstExp(100))
                                                                        )
                                                                )
                                                        )
                                                )
                                )
        )   )   )
                ;

        this.stmtList.add(ex1);
        this.stmtList.add(ex2);
        this.stmtList.add(ex3);
        this.stmtList.add(ex4);
        this.stmtList.add(ex5);
        this.stmtList.add(ex6);
        this.stmtList.add(ex7);
        this.stmtList.add(ex8);
        this.stmtList.add(ex9);
        this.stmtList.add(ex10);
        this.stmtList.add(ex11);
        this.stmtList.add(ex12);
        this.stmtList.add(ex13);
        this.stmtList.add(ex14);
        this.stmtList.add(ex15);

        ObservableList<String> stmtStringList=FXCollections.observableArrayList(
                ex1.toString(),ex2.toString(),ex3.toString(),ex4.toString()
                ,ex5.toString(),ex6.toString(),ex7.toString(),ex8.toString()
                ,ex9.toString(),ex10.toString(),ex11.toString(),ex12.toString()
                ,ex13.toString(),ex14.toString(),ex15.toString());

        stmtListView.setItems(stmtStringList);
    }

    private ExecutorService executor;
    public void clickRunOneStep(){

        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList=ctrl.removeCompletedPrograms(ctrl.getRepo().getPrgList());
        if(prgList.size()>0){
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
                ctrl.getRepo().setPrgList(prgList);
                prgList.forEach(p->ctrl.getRepo().logPrgStateExec(p));
            }
            catch(Exception e) {}
            prgList=ctrl.removeCompletedPrograms(ctrl.getRepo().getPrgList());
            selectPrgToShow();
        }
        else{
            disableRunMain(true);
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);

    }
    public void clickRunAll(){
        //ctrl.allStep();
        //updateAll(1);
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList=ctrl.removeCompletedPrograms(ctrl.getRepo().getPrgList());
        while(prgList.size()>0){
            ////////////////////
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
                ctrl.getRepo().setPrgList(prgList);
                prgList.forEach(p->ctrl.getRepo().logPrgStateExec(p));
            }
            catch(Exception e) {}
            prgList=ctrl.removeCompletedPrograms(ctrl.getRepo().getPrgList());
            selectPrgToShow();
        }
        executor.shutdownNow();
        ctrl.getRepo().setPrgList(prgList);
        disableRunMain(true);
    }
    public void selectPrgToShow(){
        String item = this.prgIDs.getSelectionModel().getSelectedItem();
        int index=1;
        if(item!=null)
            for(int i=0;i<ctrl.getRepo().getPrgList().size();i++){
                if(ctrl.getRepo().getPrgList().get(i).getId()==Integer.parseInt(item))
                {
                    index=i+1;
                    break;
                }

            }
        updateAll(index);

    }
    public void clickExitMain(){
        Stage stage=(Stage) exitMain.getScene().getWindow();
        stage.close();
    }
    @FXML
    private Button runStatement=new Button();


    public void clickRunStatement(){
        String item = this.stmtListView.getSelectionModel().getSelectedItem();
        for(int i=0;i<stmtList.size();i++) {
            if(stmtList.get(i).toString().equals(item))
            {
                stmtid = i+1;
                break;
            }
        }
        //showRunMain(true);
        init_main();
    }
    private void disableRunMain(boolean value){
        runAll.setDisable(value);
        runOneStep.setDisable(value);
        prgIDs.setDisable(value);
    }
    private void showRunMain(boolean value){

            exeList.setVisible(value);

    }
}
