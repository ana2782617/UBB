package View;

import Controller.Controller;
import Model.Expressions.*;
import Model.MyADTs.*;
import Model.PrgState;
import Model.Statements.*;
import Repository.IRepository;
import Repository.Repository;
import View.Commands.ExitCommand;
import View.Commands.RunExample;

import java.io.*;

public class Interpreter {
    public static void main(String args[]){
        //v=2;print(v)
        IStmt ex1=new CompStmt(new AssignStmt("v",new ConstExp(2)),new PrintStmt(new VarExp("v")));

        PrgState prgState1 = new PrgState(1,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex1);
        IRepository repo1=new Repository("log1.txt");
        Controller ctrl1=new Controller(repo1);
        ctrl1.addToRepo(prgState1);

        //a=2+3*5;b=a+1;print(b)
        IStmt ex2=new CompStmt(
                new AssignStmt("a",
                        new ArithExp(new ConstExp(2),"+",
                                new ArithExp(new ConstExp(3),"*",new ConstExp(5))
                        )
                )
                ,new CompStmt(
                new AssignStmt("b",new ArithExp(new VarExp("a"),"+",new ConstExp(1)))
                ,new PrintStmt(new VarExp("b")))
        );

        PrgState prgState2 = new PrgState(2,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex2);
        IRepository repo2=new Repository("log2.txt");
        Controller ctrl2=new Controller(repo2);
        ctrl2.addToRepo(prgState2);

        //a=2-2;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3=new CompStmt(
                new AssignStmt("a",new ArithExp(new ConstExp(3),"/",new ConstExp(0)))
                ,new CompStmt(
                new IfStmt(new VarExp("a")
                        ,new AssignStmt("v",new ConstExp(2))
                        ,new AssignStmt("v",new ConstExp(3)))
                ,new PrintStmt(new VarExp("v")))
        );

        PrgState prgState3 = new PrgState(3,
                new MyStack<IStmt>(),
                new MyDictionary<String, Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex3);
        IRepository repo3=new Repository("log3.txt");
        Controller ctrl3=new Controller(repo3);
        ctrl3.addToRepo(prgState3);

        //v=2;a=10;(If (a-v) Then (print(a+1)) Else (print(v+10)));print(a+v)
        IStmt ex4=new CompStmt(
                new AssignStmt("v",new ConstExp(2))
                ,new CompStmt(
                new AssignStmt("a",new ConstExp(10))
                ,new CompStmt(
                new IfStmt(new ArithExp(new VarExp("a"),"-",new VarExp("v"))
                        ,new PrintStmt(new ArithExp(new VarExp("a"),"+",new ConstExp(1)))
                        ,new PrintStmt(new ArithExp(new VarExp("v"),"+",new ConstExp(10)))
                )
                ,new PrintStmt(new ArithExp(new VarExp("a"),"+",new VarExp("v")))
        )
        )
        );
        PrgState prgState4 = new PrgState(4,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex4);
        IRepository repo4=new Repository("log4.txt");
        Controller ctrl4=new Controller(repo4);
        ctrl4.addToRepo(prgState4);

        // openRFile(var_f,"test.in");
        // readFile(var_f,var_c);
        // print(var_c);
        // (if var_c
        //      then    readFile(var_f,var_c);
        //              print(var_c)
        //      else print(0)
        // );
        // closeRFile(var_f)
        IStmt ex5=new CompStmt(
                new OpenRFile("var_f","test.in")
                ,new CompStmt(
                new ReadFile(new VarExp("var_f"),"var_c")
                ,new CompStmt(
                new PrintStmt(new VarExp("var_c"))
                ,new CompStmt(
                new IfStmt(new VarExp("var_c")
                        ,new CompStmt(
                        new ReadFile(new VarExp("var_f"),"var_c")
                        ,new PrintStmt(new VarExp("var_c")))
                        ,new PrintStmt(new ConstExp(0))
                )
                , new CloseRFile(new VarExp("var_f"))
        )
        )
        )
        );

        PrgState prgState5 = new PrgState(5,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex5);
        IRepository repo5=new Repository("log5.txt");
        Controller ctrl5=new Controller(repo5);
        ctrl5.addToRepo(prgState5);

