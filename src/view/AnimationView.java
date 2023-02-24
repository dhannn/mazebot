package view;

import java.util.ArrayList;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;

public class AnimationView extends Pane
{
    /**
     *
     */
    private static final int BUTTON_SIZE = 20;
    private static final int PLAY_PAUSE_X = 300;
    private static final int BUTTONS_Y = 650;
    private static final int CMB_ALGORITHM_HEIGHT = 50;
    private static final int CMB_ALGORITHM_WIDTH = 400;

    private MazeComponent maze;
    private StatsComponent stats;
    private ComboBox<String> cmbAlgorithms;
    private Label lblAlgorithms;

    private Button btnStep;
    private Button btnPause;
    private Button btnPlay;

    private boolean isPaused = false;

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

    public AnimationView(MazeComponent maze, StatsComponent stats, ArrayList<String> algorithms)
    {
        this.maze = maze;
        this.stats = stats;
        this.getChildren().add(maze);
        this.getChildren().add(stats);

        stats.setMinWidth(CMB_ALGORITHM_WIDTH);
        stats.setMinHeight(200);
        stats.setLayoutX(ALGORITHM_X);
        stats.setLayoutY(400);
        stats.setBackground(Background.fill(Color.BLACK));

        styleMaze();

        lblAlgorithms = new Label("Algorithm");
        cmbAlgorithms = new ComboBox<String>();
        algorithms.forEach( alg -> cmbAlgorithms.getItems().add(alg) );
        this.getChildren().add(lblAlgorithms);
        this.getChildren().add(cmbAlgorithms);
        styleAlgorithms();

        btnStart = new Button("Start");
        btnStart.setOnMouseClicked( e -> { 
            maze.search.search(); 
            maze.search.reconstructPath();
            maze.playAnim();
            stats.playAnim(); 
        });
        this.getChildren().add(btnStart);
        styleStart();

        btnPause = new Button();
        ImageView pauseView = new ImageView(new Image("assets/pause.png"));
        pauseView.setFitHeight(BUTTON_SIZE);
        pauseView.setFitWidth(BUTTON_SIZE);
        pauseView.setPreserveRatio(true);
        
        btnPause.setCursor(Cursor.HAND);
        btnPause.setBackground(Background.EMPTY);
        btnPause.setBorder(Border.EMPTY);
        btnPause.setGraphic(pauseView);
        btnPause.setPrefWidth(BUTTON_SIZE);
        btnPause.setPrefHeight(BUTTON_SIZE);
        btnPause.setLayoutX(PLAY_PAUSE_X);
        btnPause.setLayoutY(BUTTONS_Y);
        btnPause.setOnMouseClicked(e -> togglePause());
        getChildren().add(btnPause);
        
        btnPlay = new Button();
        ImageView playView = new ImageView(new Image("assets/play.png"));
        playView.setFitHeight(BUTTON_SIZE);
        playView.setFitWidth(BUTTON_SIZE);
        playView.setPreserveRatio(true);
        
        btnPlay.setCursor(Cursor.HAND);
        btnPlay.setBackground(Background.EMPTY);
        btnPlay.setBorder(Border.EMPTY);
        btnPlay.setGraphic(playView);
        btnPlay.setPrefWidth(BUTTON_SIZE);
        btnPlay.setPrefHeight(BUTTON_SIZE);
        btnPlay.setLayoutX(PLAY_PAUSE_X);
        btnPlay.setLayoutY(BUTTONS_Y);
        btnPlay.setOnMouseClicked(e -> togglePause());
        
        btnStep = new Button();
        ImageView stepView = new ImageView(new Image("assets/step.png"));
        stepView.setFitHeight(BUTTON_SIZE);
        stepView.setFitWidth(BUTTON_SIZE);
        stepView.setPreserveRatio(true);
        
        btnStep.setCursor(Cursor.HAND);
        btnStep.setBackground(Background.EMPTY);
        btnStep.setBorder(Border.EMPTY);
        btnStep.setGraphic(stepView);
        btnStep.setPrefWidth(BUTTON_SIZE);
        btnStep.setPrefHeight(BUTTON_SIZE);
        btnStep.setLayoutX(PLAY_PAUSE_X + 50);
        btnStep.setLayoutY(BUTTONS_Y);
        btnStep.setOnMouseClicked(e -> step());

        getChildren().add(btnStep);

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
        btnStart.setCursor(Cursor.HAND);
    }

    private void togglePause()
    {
        if (isPaused) {
            getChildren().remove(btnPlay);
            getChildren().add(btnPause);
            maze.playAnim();
            stats.playAnim();
        } else {
            getChildren().remove(btnPause);
            getChildren().add(btnPlay);
            maze.pauseAnim();
            stats.pauseAnim();
        }

        isPaused = !isPaused;
    }

    private void step()
    {
        getChildren().remove(btnPause);

        if (!getChildren().contains(btnPlay))
            getChildren().add(btnPlay);
        
        maze.pauseAnim();
        stats.pauseAnim();
        maze.stepAnim();
        stats.stepAnim();
    }

    public String getChosenAlgorithm()
    {
        return cmbAlgorithms.getValue();
    }
}
