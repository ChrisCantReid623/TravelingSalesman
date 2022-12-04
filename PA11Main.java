import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class PA11Main {

    /** TODO:
     * Implement MINE Traveling Salesman Algorithm
     * Documentation
     */


    /** Input Via Terminal - Edit Run Configuration */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            File filename = new File(args[0]);
            Scanner inputFile = new Scanner(filename);
            fileReader(inputFile, args);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File does not exist.");
        }
    }

    /** Input Via Console */
//    public static void main(String[] args) throws FileNotFoundException {
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter the name of a file: ");
//        String fileName = input.nextLine();
//
//        try {
//            File file = new File( fileName );
//            Scanner inputFile = new Scanner( file );
//            String[] commands = new String[] {"DummyFileName", "BACKTRACK"};
//            fileReader(inputFile, commands);
//        }
//        catch (FileNotFoundException e) {
//            System.out.println("Error: File does not exist.");
//        }
//    }

    public static void fileReader(Scanner inputFile, String[] commands) {
        // Parse Through Header Data
        String currLine = inputFile.nextLine();
        while (inputFile.hasNextLine() && currLine.charAt(0) == '%') {
            currLine = inputFile.nextLine();
        }

        // Create Directed Graph - Initiate Vertices
        String[] lineSplit = currLine.split(" +");
        DGraph map = new DGraph(Integer.parseInt(lineSplit[0]));

        // Mapping Directed Graph and Traversal
        mapping(inputFile, map);
        travelingSalesman(map, commands);
    }

    public static DGraph mapping(Scanner inputFile, DGraph map) {
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] splits = line.split(" +");
            int outFlow = Integer.parseInt(splits[0]);
            int inFlow = Integer.parseInt(splits[1]);
            double weight = Double.parseDouble(splits[2]);
            map.addEdge(outFlow, inFlow, weight);
        }
        return map;
    }

    public static void travelingSalesman(DGraph map, String[] commands) {

        for (int i = 1; i < commands.length; i++) {
            if (commands[i].equals("HEURISTIC")) {
                map.tspHeuristic(1, new ArrayList<>());
            }
            if (commands[i].equals("BACKTRACK")) {
                map.tspBacktracking(1, new ArrayList<>());
            }
            if (commands[i].equals("MINE")) {
                //TODO Improve on Backtrack and Heuristic
                System.out.println("TODO: MINE");
            }
        }
    }
}
