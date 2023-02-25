package core.search.strategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;
import lombok.Getter;

public class BFS extends SearchStrategy 
{
    Queue<State> states;
    @Getter String commonName = "Breadth-First Search";

    public BFS()
    {
        states = new LinkedList<State>();
        solutionPath = new Stack<State>();
        extractedGoal = null;
        isDone = false;
    }

    public BFS(State initial, State goal) 
    {
        super(initial, goal);
        states = new LinkedList<State>();
        solutionPath = new Stack<State>();
        extractedGoal = null;
    }

    @Override
    public void search() 
    {
        states.add(initial);

        while(!states.isEmpty()) 
        {
            lastExpanded = states.poll();
            exploredStates.add(lastExpanded);
            
            ArrayList<State> unexplored = getUnexploredStates(lastExpanded);
            
            if (State.isGoal(lastExpanded, goal.getBotLocation()))
            {
                extractedGoal = lastExpanded;
                isFound = true;
                break;
            }
            
            notifyObservers();
            states.addAll(unexplored);
        }

        isDone = true;
        nodesVisited.removeAll(nodesVisited);
    }

    @Override
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
    }

    private ArrayList<State> getUnexploredStates(State current)
    {
        ArrayList<State> all = MazeBot.getNextStates(current);
        ArrayList<State> unexplored = new ArrayList<State>();

        for (State state: all)
        {
            if (!states.contains(state) && !exploredStates.contains(state))
                unexplored.add(state);
        }
        
        nodesVisited = unexplored;
        return unexplored;
    }
}
