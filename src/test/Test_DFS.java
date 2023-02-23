
package test;

import java.io.IOException;

import core.maze.Maze;
import core.search.MazeBot;
import core.search.strategy.DFS;

public class Test_DFS
{
    public static void main(String[] args) throws IOException 
    {
        Maze maze = new Maze("dat\\testcases\\maze16_4.txt");

        MazeBot mazeBot = new MazeBot(maze);
        DFS foo = new DFS(mazeBot.getInitial(), mazeBot.getGoal());
        foo.search();
        foo.reconstructPath();
    }    
}
