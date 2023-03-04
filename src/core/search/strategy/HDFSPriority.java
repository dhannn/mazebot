package core.search.strategy;

import java.util.Comparator;

import core.maze.Cell;
import core.search.State;
import lombok.Setter;

public class HDFSPriority implements Comparator<State>
{
    @Setter private State goal;

    @Override
    public int compare(State o1, State o2) 
    {
        Cell c1 = o1.getBotLocation();
        Cell c2 = o2.getBotLocation();

        Cell goalCell = goal.getBotLocation();

        Integer heur1 = goalCell.getDistance(c1);
        Integer heur2 = goalCell.getDistance(c2);

        return heur1.compareTo(heur2);
    }
    
}
