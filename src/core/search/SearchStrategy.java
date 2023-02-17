package core.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public abstract class SearchStrategy 
{
    protected MazeBot mazeBot;
    protected ArrayList<State> solutionPath;
    protected int numExplored;
    protected HashSet<State> exploredStates;
    protected Hashtable<State, Integer> orderOfStates;

    /**
     * Initializes the object variables
     * @param mazeBot
     */
    public SearchStrategy(MazeBot mazeBot)
    {
        this.mazeBot = mazeBot;
        solutionPath = new ArrayList<State>();
        exploredStates = new HashSet<State>();
        numExplored = 0;
        orderOfStates = new Hashtable<State, Integer>();
    }

    public abstract void search();
    public abstract void reconstructPath();
}
