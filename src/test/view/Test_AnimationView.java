package test.view;

import core.maze.Cell.Type;

import java.util.ArrayList;

import core.maze.Maze;
import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;
import core.search.strategy.DFS;
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
        final int SIZE = 64;
        // MazeGraph mazegraph = MazeGenerator.generate(SIZE, 1);
        // Maze maze = GraphToCell.mazegraphToCell(mazegraph);
        Maze maze = new Maze("dat\\testcases\\maze64_10.txt");
        // MazeBot mazeBot = new MazeBot(maze);

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
        
        // mazeBot.search();
        SearchStrategy searchStrategy = new DFS(new State(maze.getInitialCell()), new State(maze.getGoalCell()));
        searchStrategy.attach(mazeview);
        
        Scene scene = new Scene(anim);
        
        stage.setTitle("MazeBot");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
        // searchStrategy.search();
        // searchStrategy.reconstructPath();
        // mazeview.playAnim();
    }
}