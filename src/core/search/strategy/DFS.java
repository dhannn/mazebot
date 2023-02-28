package core.search.strategy;

import java.util.ArrayList;
import java.util.Stack;

import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;
import lombok.Getter;

public class DFS extends SearchStrategy 
{
    @Getter String commonName = "Depth-First Search";

    public DFS()
    {
        frontier = new Stack<State>();
        goal = null;
    }

    public DFS(State initial, State goal) 
    {
        super(initial, goal);
        frontier = new Stack<State>();
        extractedGoal = null;
    }

    @Override
    public void search() 
    {
        ((Stack<State>) frontier).push(initial);

        while(!frontier.isEmpty()) 
        {
            lastExpanded = ((Stack<State>) frontier).pop();
            expandedStates.add(lastExpanded);

            ArrayList<State> unexplored = getUnexploredStates(lastExpanded);
            nodesVisited = unexplored;
            
            notifyObservers();
            
            if (unexplored.size() > 0)
            {
                frontier.add(lastExpanded);
                frontier.add(unexplored.get(0));
            }
            
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
        return new DFS();
    }
}
