package gui;

import cgp.simulation.InputParams;
import cgp.functions.Add;
import cgp.functions.Function;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Function f = new Add<Double>();
        System.out.println(f.calculate(1,3));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        // my main.css is src/main/resources
        Parent root = loader.load();
        primaryStage.setTitle("StreamMate");
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        InputParams params = InputParams.getInstance();
        primaryStage.setScene(new Scene(root, params.getWidth(), params.getHeight()));
    }

    @Override
    public void stop(){

    }


    public static void main(String[] args) {
        launch(args);
    }
}
