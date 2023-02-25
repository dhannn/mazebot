package core;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.Controller;

public class App extends Application
{
    private final double HEIGHT = 800;
    private final double WIDTH = 1200;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        initializeApp(stage);
        URL imageURL = getClass().getResource("/assets/mazebot.png");
        stage.getIcons().add(new Image(imageURL.toExternalForm()));
        stage.show();
    }

    private void initializeApp(Stage stage)
    {
        stage.setTitle("m_ai_zbot | A Simple Maze-Solving AI");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        Controller.start();
        stage.setScene(Controller.getActive());
    }
}
