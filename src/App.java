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
        stage.getIcons().add(new Image("assets/mazebot.png"));
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
