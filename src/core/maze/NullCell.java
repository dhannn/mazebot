package core.maze;

public class NullCell extends Cell
{
    public Type getType()
    {
        return null;
    }

    public void setType(Type type)
    { }

    public int getDistance(Cell other)
    {
        return 0;
    }
}
