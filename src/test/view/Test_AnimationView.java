package test.view;

import core.maze.Cell.Type;

import java.util.ArrayList;

import core.maze.Maze;
import core.maze.generator.GraphToCell;
import core.maze.generator.MazeGenerator;
import core.maze.generator.MazeGraph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.AnimationView;
import view.MazeComponent;

public class Test_AnimationView  extends Application
{
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        final int SIZE = 16;
        MazeGraph mazegraph = MazeGenerator.generate(SIZE, 1);
        Maze maze = GraphToCell.mazegraphToCell(mazegraph);

        int initRow = maze.getInitialCell().getRow();
        int initCol = maze.getInitialCell().getCol();
        int goalRow = maze.getGoalCell().getRow();
        int goalCol = maze.getGoalCell().getCol();
        
        Type[][] types = maze.getCellTypes();
        MazeComponent mazeview = new MazeComponent(types, SIZE);
        mazeview.setInitial(initRow, initCol);
        mazeview.setGoal(goalRow, goalCol);

        ArrayList<String> algos = new ArrayList<String>();
        algos.add("Depth-First Search");
        algos.add("Breadth-First Search");
        algos.add("A* Search");

        AnimationView anim = new AnimationView(mazeview, algos);

        Scene scene = new Scene(anim);

        stage.setTitle("MazeBot");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
    }
}