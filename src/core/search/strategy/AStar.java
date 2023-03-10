package core.search.strategy;

import java.util.ArrayList;
import java.util.PriorityQueue;

import core.search.SearchStrategy;
import core.search.State;
import lombok.Getter;

public class AStar extends SearchStrategy 
{
    @Getter private String commonName = "A* Search";

    @Override
    public void search() 
    {
        AStarPriority heuristic = new AStarPriority();
        heuristic.setGoal(goal);

        frontier = new PriorityQueue<State>(heuristic);
        frontier.add(initial);

        while (!frontier.isEmpty())
        {
            lastExpanded = ((PriorityQueue<State>) frontier).poll();
            expandedStates.add(lastExpanded);

            ArrayList<State> unexplored = getUnexploredStates(lastExpanded);
            frontier.addAll(unexplored);

            nodesVisited = unexplored;
            notifyObservers();

            if (State.isGoal(lastExpanded, goal.getBotLocation()))
            {
                extractedGoal = lastExpanded;
                isFound = true;
                break;
            }
        }
        
        isDone = true;
    }
    
    @Override
    public SearchStrategy instance() 
    {
        return new AStar();   
    }
}
