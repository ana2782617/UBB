package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            AnchorPane root= (AnchorPane) FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene scene=new Scene(root,710,585);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();


        }
        catch(Exception e){
            System.err.print(e.getMessage());
            e.printStackTrace();
        }
    }
}
