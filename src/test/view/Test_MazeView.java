package test.view;

import core.maze.Maze;
import core.maze.Cell.Type;
import core.maze.generator.GraphToCell;
import core.maze.generator.MazeGenerator;
import core.maze.generator.MazeGraph;
import view.MazeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Test_MazeView extends Application
{
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        final int SIZE = 32;
        MazeGraph mazegraph = MazeGenerator.generate(SIZE, 1);
        Maze maze = GraphToCell.mazegraphToCell(mazegraph);

        int initRow = maze.getInitialCell().getRow();
        int initCol = maze.getInitialCell().getCol();
        int goalRow = maze.getGoalCell().getRow();
        int goalCol = maze.getGoalCell().getCol();
        
        Type[][] types = maze.getCellTypes();
        MazeView mazeview = new MazeView(types, SIZE);
        mazeview.setInitial(initRow, initCol);
        mazeview.setGoal(goalRow, goalCol);

        Scene scene = new Scene(mazeview);
        mazeview.setLayoutX(100);
        mazeview.setLayoutY(100);

        Label lblAnimSpeed = new Label("Animation Speed");
        lblAnimSpeed.setLayoutX(100);
        lblAnimSpeed.setLayoutY(600);

        stage.setTitle("MazeBot");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
        // MazeGraph mazegraph2 = MazeGenerator.generate(SIZE * 2, 1);
        // Maze maze2 = GraphToCell.mazegraphToCell(mazegraph2);

        // initRow = maze2.getInitialCell().getRow();
        // initCol = maze2.getInitialCell().getCol();
        // goalRow = maze2.getGoalCell().getRow();
        // goalCol = maze2.getGoalCell().getCol();
        
        // types = maze2.getCellTypes();
        // MazeView mazeview2 = new MazeView(types, SIZE * 2);
        // mazeview2.setInitial(initRow, initCol);
        // mazeview2.setGoal(goalRow, goalCol);


        // Scene newScene = new Scene(mazeview2);
        // Stage newStage = new Stage();
        
        // newStage.setTitle("MazeBot");
        // newStage.setWidth(1200);
        // newStage.setHeight(800);
        // newStage.setScene(newScene);
        // newStage.show();
    }
}
