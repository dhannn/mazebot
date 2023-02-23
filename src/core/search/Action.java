package core.search;

import core.maze.Cell.Type;

public abstract class Action 
{
    public abstract State act(State state, int size, Type[][] maze);    
}
