import javafx.application.Application;
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
        stage.show();
    }

    private void initializeApp(Stage stage)
    {
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        Controller.start();
        stage.setScene(Controller.getActive());
    }
}
