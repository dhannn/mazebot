package core.search.action;

import core.maze.Cell;
import core.maze.Cell.Type;
import core.search.Action;
import core.search.MazeBot;
import core.search.State;

public class Up extends Action
{
    @Override
    public State act(State state, int size, Type[][] maze) 
    {
        State temp = new State(state.getBotLocation(), state);
        Cell newBotLocation = temp.getBotLocation().move(MazeBot.UP, size, maze);
        temp.setBotLocation(newBotLocation);

        return temp;
    }
}
