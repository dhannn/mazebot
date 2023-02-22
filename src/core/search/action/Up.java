package core.search.action;

import core.search.Action;
import core.search.State;

public class Up extends Action
{
    @Override
    public void act(State state) 
    {
        State temp = new State(state.getBotLocation(), state.getPredecessor());

        temp.getBotLocation().setRow(temp.getBotLocation().getRow() - 1);

        if (State.isValid(temp)) {
            state.setPredecessor(state);
            state.getBotLocation().setRow(state.getBotLocation().getRow() - 1);
        }
    }
}
