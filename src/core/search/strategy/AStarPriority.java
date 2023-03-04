package core.search.strategy;

import java.util.Comparator;

import core.maze.Cell;
import core.search.State;
import lombok.Setter;

public class AStarPriority implements Comparator<State>
{
    @Setter private State goal;

    @Override
    public int compare(State o1, State o2) 
    {
        Cell c1 = o1.getBotLocation();
        Cell c2 = o2.getBotLocation();

        Cell goalCell = goal.getBotLocation();

        Integer estim1 = goalCell.getDistance(c1);
        Integer estim2 = goalCell.getDistance(c2);

        Integer score1 = o1.getCost() + estim1;
        Integer score2 = o2.getCost() + estim2;

        return score1.compareTo(score2);
    }
    
}
