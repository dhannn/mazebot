package core.maze;

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

    private int row;
    private int col;
    private Type type;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    public Cell(int row, int col, Type type)
    {
        this.row = row;
        this.col = col;
        this.type = type;
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
}
