
package test;

import core.maze.Maze;
import core.maze.generator.GraphToCell;
import core.maze.generator.MazeGenerator;
import core.maze.generator.MazeGraph;

public class Test_MazeGenerator 
{
    public static void main(String[] args) 
    {
        MazeGraph mazegraph = MazeGenerator.generate(4);
        Maze maze = GraphToCell.mazegraphToCell(mazegraph);
        mazegraph.print();

        System.out.println(maze);
    }    
}
