package core.maze;

import lombok.Setter;

public class Maze 
{
    Cell[][] maze;
    @Setter int size;
    @Setter Cell initialCell;
    @Setter Cell goalCell;
    
    public final static int LEFT  =   0;
    public final static int UP    =   1;
    public final static int DOWN  =   2;
    public final static int RIGHT =   3;

    public Maze(int size) 
    {
        maze = new Cell[size][size];
        this.size = size;
    }

    public Maze(String filename)
    {
        
    }

    public Cell getCell(int row, int col)
    {
        if (row < 0 || col < 0)
            return new NullCell();

        return maze[row][col];
    }

    public void setCell(int row, int col, Cell cell)
    {
        maze[row][col] = cell;
    }

    @Override
    public String toString() 
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                Cell current = maze[i][j];

                if (current == initialCell)
                {
                    str.append("S ");
                    continue;
                }
                
                if (current == goalCell)
                {
                    str.append("G ");
                    continue;
                }


                Cell.Type type = current.getType();
                str.append(type.strRepresentation + " ");
            }

            str.append("\n");
        }

        return str.toString();
    }
}
