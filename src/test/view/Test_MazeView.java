package test.view;

import core.maze.Maze;
import core.maze.Cell.Type;
import core.maze.generator.GraphToCell;
import core.maze.generator.MazeGenerator;
import core.maze.generator.MazeGraph;
import view.MazeView;
import javafx.application.Application;
import javafx.scene.Scene;
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
        MazeGraph mazegraph = MazeGenerator.generate(SIZE, 0);
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
        mazeview.setVisible(true);
        stage.setScene(scene);
        stage.show();
    }
}
