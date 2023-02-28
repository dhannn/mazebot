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
    private static ArrayList<String> mazecode = new ArrayList<String>();

    private static ArrayList<SampleData> sampleDataList = new ArrayList<SampleData>();

    public static void main(String[] args) throws IOException 
    {
        generateTestCases();
        mazes = getMazes();
        
        for (SearchStrategy search: searches)
            getSampleData(search);

        writeSampleDataToFile(sampleDataList);
    }
    
    private static ArrayList<Maze> getMazes() throws IOException 
    {
        ArrayList<Maze> mazes = new ArrayList<>();
        File testCasesDir = new File(TESTCASES_DIRECTORY);
        File[] mazeFiles = testCasesDir.listFiles();
        for (File mazeFile : mazeFiles) {
            if (mazeFile.isFile()) {
                Maze maze = new Maze(mazeFile.getAbsolutePath());
                mazes.add(maze);

                String code = mazeFile.getPath().substring(18, 24);
                mazecode.add(code);
            }
        }
        return mazes;
    }

    private static void getSampleData(SearchStrategy search) throws IOException 
    {
        int i = 0;
        for (Maze maze : mazes) {
            System.out.println("Searching Maze #" + (mazes.indexOf(maze) + 1));
            
            MazeBot mazebot = new MazeBot(maze);
            
            SearchStrategy searchInstance = search.instance();
            mazebot.setSearchStrategy(searchInstance);
            
            double runtime = getRuntime(mazebot);
            int numExplored = mazebot.getNumExplored();
            int numSolution = mazebot.getNumSolution();
            SampleData sampleData = new SampleData(
                    mazecode.get(i),
                    search.getCommonName(),
                    maze.getSize(),
                    runtime,
                    numExplored,
                    numSolution
            );
            sampleDataList.add(sampleData);
            i++;
        }
    }

    private static void writeSampleDataToFile(ArrayList<SampleData> sampleDataList) throws IOException {
        // Create the file object
        File outputFile = new File(DAT_DIRECTORY + "/analysis/sample_data.csv");

        // Create a writer to write to the file
        FileWriter writer = new FileWriter(outputFile);

        // Write the headers to the file
        writer.write("filename,search_name,size,runtime,num_explored,num_solution\n");

        // Loop through each sample data record and write to the file
        for (SampleData sampleData : sampleDataList) {
            writer.write(String.format("%s,%s,%d,%.4f,%d,%d\n", sampleData.code(), sampleData.searchname(), sampleData.n(), sampleData.runtime(), sampleData.numExplored(), sampleData.numSolution()));
        }

        // Close the writer
        writer.close();
    }


    private static double getRuntime(MazeBot mazebot)
    {
        long start = System.nanoTime();
        mazebot.search();
        long end = System.nanoTime();

        return (end - start) / 1e6;
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
    public record SampleData(String code,
        String searchname, int n, double runtime, int numExplored, int numSolution) 
    {
    }
}
