package view;

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

    private Timeline timeline = new Timeline();
    private int frames = 0;

    public MazeComponent(Type[][] maze, int size)
    {
        CELL_SIZE = ((double) GRID_SIZE) / size;
        setMinSize(GRID_SIZE, GRID_SIZE);
        setGridLinesVisible(true);
        mazecells = new Rectangle[size][size];

        timeline.setRate((size / 64f) * 60);

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++) 
            {
                Type cell = maze[i][j];

                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                mazecells[i][j] = rect;

                if (cell == Type.SPACE) {
                    rect.setFill(Color.WHITE);
                    addKeyValue(Color.WHITE, rect);
                } else {
                    rect.setFill(Color.BLACK);
                    addKeyValue(Color.BLACK, rect);
                }
                
                add(rect, j, i);
            }
        }

        frames++;
    }

    public void setInitial(int row, int col)
    {
        mazecells[row][col].setFill(Color.RED);
        addKeyValue(Duration.ZERO, Color.RED, mazecells[row][col]);
    }
    
    public void setGoal(int row, int col)
    {
        mazecells[row][col].setFill(Color.GREEN);
        addKeyValue(Duration.ZERO, Color.GREEN, mazecells[row][col]);
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
        if (!cell.getFill().equals(Color.GREEN) && !cell.getFill().equals(Color.RED))
            addKeyValue(Color.ORANGE, cell);
    }

    private void animateVisited() {
        for (State state: search.getNodesVisited())
        {
            int row = state.getBotLocation().getRow();
            int col = state.getBotLocation().getCol();

            Rectangle visitedCell = mazecells[row][col];
            
            if (!visitedCell.getFill().equals(Color.GREEN) && !visitedCell.getFill().equals(Color.RED))
                addKeyValue(Color.LIGHT_BLUE, visitedCell);
        }
    }

    private void animateSolution() {
        if (!search.getSolutionPath().isEmpty()) {
            int row = search.getSolutionPath().peek().getBotLocation().getRow();
            int col = search.getSolutionPath().peek().getBotLocation().getCol();
            
            Rectangle solutionCell = mazecells[row][col]; 
            
            if (!solutionCell.getFill().equals(Color.GREEN) && !solutionCell.getFill().equals(Color.RED))
                addKeyValue(Color.LIGHT_GREEN, solutionCell);
        }
    }

    private void addKeyValue(Paint color, Rectangle cell)
    {
        KeyValue keyValue = new KeyValue(cell.fillProperty(), color, Interpolator.DISCRETE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frames), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }

    private void addKeyValue(Duration duration, Paint color, Rectangle cell)
    {
        KeyValue keyValue = new KeyValue(cell.fillProperty(), color, Interpolator.DISCRETE);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
}
