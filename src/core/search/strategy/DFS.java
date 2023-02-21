package core.search.strategy;

import java.util.ArrayList;
import java.util.Stack;

import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;

public class DFS extends SearchStrategy 
{
    Stack<State> states;
    State goal;

    public DFS(MazeBot mazeBot) 
    {
        super(mazeBot);
        states = new Stack<State>();
        solutionPath = new Stack<State>();
        goal = null;
    }

    @Override
    public void search() 
    {
        State initial = mazeBot.getInitial();
        states.push(initial);

        while(!states.empty()) 
        {
            State current = states.pop();
            exploredStates.add(current);
            
            ArrayList<State> unexplored = getUnexploredStates(current);

            if (unexplored.size() == 0)
                continue;
            
            states.addAll(unexplored);
            
            if (State.isGoal(current, mazeBot.getGoal().getBotLocation()))
            {
                goal = current;
                isFound = true;
                break;
            }
        }
    }

    @Override
    public void reconstructPath() 
    {
        if (goal == null) return;

        State initial = mazeBot.getInitial();
        State current = goal;

        while (!current.equals(initial))
        {
            solutionPath.add(current);
            current = current.getPredecessor();
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

        return unexplored;
    }
}