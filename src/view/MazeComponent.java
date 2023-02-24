package view;

import java.util.HashMap;

import core.maze.Cell.Type;
import core.search.SearchStrategy;
import core.search.State;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.Color;
import utils.Observable;
import utils.Observer;

public class MazeComponent extends GridPane implements Observer
{
    private static final int GRID_SIZE = 500;
    private final double CELL_SIZE;
    Rectangle[][] mazecells;
    SearchStrategy search;

    Timeline timeline = new Timeline();
    HashMap<KeyValue, Integer> keyToFrame = new HashMap<KeyValue, Integer>();
    private int frames = 0;

    public MazeComponent(Type[][] maze, int size)
    {
        CELL_SIZE = ((double) GRID_SIZE) / size;
        setMinSize(GRID_SIZE, GRID_SIZE);
        setGridLinesVisible(true);
        mazecells = new Rectangle[size][size];

        timeline.setRate((size / 64f) * 80);

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++) 
            {
                Type cell = maze[i][j];

                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                mazecells[i][j] = rect;

                if (cell == Type.SPACE){
                    rect.setFill(Color.WHITE);
                } else {
                    rect.setFill(Color.BLACK);
                }
                
                add(rect, j, i);
            }
        }
    }

    public void setInitial(int row, int col)
    {
        mazecells[row][col].setFill(Color.RED);
    }
    
    public void setGoal(int row, int col)
    {
        mazecells[row][col].setFill(Color.GREEN);
    }

    public void playAnim()
    {
        timeline.play();
    }

    public void pauseAnim()
    {
        timeline.pause();
    }

    public void stepAnim() 
    {
        Duration current = timeline.getCurrentTime();
        Duration next = current.add(Duration.seconds(1));

        timeline.jumpTo(next);
    }

    @Override
    public void link(Observable observable) 
    {
        this.search = (SearchStrategy) observable;
    }

    @Override
    public void update() 
    {
        animateExpanded();
        animateVisited();
        animateSolution();
        
        frames++;
    }

    private void animateExpanded() {
        int lastExpandedRow = search.getLastExpanded().getBotLocation().getRow();
        int lastExpandedCol = search.getLastExpanded().getBotLocation().getCol();
        
        Rectangle cell = mazecells[lastExpandedRow][lastExpandedCol];
        if (!cell.getFill().equals(Color.GREEN))
            addKeyValue(Color.ORANGE, cell);
    }

    private void animateVisited() {
        for (State state: search.getNodesVisited())
        {
            int row = state.getBotLocation().getRow();
            int col = state.getBotLocation().getCol();

            Rectangle visitedCell = mazecells[row][col];
            
            if (!visitedCell.getFill().equals(Color.GREEN))
                addKeyValue(Color.LIGHT_BLUE, visitedCell);
        }
    }

    private void animateSolution() {
        if (!search.getSolutionPath().isEmpty()) {
            int row = search.getSolutionPath().peek().getBotLocation().getRow();
            int col = search.getSolutionPath().peek().getBotLocation().getCol();
            
            Rectangle solutionCell = mazecells[row][col]; 
            
            if (!solutionCell.getFill().equals(Color.GREEN))
                addKeyValue(Color.LIGHT_GREEN, solutionCell);
        }
    }

    private void addKeyValue(Paint color, Rectangle cell)
    {
        KeyValue keyValue = new KeyValue(cell.fillProperty(), color, Interpolator.DISCRETE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frames), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
}
