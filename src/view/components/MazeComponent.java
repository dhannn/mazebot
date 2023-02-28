package view.components;

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

/**
 * The {@code MazeComponent} class encapsulates the logic for rendering the maze
 * to the user interface. 
 */
public class MazeComponent extends GridPane implements Observer
{
    /**
     * The fastest possible frame rate
     */
    private static final int MAX_FRAME_RATE = 60;
    /**
     * The largest possible size. Needed to calculate the frame rate 
     * as larger grid size means faster animation.
     */
    private static final float MAX_GRID_SIZE = 64f;
    /**
     * The size (actual pixels) of the grid to be rendered.
     */
    private static final int GRID_SIZE = 500;
    /**
     * The size (actual pixels) of each cell based on the {@code MAX_GRID_SIZE}
     * and the size of the grid (in number, not in pixels).
     */
    private final double CELL_SIZE;
    
    /**
     * A matrix whose elements point to each of their cell.
     */
    private Rectangle[][] mazecells;
    /**
     * The search algorithm that notifies the class whenever there are changes 
     * in the model.
     */
    private SearchStrategy search;

    /**
     * A timeline that records each key frame for the animation of the search.
     */
    private Timeline timeline = new Timeline();
    /**
     * The number of the current frame being processed.
     */
    private static int frames = 0;

    /**
     * The constructor for a {@code MazeComponent} object. 
     * @param maze
     * @param size
     */
    public MazeComponent(Type[][] maze, int size)
    {
        CELL_SIZE = calculateCellSize(size);
        mazecells = new Rectangle[size][size];

        createGrid(maze, size);
        setMinSize(GRID_SIZE, GRID_SIZE);

        float frameRate = (size / MAX_GRID_SIZE) * MAX_FRAME_RATE;
        timeline.setRate(frameRate);
        frames++;
    }

    /**
     * Sets and renders the inital cell
     * @param row
     * @param col
     */
    public void setInitial(int row, int col)
    {
        mazecells[row][col].setFill(Color.RED);
        addKeyFrame(Duration.ZERO, Color.RED, mazecells[row][col]);
    }

    public void restartTimeline()
    {        
        timeline.getKeyFrames().removeAll(timeline.getKeyFrames());
    }
    
    /**
     * Sets and renders the goal cell
     * @param row
     * @param col
     */
    public void setGoal(int row, int col)
    {
        mazecells[row][col].setFill(Color.GREEN);
        addKeyFrame(Duration.ZERO, Color.GREEN, mazecells[row][col]);
    }

    /**
     * Plays the animation
     */
    public void playAnim()
    {
        timeline.play();
    }

    /**
     * Pauses the animation
     */
    public void pauseAnim()
    {
        timeline.pause();
    }

    /**
     * Steps the animation
     */
    public void stepAnim() 
    {
        Duration current = timeline.getCurrentTime();
        Duration next = current.add(Duration.seconds(1));

        timeline.jumpTo(next);
    }

    /**
     * Part of the {@code Observer} contract. Links the {@code Observable}
     * object to the {@code MazeComponent} object.
     */
    @Override
    public void link(Observable observable) 
    {
        this.search = (SearchStrategy) observable;
    }

    /**
     * Runs whenever the {@code Observable} notifies its {@code Observer} 
     * objects
     */
    @Override
    public void update() 
    {
        animateExpanded();
        animateVisited();
        animateSolution();
        frames++;
    }

    /**
     * Creates the {@code size}x{@code size} grid.
     * @param maze
     * @param size
     */
    private void createGrid(Type[][] maze, int size) {
        
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++) 
            {
                Rectangle rect = createCell(maze, row, col);
                mazecells[row][col] = rect;
                add(rect, col, row);
            }
        }
    }

    /**
     * Creates a cell from a given row and column.
     * @param maze
     * @param row
     * @param col
     * @return
     */
    private Rectangle createCell(Type[][] maze, int row, int col) 
    {    
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);

        Type cell = maze[row][col];

        if (cell == Type.SPACE) {
            rect.setFill(Color.WHITE);
            addKeyFrame(Duration.ZERO, Color.WHITE, rect);
        } else {
            rect.setFill(Color.BLACK);
            addKeyFrame(Duration.ZERO, Color.BLACK, rect);
        }
        
        return rect;
    }

    /**
     * Calculates the size of a cell
     * @param size
     * @return
     */
    private double calculateCellSize(int size) 
    {
        return ((double) GRID_SIZE) / size;
    }

    /**
     * Adds a keyframe on the expanded nodes
     */
    private void animateExpanded() {
        State lastExpanded = search.getLastExpanded();
        int lastExpandedRow = lastExpanded.getBotLocation().getRow();
        int lastExpandedCol = lastExpanded.getBotLocation().getCol();
        
        Rectangle cell = mazecells[lastExpandedRow][lastExpandedCol];
        
        if (!isGoalCell(cell) && !isStartCell(cell) )
            addKeyFrame(Color.ORANGE, cell);
    }

    /**
     * Adds a keyframe on the visited nodes
     */
    private void animateVisited() {
        for (State state: search.getNodesVisited())
        {
            int row = state.getBotLocation().getRow();
            int col = state.getBotLocation().getCol();

            Rectangle visitedCell = mazecells[row][col];
            
            if (!isStartCell(visitedCell) && !isGoalCell(visitedCell))
                addKeyFrame(Color.LIGHT_BLUE, visitedCell);
        }
    }

    /**
     * Adds a keyframe on the solution path
     */
    private void animateSolution() 
    {
        if (!search.getSolutionPath().isEmpty()) 
        {
            State lastSolution = search.peekSolution();
            int row = lastSolution.getBotLocation().getRow();
            int col = lastSolution.getBotLocation().getCol();
            
            Rectangle solutionCell = mazecells[row][col]; 
            
            if (!isStartCell(solutionCell) && !isGoalCell(solutionCell))
                addKeyFrame(Color.LIGHT_GREEN, solutionCell);
        }
    }

    /**
     * Determines whether a cell is a start cell
     * @param cell
     * @return
     */
    private boolean isStartCell(Rectangle cell) {
        return cell.getFill().equals(Color.GREEN);
    }

    /**
     * Determines whether a cell is a goal cell
     * @param cell
     * @return
     */
    private boolean isGoalCell(Rectangle cell) {
        return cell.getFill().equals(Color.RED);
    }

    /**
     * Adds a keyframe based on a given duration.
     * @param duration
     * @param color
     * @param cell
     */
    private void addKeyFrame(Duration duration, Paint color, Rectangle cell)
    {
        KeyValue keyValue = new KeyValue(
            cell.fillProperty(), color, Interpolator.DISCRETE);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }

    /**
     * Adds a keyframe depending on the current frame number.
     * @param color
     * @param cell
     */
    private void addKeyFrame(Paint color, Rectangle cell)
    {
        addKeyFrame(Duration.seconds(frames), color, cell);
    }
}
