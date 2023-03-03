package test;

import java.io.IOException;

import core.maze.Maze;
import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.strategy.AStar;

public class Test_AStar 
{
    public static void main(String[] args) throws IOException 
    {
        Maze maze = new Maze("dat/testcases/sparse/maze08_010.txt");
        MazeBot mazeBot = new MazeBot(maze);

        SearchStrategy search = new AStar();
        mazeBot.setSearchStrategy(search);

        mazeBot.search();

        System.out.println(mazeBot.getNumSolution());
    }    
}
