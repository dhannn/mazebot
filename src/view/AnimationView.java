package view;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AnimationView extends Pane
{
    private static final int CMB_ALGORITHM_HEIGHT = 50;
    private static final int CMB_ALGORITHM_WIDTH = 400;

    private MazeComponent maze;
    private ComboBox<String> cmbAlgorithms;
    private Label lblAlgorithms;

    private Button btnStart;

    private final double ALGORITHM_X = 700;
    private final double CMB_ALGORITHM_Y = 150;
    private final double LBL_ALGORITHM_Y = 125;

    private final double MAZE_X = 100;
    private final double MAZE_Y = 100;

    private final double START_X = ALGORITHM_X;
    private final double START_Y = 250;
    private final double START_WIDTH = 120;
    private final double START_HEIGHT = 50;

    private final double WIDTH = 1200;
    private final double HEIGHT = 800;

    public AnimationView(MazeComponent maze, ArrayList<String> algorithms)
    {
        this.maze = maze;
        this.getChildren().add(maze);
        styleMaze();

        lblAlgorithms = new Label("Algorithm");
        cmbAlgorithms = new ComboBox<String>();
        algorithms.forEach( alg -> cmbAlgorithms.getItems().add(alg) );
        this.getChildren().add(lblAlgorithms);
        this.getChildren().add(cmbAlgorithms);
        styleAlgorithms();

        btnStart = new Button("Start");
        btnStart.setOnMouseClicked( e -> 
            { maze.search.search(); 
                maze.search.reconstructPath();
                maze.playAnim(); }
        );
        this.getChildren().add(btnStart);
        styleStart();

        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
    }

    private void styleMaze()
    {
        maze.setLayoutX(MAZE_X);
        maze.setLayoutY(MAZE_Y);
    }

    private void styleAlgorithms()
    {
        cmbAlgorithms.setMinWidth(CMB_ALGORITHM_WIDTH);
        cmbAlgorithms.setMinHeight(CMB_ALGORITHM_HEIGHT);

        lblAlgorithms.setFont(Font.font(15));
        cmbAlgorithms.setStyle("-fx-font: 20 Arial;  -fx-background-color: white; -fx-border-width: 1px; -fx-border-width: 2px; -fx-border-style: solid; -fx-border-color: #ebebeb; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        cmbAlgorithms.setLayoutX(ALGORITHM_X);
        lblAlgorithms.setLayoutX(ALGORITHM_X);
        cmbAlgorithms.setLayoutY(CMB_ALGORITHM_Y);
        lblAlgorithms.setLayoutY(LBL_ALGORITHM_Y);
        
        cmbAlgorithms.setOnAction(e -> Controller.getChosenAlgorithm() );
    }

    private void styleStart()
    {
        btnStart.setTextFill(Color.valueOf("ffffff"));
        btnStart.setStyle("-fx-font: 20 Arial; -fx-font-weight: bold; -fx-background-color: #1a1a1a; -fx-border-style: solid; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 20, 0, 0, 0); -fx-background-radius: 10px; -fx-border-radius: 10px;");

        btnStart.setLayoutX(START_X);
        btnStart.setLayoutY(START_Y);
        btnStart.setMinWidth(START_WIDTH);
        btnStart.setMinHeight(START_HEIGHT);
    }

    public String getChosenAlgorithm()
    {
        return cmbAlgorithms.getValue();
    }
}
