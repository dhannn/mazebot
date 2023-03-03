package core.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;
import utils.Observable;
import utils.Observer;

public abstract class SearchStrategy implements Observable
{
    private ArrayList<Observer> observers;
    
    protected Collection<State> frontier;
    @Getter protected State lastExpanded;
    @Getter protected ArrayList<State> nodesVisited;

    @Getter protected Stack<State> solutionPath;
    protected HashSet<State> expandedStates;
    @Getter protected boolean isFound;
    @Getter protected boolean isDone;
    protected State initial;
    protected State goal;
    protected State extractedGoal = null;
    @Setter private MazeBot mazebot;

    public SearchStrategy() 
    {
        expandedStates = new HashSet<State>();
        isFound = false;
        isDone = false;
        solutionPath = new Stack<State>();

        observers = new ArrayList<Observer>();
    }

    /**
     * 
     * @param initial
     * @param goal
     */
    public SearchStrategy(State initial, State goal)
    {
        this.initial = initial;
        this.goal = goal;

        expandedStates = new HashSet<State>();
        solutionPath = new Stack<State>();
        isFound = false;

        observers = new ArrayList<Observer>();
    }
    
    public void reconstructPath() 
    {
        if (extractedGoal == null) 
        {
            notifyObservers();
            return;
        }

        State current = extractedGoal;

        while (!current.equals(initial))
        {
            solutionPath.add(current);

            if (current.getPredecessor() != null)
                current = current.getPredecessor();

            notifyObservers();
        }
        
        solutionPath.add(current);
        notifyObservers();
    }

    public void setInitial(State initial)
    {
        this.initial = initial;
    }

    protected ArrayList<State> getUnexploredStates(State current)
    {
        ArrayList<State> all = mazebot.getNextStates(current);
        ArrayList<State> unexplored = new ArrayList<State>();

        for (State state: all)
        {
            if (!frontier.contains(state) && !expandedStates.contains(state))
                unexplored.add(state);
        }
        
        return unexplored;
    }
    
    public void setGoal(State goal)
    {
        this.goal = goal;
    }

    public State peekSolution()
    {
        return solutionPath.peek();
    }

    public void attach(Observer observer)
    {
        observers.add(observer);
        observer.link(this);
    }

    public void notifyObservers()
    {
        observers.forEach(observer -> observer.update());
    }

    public abstract String getCommonName();
    public abstract SearchStrategy instance(); 
    public abstract void search();
}
