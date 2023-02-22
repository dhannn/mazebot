package core.search.action;

import core.search.Action;
import core.search.State;

public class Right extends Action
{
    @Override
    public State act(State state)
    {
        State temp = new State(state.getBotLocation(), state.getPredecessor());

        temp.getBotLocation().setCol(temp.getBotLocation().getCol() + 1);

        if (state.isValid(temp)) {
            state.setPredecessor(state);
            state.getBotLocation().setCol(state.getBotLocation().getCol() + 1);
        }

        return temp;
    }
    
}
