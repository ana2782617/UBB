package View;

import Controller.Controller;
import Model.*;
import Model.Expressions.ArithExp;
import Model.Expressions.ConstExp;
import Model.Expressions.VarExp;
import Model.MyADTs.*;
import Model.MyExceptions.MyEmptyStackException;
import Model.MyExceptions.MyException;
import Model.Statements.*;
import Repository.*;

import java.io.*;
import java.util.Scanner;

public class MyView {
    public static void main(String args[]){
        //v=2;print(v)
        IStmt ex1=new CompStmt(new AssignStmt("v",new ConstExp(2)),new PrintStmt(new VarExp("v")));

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

        //a=2-2;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3=new CompStmt(
                new AssignStmt("a",new ArithExp(new ConstExp(3),"/",new ConstExp(0)))
                ,new CompStmt(
                        new IfStmt(new VarExp("a")
                                ,new AssignStmt("v",new ConstExp(2))
                                ,new AssignStmt("v",new ConstExp(3)))
                        ,new PrintStmt(new VarExp("v")))
                );

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

        MyIStack<IStmt> stk=new MyStack<IStmt>();
        MyIDictionary<String,Integer> symTable=new MyDictionary<String,Integer>();
        MyIList<Integer> out=new MyList<Integer>();
        MyIFileTable<String,BufferedReader> fileTable=new MyFileTable<String, BufferedReader>();
        MyIHeap<Integer> heap=new MyHeap<Integer>();



        System.out.println("What statement example do you wish to execute: 1-4\n");
        Scanner input = new Scanner(System.in);
        int nr_ex = input.nextInt();
        IStmt ex;
        switch (nr_ex) {
            case 1:{
                ex=ex1;
                break;
            }
            case 2:{
                ex=ex2;
                break;
            }
            case 3:{
                ex=ex3;
                break;
            }
            case 4:{
                ex=ex4;
                break;
            }
            case 5:{
                ex=ex5;
                break;
            }
            case 6:{
                ex=ex6;
                break;
            }
            default: {
                ex=ex1;
            }
        }
        PrgState prgState = new PrgState(1,stk, symTable, out, fileTable,heap, ex);

        //print to see the first prgstate
        //System.out.println("Initial program state:\n"+prgState.toString());

        IRepository repo = new Repository("PrgStateExec.txt");

        //I want to clear the file before i type in it
        try(PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter("PrgStateExec.txt")));)
        {}
        catch (IOException e){}

        Controller ctrl = new Controller(repo);
        ctrl.addToRepo(prgState);
        System.out.println("How do you want to see the program:\n\t1-step by step (writes to file)\n\t2-final result\n");
        int type_exec = input.nextInt();
        switch(type_exec){
            case 1:{
                ctrl.setFlag(true);
                ctrl.allStep();
                break;
                /*while(true){
                    ctrl.oneStep(prgState);
                    System.out.println(ctrl.getRepo().getCrtPrg().toString());
                }*/

            }
            case 2:{
                ctrl.setFlag(false);
                ctrl.allStep();
                //System.out.println(ctrl.getRepo().getCrtPrg().toString());
            }
        }



        /*catch (IOException e){
            System.err.print(e.getMessage());
        }*/
        /*
        catch (MyException e){
            System.err.print(e.getMessage());
        }
        catch (MyEmptyStackException e){}*/

    }
}
