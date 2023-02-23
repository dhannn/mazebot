package core.search;

import core.maze.Cell;
import core.maze.NullCell;
import core.maze.Cell.Type;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
public class State 
{
    @Getter @Setter private Cell botLocation;
    @Getter @Setter private State predecessor;

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

    public State(Cell cell)
    {
        botLocation = cell;
    }

    /**
     * Checks if the given state is valid
     * @param state
     * @return {@code true} if valid, {@code false} otherwise
     */
    public static boolean isValid(State state)
    {
        if(state.getBotLocation().getType() == Type.SPACE && !(state.getBotLocation() instanceof NullCell)) {
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
        if(state.getBotLocation().equals(goalCell)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) 
    {
        State other = (State) obj;
        return botLocation.equals(other.botLocation);
    }

    @Override
    public int hashCode() 
    {
        int index = botLocation.getCol() * 64 + botLocation.getRow(); 
        return Integer.valueOf(index).hashCode();
    }
}
