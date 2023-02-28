package core.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import lombok.Getter;
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
    protected State extractedGoal;

    public SearchStrategy() 
    {
        expandedStates = new HashSet<State>();
        isFound = false;
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

    public void setInitial(State initial)
    {
        this.initial = initial;
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
        observers.forEach(observer -> observer.update() );
    }

    public abstract String getCommonName();
    public abstract SearchStrategy instance(); 
    public abstract void search();
    public abstract void reconstructPath();
}
