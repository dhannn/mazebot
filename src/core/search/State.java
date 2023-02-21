package core.search;

import core.maze.Cell;
import core.maze.Cell.Type;

@SuppressWarnings("unused")
public class State 
{
    private Cell botLocation;
    private State predecessor;

    /**
     * Slay
     * @param cell
     * @param predecessor
     */
    public State(Cell cell, State predecessor)
    {
        this.botLocation = cell;
        this.predecessor = predecessor;
    }

    /**
     * Checks if the given state is valid
     * @param state
     * @return {@code true} if valid, {@code false} otherwise
     */
    public static boolean isValid(State state)
    {
        if(state.getBotLocation().getType() == Type.SPACE) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the given state is a goal state
     * @param state
     * @param goalCell
     * @return {@code true} if is goal, {@code false} otherwise
     */
    public static boolean isGoal(State state, Cell goalCell)
    {
        if(state.getBotLocation() == goalCell) {
            return true;
        }

        return false;
    }

    public Cell getBotLocation() {
        return this.botLocation;
    }

    public State getPredecessor() {
        return this.predecessor;
    }

    public void setBotLocation(Cell botLocation) {
        this.botLocation = botLocation;
    }

    public void setPredecessor(State predecessor) {
        this.predecessor = predecessor;
    }
}
