package core.search.strategy;

import java.util.ArrayList;
import java.util.PriorityQueue;

import core.search.SearchStrategy;
import core.search.State;
import lombok.Getter;

public class HDFS extends SearchStrategy
{
    @Getter public final String commonName = "Informed DFS";

    @Override
    public SearchStrategy instance() 
    {
        return new HDFS();
    }

    @Override
    public void search() 
    {
        HDFSPriority heuristic = new HDFSPriority();
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
    
}
