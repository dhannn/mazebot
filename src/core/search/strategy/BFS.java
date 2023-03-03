package core.search.strategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import core.search.SearchStrategy;
import core.search.State;
import lombok.Getter;

public class BFS extends SearchStrategy 
{
    @Getter String commonName = "Breadth-First Search";

    public BFS()
    {
        frontier = new LinkedList<State>();
        solutionPath = new Stack<State>();
        extractedGoal = null;
        isDone = false;
    }

    public BFS(State initial, State goal) 
    {
        super(initial, goal);
        frontier = new LinkedList<State>();
        solutionPath = new Stack<State>();
        extractedGoal = null;
    }

    @Override
    public void search() 
    {
        frontier.add(initial);

        while(!frontier.isEmpty()) 
        {
            lastExpanded = ((LinkedList<State>) frontier).poll();
            expandedStates.add(lastExpanded);
            
            ArrayList<State> unexplored = getUnexploredStates(lastExpanded);
            nodesVisited = unexplored;
            
            notifyObservers();
            
            if (unexplored.size() > 0)
                frontier.addAll(unexplored);
            
            if (State.isGoal(lastExpanded, goal.getBotLocation()))
            {
                extractedGoal = lastExpanded;
                isFound = true;
                break;
            }
        }

        isDone = true;
        nodesVisited.removeAll(nodesVisited);
    }

    @Override
    public SearchStrategy instance() 
    {
        return new BFS();
    }
}
