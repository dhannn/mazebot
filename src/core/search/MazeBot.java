package core.search;

import java.util.ArrayList;

import core.maze.Maze;
import core.search.action.*;
import lombok.Getter;

public class MazeBot 
{
    public final static int LEFT = 0;
    public final static int UP = 1;
    public final static int RIGHT = 2;
    public final static int DOWN = 3;

    @Getter private State initial;
    @Getter private State goal;
    private static Maze maze;
    private SearchStrategy searchStrategy;
    private final static Action[] ACTION = {
        new Left(), new Up(), new Right(), new Down()};
    @Getter private static int size;

    /**
     * This constructor will initialize the initial and goal state from the maze.
     * It will also initialize the search strategy.
     * @param maze
     * @param searchStrategy
     */
    public MazeBot(Maze maze)
    {
        MazeBot.maze = maze;
        MazeBot.size = maze.getSize();
        this.initial = new State(maze.getInitialCell(), null);
        this.goal = new State(maze.getGoalCell(), null);
    }

    public void setSearchStrategy(SearchStrategy search)
    {
        searchStrategy = search;
        searchStrategy.setInitial(initial);
        searchStrategy.setGoal(goal);
    }

    public static ArrayList<State> getNextStates(State state)
    {
        ArrayList<State> states = new ArrayList<State>();

        for (Action action: ACTION)
        {
            State nextState = MazeBot.next(state, action);

            if (State.isValid(nextState))
                states.add(nextState);
        }

        return states;
    }


    /**
     * This function calls the {@code search()} function of the 
     * {@code SearchStrategy} object.
     */
    public void search()
    {
        searchStrategy.search();
        searchStrategy.reconstructPath();
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
        return action.act(state, size, maze.getCellTypes());
    }

    public int getNumExplored()
    {
        return searchStrategy.expandedStates.size();
    }
    
    public int getNumSolution()
    {
        return searchStrategy.solutionPath.size();
    }
}
