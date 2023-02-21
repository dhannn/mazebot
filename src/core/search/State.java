package core.search;

import core.maze.Cell;
import lombok.Getter;

@SuppressWarnings("unused")
public class State 
{
    @Getter private Cell botLocation;
    @Getter private State predecessor;
    private Action action;

    /**
     * Slay
     * @param cell
     * @param predecessor
     */
    public State(Cell cell, Action action, State predecessor)
    {

    }

    /**
     * Checks if the given state is valid
     * @param state
     * @return {@code true} if valid, {@code false} otherwise
     */
    public static boolean isValid(State state)
    {
        return false;
    }

    /**
     * Checks if the given state is a goal state
     * @param state
     * @param goalCell
     * @return {@code true} if valid, {@code false} otherwise
     */
    public static boolean isGoal(State state, Cell goalCell)
    {
        return false;
    }
}
