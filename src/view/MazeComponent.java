package view;

import core.maze.Maze;
import core.maze.Cell.Type;
import core.maze.generator.CellNode;
import core.search.SearchStrategy;
import core.search.State;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.Observable;
import utils.Observer;

public class MazeComponent extends GridPane implements Observer
{
    private static final int GRID_SIZE = 500;
    private final double CELL_SIZE;
    Rectangle[][] mazecells;
    SearchStrategy search;

    Timeline timeline = new Timeline();
    private int frameExpanded = 0;
    private int frameVisited = 0;
    private int frameSolution = 0;
    ;

    public MazeComponent(Type[][] maze, int size)
    {
        CELL_SIZE = ((double) GRID_SIZE) / size;
        setMinSize(GRID_SIZE, GRID_SIZE);
        // setGridLinesVisible(true);
        mazecells = new Rectangle[size][size];

        timeline.setRate((size / 64f) * 60);

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++) 
            {
                Type cell = maze[i][j];

                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                mazecells[i][j] = rect;

                if (cell == Type.SPACE){
                    rect.setFill(Color.LIGHT_BLUE);
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

    @Override
    public void link(Observable observable) 
    {
        this.search = (SearchStrategy) observable;
    }

    @Override
    public void update() 
    {
        int row = search.getLastExpanded().getBotLocation().getRow();
        int col = search.getLastExpanded().getBotLocation().getCol();

        // System.out.println(mazecells[row][col].getFill());
        if (mazecells[row][col].getFill() !=  Color.RED && mazecells[row][col].getFill() != Color.GREEN)
        {
            KeyValue kv1 = new KeyValue(mazecells[row][col].fillProperty(), Color.BLUE, Interpolator.DISCRETE);
            KeyValue kv2 = new KeyValue(mazecells[row][col].fillProperty(), Color.ORANGE, Interpolator.DISCRETE);
            KeyFrame kf1 = new KeyFrame(Duration.seconds(frameVisited), kv1);
            KeyFrame kf2 = new KeyFrame(Duration.seconds(frameVisited + 1), kv2);
            timeline.getKeyFrames().add(kf1);
            timeline.getKeyFrames().add(kf2);
        }

        for (State state: search.getNodesVisited())
        {
            
            int row2 = state.getBotLocation().getRow();
            int col2 = state.getBotLocation().getCol();
            if (mazecells[row2][col2].getFill() == Color.GREEN) 
                continue;
            
            KeyValue kvs = new KeyValue(mazecells[row2][col2].fillProperty(), Color.LIGHT_BLUE, Interpolator.DISCRETE);
            KeyValue kve = new KeyValue(mazecells[row2][col2].fillProperty(), Color.BLUE, Interpolator.DISCRETE);
            KeyFrame kfs = new KeyFrame(Duration.seconds(frameExpanded), kvs);
            KeyFrame kfe = new KeyFrame(Duration.seconds(frameExpanded + 1), kve);
            timeline.getKeyFrames().add(kfs);
            timeline.getKeyFrames().add(kfe);
        }
        
        
        if (!search.getSolutionPath().isEmpty()) {
            int row3 = search.getSolutionPath().peek().getBotLocation().getRow();
            int col3 = search.getSolutionPath().peek().getBotLocation().getCol();
            if (mazecells[row3][col3].getFill() != Color.GREEN) 
            {
                KeyValue kvs = new KeyValue(mazecells[row3][col3].fillProperty(), Color.ORANGE, Interpolator.DISCRETE);
                KeyValue kve = new KeyValue(mazecells[row3][col3].fillProperty(), Color.LIGHT_GREEN, Interpolator.DISCRETE);
                KeyFrame kfs = new KeyFrame(Duration.seconds(frameExpanded), kvs);
                KeyFrame kfe = new KeyFrame(Duration.seconds(frameExpanded + 1), kve);
                timeline.getKeyFrames().add(kfs);
                timeline.getKeyFrames().add(kfe);
            }
            
        }
        
        frameVisited++;
        frameExpanded++;
    }
}
