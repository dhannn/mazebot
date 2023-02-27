package analysis;

import java.io.IOException;
import java.util.ArrayList;

import core.maze.Maze;
import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.strategy.BFS;
import core.search.strategy.DFS;

public class RuntimeAnalysis 
{
    private static final String DAT_DIRECTORY = "dat";
    private static final String TESTCASES_DIRECTORY = DAT_DIRECTORY + "/testcases";

    private static ArrayList<Maze> mazes = new ArrayList<Maze>();
    private static SearchStrategy[] searches = {new BFS(), new DFS()};

    private static ArrayList<SampleData> sampleData = new ArrayList<SampleData>();

    public static void main(String[] args) throws IOException 
    {
        generateTestCases();
        mazes = getMazes();
        
        for (SearchStrategy search: searches)
            getSampleData(search);

    }
    /**
     * TODO:    Create a function that will loop through the TESTCASES_DIRECTORY
     *          and create a maze object out of each file. Return the arraylist
     *          of all maze objects created.
     */
    private static ArrayList<Maze> getMazes()
    {
        return null;
    }

    /**
     * TODO:    Loop through each maze in the arraylist. Create a mazebot object
     *          that takes in the current maze. Then, set the search strategy
     *          of the mazebot. 
     * 
     *          Afterwards, call the getRuntime() function and store it to a variable. 
     * 
     *          Finally, add the pertinent details to a SampleData record 
     *          (see how to use Record in Java; here's a link: 
     *          https://www.baeldung.com/java-record-keyword). Add it to the 
     *          sampleData arraylist.
     */
    private static void getSampleData(SearchStrategy search)
    {
        MazeBot mazebot = new MazeBot(null);
        mazebot.setSearchStrategy(search);
        
    }

    private static long getRuntime(MazeBot mazebot)
    {
        long start = System.nanoTime();
        mazebot.search();
        long end = System.nanoTime();

        return (end - start);
    }

    private static void generateTestCases() throws IOException 
    {
        for (int n = 4; n <= 64; n = n << 1)
        {
            TestCaseGenerator testCaseGenerator = new TestCaseGenerator(1, n);
            testCaseGenerator.printToFile(TESTCASES_DIRECTORY);
        }
    }

    /**
     * Runtime
     */
    public record SampleData(
        String searchname, int n, long runtime, int numExplored, int numSolution) 
    {
    }
}
