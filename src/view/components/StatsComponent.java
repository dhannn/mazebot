package view.components;

import java.util.HashSet;

import core.search.SearchStrategy;
import core.search.State;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import utils.Color;
import utils.Observable;
import utils.Observer;

public class StatsComponent extends TextFlow implements Observer
{
    private static Text txtNumExpanded;
    private static Text txtNumExpandedData;
    private static Text txtNumVisited;
    private static Text txtNumVisitedData;
    private static Text txtSolution;
    private static Text txtSolutionData;

    private HashSet<State> visited;
    private State lastExpanded = null;
    private Timeline timeline;

    private int numExpanded = 0;
    private int numVisited = 0;
    private int numSolution = 0;

    private int frames = 0;

    private SearchStrategy search;

    public StatsComponent(int size)
    {
        visited = new HashSet<State>();

        timeline = new Timeline();
        timeline.setRate((size / 64f) * 60);
        setLineSpacing(1);

        txtNumExpanded = new Text("Number of states expanded");
        txtNumExpanded.setFill(Color.BLACK);
        txtNumExpanded.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        getChildren().add(txtNumExpanded);
        
        txtNumExpandedData = new Text();
        txtNumExpandedData.setText("\n0 states");
        txtNumExpandedData.setFill(Color.BLACK);
        txtNumExpandedData.setFont(Font.font("Arial", 16));
        getChildren().add(txtNumExpandedData);

        txtNumVisited = new Text("\n\nNumber of states visited");
        txtNumVisited.setFill(Color.BLACK);
        txtNumVisited.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        getChildren().add(txtNumVisited);
        
        txtNumVisitedData = new Text();
        txtNumVisitedData.setText("\n0 states");
        txtNumVisitedData.setFill(Color.BLACK);
        txtNumVisitedData.setFont(Font.font("Arial", 16));
        getChildren().add(txtNumVisitedData);

        txtSolution = new Text("\n\nNumber of states in solution");
        txtSolution.setFill(Color.BLACK);
        txtSolution.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        getChildren().add(txtSolution);
        
        txtSolutionData = new Text();
        txtSolutionData.setText("\n0 states");
        txtSolutionData.setFill(Color.BLACK);
        txtSolutionData.setFont(Font.font("Arial", 16));
        getChildren().add(txtSolutionData);

        setPadding(new Insets(25));  
    }

    @Override
    public void link(Observable observable) 
    {
        search = (SearchStrategy) observable;
    }

    @Override
    public void update() 
    {
        visited.addAll(search.getNodesVisited());
        numVisited = visited.size();        

        if (lastExpanded != search.getLastExpanded())
        {
            lastExpanded = search.getLastExpanded();
            numExpanded++;
        }

        if (search.getSolutionPath().size() > 0)
        {
            numSolution++;
        }

        setText();
        frames++;
    }
    
    private void setText()
    {
        KeyValue expandedKeyValue = new KeyValue(
            txtNumExpandedData.textProperty(), 
            "\n" + numExpanded + " states");

        KeyValue visitedKeyValue = new KeyValue(
            txtNumVisitedData.textProperty(), 
            "\n" + numVisited + " states");

        
        KeyValue solutionKeyValue = null;
        if (search.isDone() && !search.isFound()) {
            solutionKeyValue = new KeyValue(
            txtSolutionData.textProperty(), 
            "\nNo solution has been found!");
        } else {
            solutionKeyValue = new KeyValue(
            txtSolutionData.textProperty(), 
            "\n" + numSolution + " states");
        }
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frames), expandedKeyValue, visitedKeyValue, solutionKeyValue);
        timeline.getKeyFrames().add(keyFrame);
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
}
