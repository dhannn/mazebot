package core.maze;

import lombok.Getter;
import core.maze.Cell.Type;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze 
{
    Cell[][] maze;
    @Getter @Setter int size;
    @Getter @Setter Cell initialCell;
    @Getter @Setter Cell goalCell;
    
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
        size = Integer.parseInt(line);
        maze = new Cell[size][size];
        int row = 0;
        while ((line = reader.readLine()) != null) {
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
        };

        reader.close();
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

    public Type[][] getCellTypes()
    {
        Type[][] types = new Type[size][size];
        
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                types[i][j] = maze[i][j].getType();
            }
        }

        return types;
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
                    str.append("S");
                    continue;
                }
                
                if (current == goalCell)
                {
                    str.append("G");
                    continue;
                }

                Cell.Type type = current.getType();
                str.append(type.strRepresentation + "");
            }

            str.append("\n");
        }

        return str.toString();
    }
}
