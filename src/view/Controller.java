package view;
import java.io.IOException;
import java.util.ArrayList;
import core.maze.Maze;
import core.maze.Cell.Type;
import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.State;
import core.search.strategy.BFS;
import core.search.strategy.DFS;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Getter;
import view.components.MazeComponent;
import view.components.StatsComponent;

public class Controller 
{
    @Getter private static String mazefile;
    @Getter private static Maze maze;

    @Getter private static Scene active;
    private static LoadMazeView loadMazeView;
    private static AnimationView animationView;
    
    private static StatsComponent statsComponent;
    private static MazeComponent mazeComponent;

    private static SearchStrategy[] searches = {new DFS(), new BFS()};
    private static SearchStrategy chosenSearch;

    private static MazeBot mazeBot;

    public static void start()
    {
        loadMazeView = new LoadMazeView();
        active = new Scene(loadMazeView);
    }
    
    public static void loadMaze()
    {
        try {
            mazefile = loadMazeView.getMazefile();
            maze = new Maze(mazefile);
            mazeBot = new MazeBot(maze);

            Type[][] types = maze.getCellTypes();
            int size = maze.getSize();

            mazeComponent = new MazeComponent(types, size);
            statsComponent = new StatsComponent(size);
            
            int goalcol = maze.getGoalCell().getCol();
            int goalrow = maze.getGoalCell().getRow();
            int initcol = maze.getInitialCell().getCol();
            int initrow = maze.getInitialCell().getRow();
            mazeComponent.setInitial(initrow, initcol);
            mazeComponent.setGoal(goalrow, goalcol);
            animationView = new AnimationView(mazeComponent, statsComponent, getAlgorithmNames());
            active.setRoot(animationView);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, "Make sure you have the right path.");
            alert.setTitle("File not found");
            alert.setHeaderText("We cannot find maze!");
            alert.show();

            System.err.println("The maze file does not exist.");
        }
    }

    public static void getChosenAlgorithm()
    {
        String chosen = animationView.getChosenAlgorithm();

        for (SearchStrategy search: searches)
        {
            String commonName = search.getCommonName();
            if (commonName.equals(chosen))
            {
                chosenSearch = search;
                break;
            }
        }

        mazeBot.setSearchStrategy(chosenSearch);
        chosenSearch.attach(mazeComponent);
        chosenSearch.attach(statsComponent);
    }

    public static void search()
    {
        mazeBot.search();
        mazeComponent.playAnim();
        statsComponent.playAnim();
    }

    private static ArrayList<String> getAlgorithmNames()
    {
        ArrayList<String> names = new ArrayList<String>();
        
        for (SearchStrategy search: searches)
        {
            String commonName = search.getCommonName();
            names.add(commonName);
        }

        return names;
    }
}