        //openRFile(var_f,"test.in");
        //readFile(var_f+2,var_c);
        // print(var_c);
        //(if var_c
        //      then    readFile(var_f,var_c);
        //              print(var_c)
        //      else print(0)
        // );
        //closeRFile(var_f)
        IStmt ex6=new CompStmt(
                new OpenRFile("var_f","test.in")
                ,new CompStmt(
                new ReadFile(new ArithExp(new VarExp("var_f"),"+",new ConstExp(2)),"var_c")
                ,new CompStmt(
                new PrintStmt(new VarExp("var_c"))
                ,new CompStmt(
                new IfStmt(new VarExp("var_c")
                        ,new CompStmt(
                        new ReadFile(new VarExp("var_f"),"var_c")
                        ,new PrintStmt(new VarExp("var_c")))
                        ,new PrintStmt(new ConstExp(0))
                )
                ,new CloseRFile(new VarExp("var_f"))
        )
        )
        )
        );

        PrgState prgState6 = new PrgState(6,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex6);
        IRepository repo6=new Repository("log6.txt");
        Controller ctrl6=new Controller(repo6);
        ctrl6.addToRepo(prgState6);

        //v=10;new(v,20);new(a,22);print(v)
        IStmt ex7= new CompStmt(
                new AssignStmt("v",new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v",new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a",new ConstExp(22)),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        PrgState prgState7 = new PrgState(7,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex7);
        IRepository repo7=new Repository("log7.txt");
        Controller ctrl7=new Controller(repo7);
        ctrl7.addToRepo(prgState7);

        //v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
        IStmt ex8=new CompStmt(
                new AssignStmt("v",new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v",new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a",new ConstExp(22)),
                                new CompStmt(
                                        new PrintStmt(
                                                new ArithExp(new ConstExp(100),"+",new RHExp("v"))),
                                        new PrintStmt(
                                                new ArithExp(new ConstExp(100),"+",new RHExp("a"))
                                        )
                                )
                        )
                )
        );
        PrgState prgState8 = new PrgState(8,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex8);
        IRepository repo8=new Repository("log8.txt");
        Controller ctrl8=new Controller(repo8);
        ctrl8.addToRepo(prgState8);

        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a))
        IStmt ex9=new CompStmt(
                new AssignStmt("v",new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v",new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a",new ConstExp(22)),
                                new CompStmt(
                                        new WHStmt("a",new ConstExp(30)),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new RHExp("a"))
                                        )
                                )
                        )
                )
        );
        PrgState prgState9 = new PrgState(9,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex9);
        IRepository repo9=new Repository("log9.txt");
        Controller ctrl9=new Controller(repo9);
        ctrl9.addToRepo(prgState9);

        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
        IStmt ex10=new CompStmt(
                new AssignStmt("v",new ConstExp(10)),
                new CompStmt(
                        new NewStmt("v",new ConstExp(20)),
                        new CompStmt(
                                new NewStmt("a",new ConstExp(22)),
                                new CompStmt(
                                        new WHStmt("a",new ConstExp(30)),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("a")),
                                                new CompStmt(
                                                        new PrintStmt(new RHExp("a")),
                                                        new AssignStmt("a",new ConstExp(0))
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prgState10 = new PrgState(10,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex10);
        IRepository repo10=new Repository("log10.txt");
        Controller ctrl10=new Controller(repo10);
        ctrl10.addToRepo(prgState10);

        // openRFile(var_f,"test.in");
        // readFile(var_f,var_c);
        // print(var_c);
        // (if var_c
        //      then    readFile(var_f,var_c);
        //              print(var_c)
        //      else print(0)
        // )
        IStmt ex11=new CompStmt(
                new OpenRFile("var_f","test.in")
                ,new CompStmt(
                new ReadFile(new VarExp("var_f"),"var_c")
                ,new CompStmt(
                new PrintStmt(new VarExp("var_c"))
                ,new IfStmt(new VarExp("var_c")
                        ,new CompStmt(
                        new ReadFile(new VarExp("var_f"),"var_c")
                        ,new PrintStmt(new VarExp("var_c")))
                        ,new PrintStmt(new ConstExp(0))
                )

        )
        )
        );

        PrgState prgState11 = new PrgState(11,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex11);
        IRepository repo11=new Repository("log11.txt");
        Controller ctrl11=new Controller(repo11);
        ctrl11.addToRepo(prgState11);

        //a=3;
        // while(a>0) (
        //    print(a);
        //    a=a-1;
        //  );
        //print(-1)
        IStmt ex12=new CompStmt(
                new AssignStmt("a",new ConstExp(3)),
                new CompStmt(
                        new WhileStmt(new BoolExp(new VarExp("a"),">",new ConstExp(0)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("a")),
                                        new AssignStmt("a",new ArithExp(new VarExp("a"),"-",new ConstExp(1)))
                                )),
                        new PrintStmt(new ConstExp(-1))
                )
        );

        PrgState prgState12 = new PrgState(12,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex12);
        IRepository repo12=new Repository("log12.txt");
        Controller ctrl12=new Controller(repo12);
        ctrl12.addToRepo(prgState12);

        //v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        IStmt ex13=new CompStmt(
                new AssignStmt("v",new ConstExp(10)),
                new CompStmt(
                        new NewStmt("a",new ConstExp(22)),
                        new CompStmt(
                                new ForkStmt(new CompStmt(
                                        new WHStmt("a",new ConstExp(30))
                                        ,new CompStmt(
                                                new AssignStmt("v",new ConstExp(32))
                                                ,new CompStmt(
                                                        new PrintStmt(new VarExp("v"))
                                                        ,new PrintStmt(new RHExp("a"))
                                                )
                                        )
                                    )
                                )
                                ,new CompStmt(
                                new PrintStmt(new VarExp("v"))
                                ,new PrintStmt(new RHExp("a"))
                        )
                        )
                )
        );

        PrgState prgState13 = new PrgState(13,
                new MyStack<IStmt>(),
                new MyDictionary<String,Integer>(),
                new MyList<Integer>(),
                new MyFileTable<String, BufferedReader>(),
                new MyHeap<Integer>(),
                ex13);
        IRepository repo13=new Repository("log13.txt");
        Controller ctrl13=new Controller(repo13);
        ctrl13.addToRepo(prgState13);

        //I want to clear the files before i type in them
        try(PrintWriter logFile1= new PrintWriter(new BufferedWriter(new FileWriter("log1.txt")));
            PrintWriter logFile2= new PrintWriter(new BufferedWriter(new FileWriter("log2.txt")));
            PrintWriter logFile3= new PrintWriter(new BufferedWriter(new FileWriter("log3.txt")));
            PrintWriter logFile4= new PrintWriter(new BufferedWriter(new FileWriter("log4.txt")));
            PrintWriter logFile5= new PrintWriter(new BufferedWriter(new FileWriter("log5.txt")));
            PrintWriter logFile6= new PrintWriter(new BufferedWriter(new FileWriter("log6.txt")));
            PrintWriter logFile7= new PrintWriter(new BufferedWriter(new FileWriter("log7.txt")));
            PrintWriter logFile8= new PrintWriter(new BufferedWriter(new FileWriter("log8.txt")));
            PrintWriter logFile9= new PrintWriter(new BufferedWriter(new FileWriter("log9.txt")));
            PrintWriter logFile10= new PrintWriter(new BufferedWriter(new FileWriter("log10.txt")));
            PrintWriter logFile11= new PrintWriter(new BufferedWriter(new FileWriter("log11.txt")));
            PrintWriter logFile12= new PrintWriter(new BufferedWriter(new FileWriter("log12.txt")));
            PrintWriter logFile13= new PrintWriter(new BufferedWriter(new FileWriter("log13.txt")));
                ) {}
        catch (IOException e){}

        TextMenu menu=new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctrl2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctrl3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctrl4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctrl5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctrl6));
        menu.addCommand(new RunExample("7",ex7.toString(),ctrl7));
        menu.addCommand(new RunExample("8",ex8.toString(),ctrl8));
        menu.addCommand(new RunExample("9",ex9.toString(),ctrl9));
        menu.addCommand(new RunExample("10",ex10.toString(),ctrl10));
        menu.addCommand(new RunExample("11",ex11.toString(),ctrl11));
        menu.addCommand(new RunExample("12",ex12.toString(),ctrl12));
        menu.addCommand(new RunExample("13",ex13.toString(),ctrl13));
        menu.show();
    }
}
