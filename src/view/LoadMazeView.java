package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoadMazeView extends VBox
{
    private Label lblFilename;
    private TextField txtFilename;
    private Button btnLoad;

    private final double LBL_FILENAME_Y = 250;
    private final double TXT_FILENAME_Y = 300;
    private final double TXT_FILENAME_WIDTH = 500;
    private final double TXT_FILENAME_HEIGHT = 50;
    
    private final double LOAD_WIDTH = 120;
    private final double LOAD_HEIGHT = 50;
    
    private final double LOAD_Y = 500;

    private final double WIDTH = 1200;
    private final double HEIGHT = 800;

    private final double SPACING = 20;

    public LoadMazeView()
    {
        lblFilename = new Label("Maze Filename");
        txtFilename = new TextField();
        styleFilename();
        getChildren().add(lblFilename);
        getChildren().add(txtFilename);

        btnLoad = new Button("Load");
        styleLoad();
        getChildren().add(btnLoad);
        
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setAlignment(Pos.CENTER);
        setSpacing(SPACING);
    }

    public String getMazefile()
    {
        return txtFilename.getText();
    }

    private void styleFilename()
    {
        lblFilename.setLayoutY(LBL_FILENAME_Y);
        lblFilename.setFont(Font.font("Arial", 15));
        txtFilename.setLayoutY(TXT_FILENAME_Y);
        txtFilename.setMinSize(TXT_FILENAME_WIDTH, TXT_FILENAME_HEIGHT);
        txtFilename.setPrefSize(TXT_FILENAME_WIDTH, TXT_FILENAME_HEIGHT);
        txtFilename.setMaxSize(TXT_FILENAME_WIDTH, TXT_FILENAME_HEIGHT);
        txtFilename.setAlignment(Pos.CENTER);
        txtFilename.setStyle("-fx-padding: 10px 20px; -fx-font: 20 Arial;  -fx-background-color: white; -fx-border-width: 1px; -fx-border-width: 2px; -fx-border-style: solid; -fx-border-color: #ebebeb; -fx-border-radius: 10px; -fx-background-radius: 10px;");
    }

    private void styleLoad()
    {
        btnLoad.setLayoutY(LOAD_Y);
        btnLoad.setTextFill(Color.valueOf("ffffff"));
        btnLoad.setStyle("-fx-font: 20 Arial; -fx-font-weight: bold; -fx-background-color: #1a1a1a; -fx-border-style: solid; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 20, 0, 0, 0); -fx-background-radius: 10px; -fx-border-radius: 10px;");
        btnLoad.setMinWidth(LOAD_WIDTH);
        btnLoad.setMinHeight(LOAD_HEIGHT);
        btnLoad.setOnMouseClicked(e -> Controller.loadMaze() );
    }
}
