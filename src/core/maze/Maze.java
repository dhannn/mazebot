package core.maze;

import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public Maze(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        size = line.length();
        maze = new Cell[size][size];
        int row = 0;
        do {
            for (int col = 0; col < size; col++) {
                char c = line.charAt(col);
                Cell.Type type = c == '#' ? Cell.Type.WALL : Cell.Type.SPACE;
                maze[row][col] = new Cell(row, col, type);
                if (c == 'S') {
                    initialCell = maze[row][col];
                } else if (c == 'G') {
                    goalCell = maze[row][col];
                }
            }
            row++;
        } while ((line = reader.readLine()) != null);

        reader.close();
    }

    public Cell getCell(int row, int col)
    {
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
