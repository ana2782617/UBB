package View;

import Model.Expressions.*;
import Model.Statements.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProgramsController implements Initializable{
    @FXML
    private ListView<String> stmtListView=new ListView<>();

    private ArrayList<IStmt> stmtList=new ArrayList<>();

    @FXML
    private Button runStatementButton=new Button();
    @FXML
    private Button exitApp=new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateStmtList();
    }

    public void populateStmtList() {

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

        /*ArrayList<String> stmtStringList=new ArrayList<>();
        for(IStmt i:stmtList){
            stmtStringList.add(i.toString());
        }
        */
        ObservableList<String> stmtStringList=FXCollections.observableArrayList(
                ex1.toString(),ex2.toString(),ex3.toString(),ex4.toString()
                ,ex5.toString(),ex6.toString(),ex7.toString(),ex8.toString()
                ,ex9.toString(),ex10.toString(),ex11.toString(),ex12.toString()
                ,ex13.toString());

        stmtListView.setItems(stmtStringList);

    }

    public ArrayList<IStmt> getStmtList() {
        return stmtList;
    }
    /*@FXML
    public void populatePrgList(){
        prgList=new ListView<String>();
        ObservableList<String> items= FXCollections.observableArrayList();
        items.add()
    }*/

    public void clickRunButton(){
        String item = this.stmtListView.getSelectionModel().getSelectedItem();
        //writes the statement in a file and opens the main window.
        try(PrintWriter stmtFile= new PrintWriter(new BufferedWriter(new FileWriter("Statement.in")));)
        {
            for(int i=1;i<=stmtList.size();i++)
                if(stmtList.get(i-1).toString().equals(item))
                    stmtFile.print(i);

            Stage mainStage=new Stage();
            AnchorPane mainRoot= (AnchorPane) FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene sceneMain=new Scene(mainRoot,700,300);
            mainStage.setScene(sceneMain);
            mainStage.show();
        }
        catch(IOException e){}


    }
    public void clickExitButton(){
        System.exit(0);
    }
}
