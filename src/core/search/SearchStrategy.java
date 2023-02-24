package core.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

import lombok.Getter;
import utils.Observable;
import utils.Observer;

public abstract class SearchStrategy implements Observable
{
    private ArrayList<Observer> observers;
    
    @Getter protected State lastExpanded;
    @Getter protected ArrayList<State> nodesVisited;

    @Getter protected Stack<State> solutionPath;
    protected int numExplored;
    protected HashSet<State> exploredStates;
    protected Hashtable<State, Integer> orderOfStates;
    protected boolean isFound;
    
    protected State initial;
    protected State goal;

    public SearchStrategy() 
    {
        exploredStates = new HashSet<State>();
        numExplored = 0;
        orderOfStates = new Hashtable<State, Integer>();
        isFound = false;

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

        exploredStates = new HashSet<State>();
        numExplored = 0;
        orderOfStates = new Hashtable<State, Integer>();
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
    public abstract void search();
    public abstract void reconstructPath();
}
