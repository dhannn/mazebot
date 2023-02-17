package core.search;

import java.util.ArrayList;

import core.maze.Maze;
import core.search.action.*;

@SuppressWarnings("unused")
public class MazeBot 
{
    private State initial;
    private State goal;
    private SearchStrategy searchStrategy;
    private final Action[] ACTION = {
        new Left(), new Up(), new Right(), new Down()};

    /**
     * This constructor will initialize the initial and goal state from the maze.
     * It will also initialize the search strategy.
     * @param maze
     * @param searchStrategy
     */
    public MazeBot(Maze maze, SearchStrategy searchStrategy)
    {

    }

    /**
     * This function calls the {@code search()} function of the 
     * {@code SearchStrategy} object.
     */
    public void search()
    {

    }

    /**
     * This function gets the next state by invoking the {@code act()} method
     * of the give {@code Action} object.
     * @param state
     * @param action
     * @return
     */
    public static State next(State state, Action action)
    {
        return null;
    }
}
