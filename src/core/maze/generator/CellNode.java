package core.maze.generator;

import java.util.ArrayList;

public class CellNode 
{
    int row;
    int col;
    ArrayList<CellNode> edges;

    public CellNode(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.edges = new ArrayList<CellNode>();
    }

    public boolean isConnected(CellNode other)
    {
        return edges.contains(other) || other.edges.contains(this);
    }

    public int getDistance(CellNode other)
    {
        return Math.abs(other.col - col) + Math.abs(other.row - row);
    }

    @Override
    public int hashCode() 
    {
        return (row * 100) + col;
    }

    @Override
    public boolean equals(Object obj) {
        CellNode other = (CellNode)obj;

        return row == other.row && col == other.col;
    }
}
