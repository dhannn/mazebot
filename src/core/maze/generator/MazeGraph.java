package core.maze.generator;

import lombok.Getter;

public class MazeGraph 
{
    CellNode[][] maze;
    @Getter int size;
    CellNode initialCell;
    CellNode goalCell;
    
    public final static int LEFT  =   0;
    public final static int UP    =   1;
    public final static int RIGHT =   2;
    public final static int DOWN  =   3;

    @Getter char[][] strRep;
    
    public MazeGraph(int size)
    {
        maze = new CellNode[size][size];
        strRep = new char[size * 2 - 1][size * 2 - 1];

        this.size = size;

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                maze[i][j] = new CellNode(i, j);
                strRep[i * 2][j * 2] = '.';

                if (i < size - 1 && j < size - 1)
                    strRep[i * 2 + 1][j * 2 + 1] = '#';
                if (j < size - 1) 
                    strRep[i * 2][j * 2 + 1] = '#';
                if (i < size - 1) 
                    strRep[i * 2 + 1][j * 2] = '#';
            }
        }
    }

    public CellNode get(int row, int col)
    {
        return maze[row][col];
    }

    @Override
    public String toString() 
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < size; i++)
        {
            str.append("|");
            for (int j = 0; j < size; j++)
            {
                CellNode current = maze[i][j];

                if (j == size - 1)
                {
                    str.append(" |");
                    break;
                }

                if (i == size - 1)
                {
                    str.append("__");
                    break;
                }


                CellNode right = maze[i][j + 1];
                CellNode below = maze[i + 1][j];


                if (isConnected(current, right) && isConnected(current, below))
                    str.append("  ");
                else if (isConnected(current, right) && !isConnected(current, below))
                    str.append("__");
                else if (!isConnected(current, right) && isConnected(current, below))
                    str.append(" |");     
                else if (!isConnected(current, right) && !isConnected(current, below))
                    str.append("_|");
            }

            str.append("\n");
        }

        return str.toString();
    }

    private boolean isConnected(CellNode a, CellNode b)
    {
        return a.edges.contains(b) || b.edges.contains(a);
    }
    
    public void print() 
    {
        for (int i = 0; i < size - 1; i++)
        {
            for (int j = 0; j < size - 1; j++)
            {
                CellNode current = maze[i][j];

                    CellNode right = maze[i][j + 1];
                    
                    if ((j + 1) * 2 - 1 > 0 && current.isConnected(right))
                        strRep[i * 2][(j + 1) * 2 - 1] = '.';

                
                    CellNode below = maze[i + 1][j];
                    
                    if ((i + 1) * 2 - 1 > 0 && current.isConnected(below))
                        strRep[(i + 1) * 2 - 1][j * 2] = '.';
            }

            CellNode border = maze[i][size - 1];
            CellNode below = maze[i + 1][size - 1];

            if (isConnected(border, below))
                strRep[(i + 1) * 2 - 1][(size - 1) * 2] = '.';
        }

        for (int i = 0; i < size - 1; i++)
        {
            CellNode border = maze[size - 1][i];
            CellNode right = maze[size - 1][i + 1];

            if (isConnected(right, border))
                strRep[(size - 1) * 2][(i + 1) * 2 - 1] = '.';
        }
    }
}
