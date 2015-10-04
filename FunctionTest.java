import java.io.*;

/**
 * @author Rui Wang
 */
public class FunctionTest {

    /**
     * Functional test. Read names from input file, print result to output file,
     * as well as standard output
     * @param input_path
     *          path to input file.
     * @param output_path
     *          path to test output file.
     */
    public void FunctionalTest(String input_path, String output_path) {
        Solution s = new Solution();
        try {
            s.processData(input_path);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        String result = s.getLongestNameChain();
        System.out.println(result);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(output_path)));
            writer.write(result);
            writer.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Invalid test parameters");
            return;
        }
        FunctionTest test = new FunctionTest();

        //Test one
        // Input data comes from Wikipedia, the data contains a cycle.
        test.FunctionalTest(args[0], args[1]);

        //Test two
        // Input data is simple, come from the example in email.
        test.FunctionalTest(args[2], args[3]);
    }
}
