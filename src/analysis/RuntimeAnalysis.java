package analysis;

import core.maze.Maze;
import core.search.MazeBot;
import core.search.SearchStrategy;
import core.search.strategy.BFS;
import core.search.strategy.DFS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class RuntimeAnalysis 
{
    private static final String DAT_DIRECTORY = "dat";
    private static final String TESTCASES_DIRECTORY = DAT_DIRECTORY + "/testcases";

    private static ArrayList<Maze> mazes;
    private static SearchStrategy[] searches = {new BFS(), new DFS()};

    private static ArrayList<SampleData> sampleDataList = new ArrayList<SampleData>();

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
    private static ArrayList<Maze> getMazes() throws IOException {
        ArrayList<Maze> mazes = new ArrayList<>();
        File testCasesDir = new File(TESTCASES_DIRECTORY);
        File[] mazeFiles = testCasesDir.listFiles();
        for (File mazeFile : mazeFiles) {
            if (mazeFile.isFile()) {
                Maze maze = new Maze(mazeFile.getAbsolutePath());
                mazes.add(maze);
            }
        }
        return mazes;
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
    private static void getSampleData(SearchStrategy search) throws IOException {
        for (Maze maze : mazes) {
            MazeBot mazebot = new MazeBot(maze);
            mazebot.setSearchStrategy(search);
            long runtime = getRuntime(mazebot);
            int numExplored = mazebot.getNumExplored();
            int numSolution = mazebot.getNumSolution();
            SampleData sampleData = new SampleData(
                    search.getCommonName(),
                    maze.getSize(),
                    runtime,
                    numExplored,
                    numSolution
            );
            sampleDataList.add(sampleData);
        }

        writeSampleDataToFile(sampleDataList);
    }

    private static void writeSampleDataToFile(ArrayList<SampleData> sampleDataList) throws IOException {
        // Create the file object
        File outputFile = new File(DAT_DIRECTORY + "/analysis/sample_data.csv");

        // Create a writer to write to the file
        FileWriter writer = new FileWriter(outputFile);

        // Write the headers to the file
        writer.write("search_name, size, runtime, num_explored, num_solution\n");

        // Loop through each sample data record and write to the file
        for (SampleData sampleData : sampleDataList) {
            writer.write(String.format("%s,%d,%d,%d,%d\n", sampleData.searchname(), sampleData.n(), sampleData.runtime(), sampleData.numExplored(), sampleData.numSolution()));
        }

        // Close the writer
        writer.close();
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
            TestCaseGenerator testCaseGenerator = new TestCaseGenerator(100, n);
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
