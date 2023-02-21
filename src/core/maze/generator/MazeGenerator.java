package core.maze.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator 
{
    static MazeGraph mazegraph;
    static int size;
    static HashSet<CellNode> visited = new HashSet<CellNode>();
    static Stack<CellNode> stack = new Stack<CellNode>();
    static double density = 1.0;

    static Random random = new Random();

    private final static int NUM_DIR = 4; 

    public static MazeGraph generate(int size, double density)
    {
        visited = new HashSet<CellNode>();
        stack = new Stack<CellNode>();
        MazeGenerator.density = density;

        MazeGenerator.size = (size + 1) / 2;
        mazegraph = new MazeGraph((size + 1) / 2);
        randomDFS();

        return mazegraph;
    }

    public static MazeGraph generate(int size)
    {
        visited = new HashSet<CellNode>();
        stack = new Stack<CellNode>();
        
        MazeGenerator.size = (size + 1) / 2;
        mazegraph = new MazeGraph((size + 1) / 2);
        randomDFS();

        return mazegraph;
    }

    private static void randomDFS()
    {
        mazegraph.initialCell = getRandomCell();
        visited.add(mazegraph.initialCell);
        stack.push(mazegraph.initialCell);

        while (!stack.empty())
        {
            CellNode current = stack.pop();
            ArrayList<CellNode> neighbors = getUnexploredNeighbors(current);

            if (!neighbors.isEmpty())
            {
                stack.push(current);
                
                CellNode neighbor = choose(neighbors);
                current.edges.add(neighbor);
                neighbor.edges.add(current);

                if (shouldConnect())
                {
                    CellNode another = choose(neighbors);
                    current.edges.add(another);
                    another.edges.add(current);
                }

                visited.add(neighbor);
                stack.push(neighbor);
            }
        }

        do 
            mazegraph.goalCell = getRandomCell();
        while (mazegraph.goalCell == mazegraph.initialCell || mazegraph.goalCell.getDistance(mazegraph.initialCell) < size);
    }

    private static CellNode choose(ArrayList<CellNode> cells)
    {
        int index = random.nextInt(cells.size());
        return cells.get(index);
    }

    private static ArrayList<CellNode> getUnexploredNeighbors(CellNode cell)
    {
        ArrayList<CellNode> neighbors = new ArrayList<CellNode>();

        for (int direction = 0; direction < NUM_DIR; direction++)
        {
            if (!isWithinRange(cell, direction))
                continue;
            
            CellNode neighbor = move(cell, direction);

            if (!visited.contains(neighbor))
                neighbors.add(neighbor);
        }

        return neighbors;
    }

    private static boolean isWithinRange(CellNode cell, int direction)
    {
        int row = cell.row;
        int col = cell.col;

        boolean[] conditions = {
            col - 1 >= 0,
            row - 1 >= 0,
            col + 1 < size,
            row + 1 < size
        };

        return conditions[direction];
    }

    private static boolean shouldConnect()
    {
        double test = random.nextDouble();

        return test > density;
    }

    private static CellNode getRandomCell()
    {
        int row = random.nextInt(size);
        int col = random.nextInt(size);

        return mazegraph.maze[row][col];
    }

    private static CellNode move(CellNode cell, int direction)
    {
        int row = cell.row;
        int col = cell.col;

        if (direction == MazeGraph.LEFT)
            col--;
        if (direction == MazeGraph.UP)
            row--;
        if (direction == MazeGraph.RIGHT)
            col++;
        if (direction == MazeGraph.DOWN)
            row++;
        
        return mazegraph.maze[row][col];
    }
}
