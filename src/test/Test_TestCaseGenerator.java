package test;

import java.io.IOException;

import analysis.TestCaseGenerator;

public class Test_TestCaseGenerator 
{
    public static void main(String[] args) throws IOException
    {
        for (int n = 4; n <= 64; n = n << 1)
        {
            TestCaseGenerator testCaseGenerator = new TestCaseGenerator(20, n);
            testCaseGenerator.printToFile("D:\\Y2T2\\CSINTSY\\MCO1_StateBasedSearch\\source\\dat\\testcases");
        }

    }    
}
