package core.maze.generator;

import core.maze.Cell;
import core.maze.Maze;
import core.maze.Cell.Type;

public class GraphToCell 
{
    private static MazeGraph mazegraph;

    public static Maze mazegraphToCell(MazeGraph mazeGraph)
    {
        int size = mazeGraph.size;
        GraphToCell.mazegraph = mazeGraph;

        Maze maze = initializeCells();
        
        for (int row = 0; row < size - 1; row++)
        {
            for (int col = 0; col < size - 1; col++)
            {
                CellNode current = mazegraph.get(row, col);
                CellNode right = mazegraph.get(row, col + 1);

                if (current.isConnected(right))
                {
                    Cell rightCell = maze.getCell(row * 2, (col + 1) * 2 - 1);
                    rightCell.setType(Type.SPACE);
                }

                CellNode below = mazegraph.get(row + 1, col);

                if (current.isConnected(below))
                {
                    Cell belowCell = maze.getCell((row + 1) * 2 - 1, col * 2);
                    belowCell.setType(Type.SPACE);
                }
            }

            CellNode border = mazegraph.get(row, size - 1);
            CellNode below = mazegraph.get(row + 1, size - 1);

            if (border.isConnected(below))
            {
                Cell belowCell = maze.getCell((row + 1) * 2 - 1, (size - 1) * 2);
                belowCell.setType(Type.SPACE);
            }
        }

        for (int i = 0; i < size - 1; i++)
        {
            CellNode border = mazegraph.get(size - 1, i);
            CellNode right = mazegraph.get(size - 1, i + 1);

            if (right.isConnected(border))
            {
                Cell belowCell = maze.getCell((size - 1) * 2, (i + 1) * 2 - 1);
                belowCell.setType(Type.SPACE);
            }
        }

        return maze;
    }

    private static Maze initializeCells()
    {  
        int oldSize = mazegraph.size;
        int newSize = mazegraph.size * 2;
        
        Maze maze = new Maze(newSize);
        maze.setSize(newSize);

        for (int row = 0; row < oldSize; row++)
        {
            for (int col = 0; col < oldSize; col++)
            {
                Cell cell = new Cell(row * 2, col * 2, Type.SPACE);
                maze.setCell(row * 2, col * 2, cell);

                Cell nextCell = new Cell(row * 2 + 1, col * 2 + 1, Type.WALL);
                nextCell.setType(Type.WALL);
                maze.setCell(row * 2 + 1, col * 2 + 1, nextCell);

                nextCell = new Cell(row * 2, col * 2 + 1, Type.WALL);
                nextCell.setType(Type.WALL);
                maze.setCell(row * 2, col * 2 + 1, nextCell);

                nextCell = new Cell(row * 2 + 1, col * 2, Type.WALL);
                nextCell.setType(Type.WALL);
                maze.setCell(row * 2 + 1, col * 2, nextCell);
            }
        }
            
        CellNode initialNode = mazegraph.initialCell;
        initializeStartNode(maze, initialNode);
        CellNode goalNode = mazegraph.goalCell;
        initializeGoalNode(maze, goalNode);

        return maze;
    }

    private static void initializeStartNode(Maze maze, CellNode initialNode)
    {
        Cell cell = maze.getCell(initialNode.row * 2, initialNode.col * 2);
        maze.setInitialCell(cell);
    }

    private static void initializeGoalNode(Maze maze, CellNode goalNode)
    {
        Cell cell = maze.getCell(goalNode.row * 2, goalNode.col * 2);
        maze.setGoalCell(cell);
    }
}
