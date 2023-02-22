package test.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoadMazeView;

public class Test_LoadMazeView extends Application
{
    public static void main(String[] args) 
    {
        launch(args);    
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        LoadMazeView loadMazeView = new LoadMazeView();
        Scene scene = new Scene(loadMazeView);
        stage.setScene(scene);
        stage.setTitle("MazeBot");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.show();
    }
}
