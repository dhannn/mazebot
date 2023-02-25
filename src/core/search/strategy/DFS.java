package core.search.strategy;

import java.util.ArrayList;
import java.util.Stack;

import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;
import lombok.Getter;

public class DFS extends SearchStrategy 
{
    Stack<State> states;
    @Getter String commonName = "Depth-First Search";;

    public DFS()
    {
        states = new Stack<State>();
        solutionPath = new Stack<State>();
        goal = null;
    }

    public DFS(State initial, State goal) 
    {
        super(initial, goal);
        states = new Stack<State>();
        solutionPath = new Stack<State>();
        extractedGoal = null;
    }

    @Override
    public void search() 
    {
        states.push(initial);

        while(!states.empty()) 
        {
            lastExpanded = states.pop();
            exploredStates.add(lastExpanded);

            System.out.println("Exploring State " + lastExpanded.getBotLocation());
            
            ArrayList<State> unexplored = getUnexploredStates(lastExpanded);
            
            if (unexplored.size() == 0)
                continue;
            
            notifyObservers();
            states.add(lastExpanded);
            states.add(unexplored.get(0));

            if (State.isGoal(lastExpanded, goal.getBotLocation()))
            {
                extractedGoal = lastExpanded;
                isFound = true;
                break;
            }
        }

        isDone = true;
        nodesVisited.removeAll(nodesVisited);
        nodesVisited.forEach((State state) -> {System.out.println(state.getBotLocation());});
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
            if (!exploredStates.contains(state))
                unexplored.add(state);
        }
        
        nodesVisited = unexplored;
        return unexplored;
    }
}
