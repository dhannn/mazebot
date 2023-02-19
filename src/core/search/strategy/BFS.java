package core.search.strategy;

import core.search.MazeBot;
import core.search.SearchStrategy;
import java.util.Queue;
import java.util.LinkedList;

public class BFS extends SearchStrategy
{
    // D:\New Folder\22-23\Term 2\CSINTSY\MCO1
    private Queue<State> states;

    public BFS {
        super(mazeBot);
        states = new Queue<State>();
    }
    
    @Override
    public void search() {
        
        // From start state look for best path to goal state
        
        exploredStates.add(states.getStartState()??);
        numExplored++;

        State currentState = states.getStartState()??;

        while(currentState != states.getGoalState()??) {

            for(State i : adjacentStates) {
                if(!exploredStates.containsKey(i)) {
                    exploredStates.put(i, currentState);
                    numExplored++;
                }
            }

            currentState = /*get next state*/;
        }
    }

    @Override
    public void reconstructPath() {
        
        solutionPath.add(currentState);
        
        while(currentState != states.getStartState()??) {
            solutionPath.add(currentState);
        }

    }
}