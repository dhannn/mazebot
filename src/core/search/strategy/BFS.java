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
    Queue<State> frontier;
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
            lastExpanded = frontier.poll();
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
        notifyObservers();
    }

    private ArrayList<State> getUnexploredStates(State current)
    {
        ArrayList<State> all = MazeBot.getNextStates(current);
        ArrayList<State> unexplored = new ArrayList<State>();

        for (State state: all)
        {
            if (!frontier.contains(state) && !expandedStates.contains(state))
                unexplored.add(state);
        }
        
        return unexplored;
    }

    @Override
    public SearchStrategy instance() 
    {
        return new BFS();
    }
}
