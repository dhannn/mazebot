package view.components;

import java.util.HashSet;

import core.search.SearchStrategy;
import core.search.State;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import utils.Color;
import utils.Observable;
import utils.Observer;

/**
 * This class encapsulates the rendered stats of the chosen search algorithm.
 * The search algorithm contains three pertinent stats - the states visited 
 * (or the states known but not its neighbors), the states expanded (when 
 * its neighbors are known) and the states in the solution.
 */
public class StatsComponent extends TextFlow implements Observer
{
    /**
     * Message when no solution has been found.
     */
    private final static String NO_SOLUTION_MSG = 
        "m_ai_zbot cannot find solution!";

    /**
     * The value of the stats when initialized
     */
    private final static String DEFAULT_STAT = "0 states";

    /**
     * Integral value of the visited stat
     */
    private final static int VISITED = 0;
    /**
     * Integral value of the expanded stat
     */
    private final static int EXPANDED = 1;
    /**
     * Integral value of the solution stat
     */
    private final static int SOLUTION = 2;
    
    /**
     * All pertinent stats in the search
     */
    private static Stat[] stats = new Stat[3];
    
    /**
     * The timeline used for recording animation
     */
    private Timeline timeline = new Timeline();
    /**
     * The search algorithm used as the {@code Observable} object
     */
    private SearchStrategy search;
    /**
     * The current frame being rendered
     */
    private int frames = 1;

    /**
     * Contains the visited states
     */
    private HashSet<State> visited = new HashSet<State>();
    /**
     * The last expanded node
     */
    private State lastExpanded = null;

    /**
     * Constructor for the {@code StatsComponent} object
     * @param size
     */
    public StatsComponent(int size)
    {
        stats[VISITED] = new Stat("Number of states visited", DEFAULT_STAT);
        stats[EXPANDED] = new Stat("Number of states expanded", DEFAULT_STAT);
        stats[SOLUTION] = new Stat(
            "Number of states in the solution", DEFAULT_STAT);

        for (Stat stat: stats)
        {
            stat.styleStat();
            stat.addToParent(this);
        }

        timeline.setRate((size / 64f) * 60);
        setLineSpacing(1);
        setPadding(new Insets(25));  
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

    public void restartTimeline()
    {
        timeline.getKeyFrames().removeAll(timeline.getKeyFrames());
    }

    @Override
    public void link(Observable observable) 
    {
        search = (SearchStrategy) observable;
    }

    @Override
    public void update() 
    {
        if (lastExpanded != search.getLastExpanded())
        {
            lastExpanded = search.getLastExpanded();
            stats[EXPANDED].increment();
        }
        
        visited.add(lastExpanded);
        visited.addAll(search.getNodesVisited());
        stats[VISITED].setValue(visited.size());

        if (search.getSolutionPath().size() > 0)
            stats[SOLUTION].increment();

        addKeyframe();
        frames++;
    }
    
    /**
     * Adds a keyframe to the timeline based on the current stats
     */
    private void addKeyframe()
    {
        KeyValue[] keyvals = new KeyValue[3];

        for (int i = VISITED; i <= SOLUTION; i++)
        {
            KeyValue kVal = stats[i].getAnimKeyValue();
            keyvals[i] = kVal;
        }

        if (search.isDone() && !search.isFound())
            keyvals[SOLUTION] 
                = stats[SOLUTION].getAnimKeyValue(NO_SOLUTION_MSG);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frames), keyvals);
        timeline.getKeyFrames().add(keyFrame);
    }

    private class Stat
    {
        private Text keyView;
        private Text valueView;
        @Getter @Setter private int value;

        private final static Font KEY_FONT = Font.font("Arial", 
            FontWeight.BOLD, 13);
        private final static Font VAL_FONT = Font.font("Arial", 16);

        private final static Paint DEFAULT_COLOR = Color.BLACK;

        public Stat(String key, String value)
        {
            keyView = new Text(key + "\n");
            valueView = new Text(value + "\n");

            this.value = 0;
        }

        public void increment()
        {
            value++;
        }

        public void styleStat()
        {
            keyView.setFont(KEY_FONT);
            valueView.setFont(VAL_FONT);

            keyView.setFill(DEFAULT_COLOR);
            valueView.setFill(DEFAULT_COLOR);
        }

        public void addToParent(StatsComponent root)
        {
            root.getChildren().add(keyView);
            root.getChildren().add(valueView);
        }

        public KeyValue getAnimKeyValue()
        {
            return new KeyValue(valueView.textProperty(), value + " states\n");
        }

        public KeyValue getAnimKeyValue(String override)
        {
            return new KeyValue(valueView.textProperty(), override);
        }
    }
}
