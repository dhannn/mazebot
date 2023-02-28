package core.maze;

import core.search.MazeBot;
import lombok.Getter;
import lombok.Setter;

public class Cell 
{
    public enum Type
    {
        WALL("#"),
        SPACE(".");

        public final String strRepresentation;

        private Type(String strRep)
        {
            strRepresentation = strRep;
        }
    }

    @Getter @Setter private int row;
    @Getter @Setter private int col;
    private Type type;
    
    public Cell() {}

    public Cell(int row, int col, Type type)
    {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public Cell move(int direction, int size, Type[][] maze)
    {
        int row = this.row;
        int col = this.col;

        switch (direction) {
            case MazeBot.LEFT:
                col--;
                break;
            case MazeBot.UP:
                row--;
                break;
            case MazeBot.RIGHT:
                col++;
                break;
            case MazeBot.DOWN:
                row++;
                break;
        }

        if (row == -1 || col == -1 || row >= size || col >= size)
            return new NullCell();

        return new Cell(row, col, maze[row][col]);
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public int getDistance(Cell other)
    {
        return Math.abs(other.col - col) + Math.abs(other.row - row);
    }

    @Override
    public boolean equals(Object obj) 
    {
        Cell other = (Cell) obj;
        return col == other.col && row == other.row;
    }

    @Override
    public int hashCode() 
    {
        return super.hashCode();
    }
}
