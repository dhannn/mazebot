package view;

import core.maze.Cell.Type;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MazeView extends GridPane
{
    private static final int GRID_SIZE = 700;
    private final int CELL_SIZE;
    Rectangle[][] mazecells;

    public MazeView(Type[][] maze, int size)
    {
        CELL_SIZE = GRID_SIZE / size;
        setMinSize(GRID_SIZE, GRID_SIZE);
        mazecells = new Rectangle[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++) 
            {
                Type cell = maze[i][j];

                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                mazecells[i][j] = rect;

                if (cell == Type.SPACE){
                    rect.setFill(Color.WHITE);
                } else {
                    rect.setFill(Color.BLACK);
                }
                
                add(rect, i, j);
                rect.setVisible(true);
            }
        }
    }

    public void setInitial(int row, int col)
    {
        mazecells[row][col].setFill(Color.RED);
    }
    
    public void setGoal(int row, int col)
    {
        mazecells[row][col].setFill(Color.GREEN);
    }

    public void updateMaze()
    {
        
    }
}
