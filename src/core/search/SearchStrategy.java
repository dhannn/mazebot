package core.search;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public abstract class SearchStrategy 
{
    protected List<State> solutionPath;
    protected int numExplored;
    protected HashSet<State> exploredStates;
    protected Hashtable<State, Integer> orderOfStates;
    protected boolean isFound;

    
    protected State initial;
    protected State goal;

    /**
     * 
     * @param initial
     * @param goal
     */
    public SearchStrategy(State initial, State goal)
    {
        this.initial = initial;
        this.goal = goal;

        exploredStates = new HashSet<State>();
        numExplored = 0;
        orderOfStates = new Hashtable<State, Integer>();
        isFound = false;
    }

    public abstract void search();
    public abstract void reconstructPath();
}
