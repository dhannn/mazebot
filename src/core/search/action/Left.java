package core.search.action;

import core.search.Action;
import core.search.State;

public class Left extends Action
{
    @Override
    public State act(State state)
    {
        State temp = new State(state.getBotLocation(), state.getPredecessor());

        temp.getBotLocation().setColumn(temp.getBotLocation().getColumn() - 1);

        if (state.isValid(temp)) {
            state.setPredecessor(state);
            state.getBotLocation().setColumn(state.getBotLocation().getColumn() - 1);
        }

        return temp;
    }
    
}
