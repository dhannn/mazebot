package analysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.maze.Maze;
import core.maze.generator.GraphToCell;
import core.maze.generator.MazeGenerator;
import core.maze.generator.MazeGraph;

public class TestCaseGenerator 
{
    private int numTestCases;
    private int n;
    private boolean isSparse;

    private ArrayList<MazeGraph> mazes = new ArrayList<MazeGraph>();

    private String format = "maze%02d_%03d.txt";

    public TestCaseGenerator(int numTestCases, int n, boolean isSparse)
    {
        this.n = n;
        this.numTestCases = numTestCases;
        this.isSparse = isSparse;

        generateTestCases();
    }

    public void printToFile(String directory) throws IOException
    {
        for (int i = 1; i <= mazes.size(); i++)
        {
            String filename = String.format(format, n, i);

            FileWriter file = new FileWriter(directory + "//" + filename);
            Maze maze = GraphToCell.mazegraphToCell(mazes.get(i - 1));
            
            if (isSparse)
                maze.setSparse();

            file.append(n + "\n");
            file.append(maze.toString());
            file.close();
        }
    }

    private void generateTestCases()
    {
        for (int i = 0; i < numTestCases; i++)
        {
            MazeGraph mazegraph = MazeGenerator.generate(n);
            mazes.add(mazegraph);
        }
    }
}
