package core.search;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public abstract class SearchStrategy 
{
    protected MazeBot mazeBot;
    protected List<State> solutionPath;
    protected int numExplored;
    protected HashSet<State> exploredStates;
    protected Hashtable<State, Integer> orderOfStates;
    protected boolean isFound;

    /**
     * Initializes the object variables
     * @param mazeBot
     */
    public SearchStrategy(MazeBot mazeBot)
    {
        this.mazeBot = mazeBot;
        exploredStates = new HashSet<State>();
        numExplored = 0;
        orderOfStates = new Hashtable<State, Integer>();
        isFound = false;
    }

    public abstract void search();
    public abstract void reconstructPath();
}
