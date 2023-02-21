package core.search.strategy;

import java.util.ArrayList;
import java.util.Stack;

import core.search.Action;
import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;

public class DFSID extends SearchStrategy
{
    private int maxDepth = 0;
    private int currentDepth = 0;

    private Stack<State> states;

    public DFSID(MazeBot mazeBot) 
    {
        super(mazeBot);
        states = new Stack<State>();
    }

    @Override
    public void search()
    {
        /**
         *  ALGORITHM FOR DFS-ID
         *  Initialize depth = 0.
         * 
         *  Explore states up until the current depth.
         * 
         *  Is state a solution?
         *      If yes, return.
         *      If no, continue.
         * 
         *  Increase depth.
         * 
         *  Repeat Step 2.
         */
    }

    private void explore()
    {
        /**
         *  EXPLORE STATE ALGORITHM (Basic DFS)
         *  Initialize empty stack.
         * 
         *  Push the root to the stack.
         * 
         *  Pop the stack to the current state.
         * 
         *  Set the current state to explored.
         * 
         *  Check if the current state is the goal state.
         *      If yes, return.
         * 
         *  Check if the current state has unexplored children.
         *      If yes, push state back to the stack.
         *  
         *  Push an unexplored children of the current state.
         * 
         *  Check if the stack is empty.
         *      If yes, no solution has been found in the
         *      current depth.
         *      If no, then go to Step 3.
         */
    }

    @Override
    public void reconstructPath() 
    {
    }
    
}
