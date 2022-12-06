/** Author: Christopher Reid
 * CSC 210 Fall 2022
 * PA11Main.java: Takes as input a directed weighted graph which represents a network of
 * cities in a Traveling Salesman scenario. The graph is stored in a file and the filename is given to the program as a
 * command line argument. The input files are in the matrix market (.mtx) format. */

import java.io.*;
import java.util.*;

public class PA11Main {
    /**
     * Input Via Terminal
     * @param args terminal input, modified with run configurations
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            File filename = new File(args[0]);
            Scanner inputFile = new Scanner(filename);
            fileReader(inputFile, args);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File does not exist.");
        }
    }

    /**
     * Reads in a file containing mapping data and creates a directed graph object.
     *
     * @param inputFile a scanner object with vertex/edge data
     * @param commands  the algorithm method for traversing to find the most efficient route
     */
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

    /**
     * Processes map data from text file, storing it in a directed graph.
     *
     * @param inputFile a scanner object with vertex/edge data
     * @param map       a directed graph storing the vertex/edge data
     */
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

    /**
     * Performs the desired search algorithm for the 'shortest' path through a series of vertices.
     *
     * @param map      a directed graph storing the vertex/edge data
     * @param commands the algorithm method for traversing to find the most efficient route
     */
    public static void travelingSalesman(DGraph map, String[] commands) {
        for (int i = 1; i < commands.length; i++) {
            List<Integer> currPath = new ArrayList<Integer>();

            if (commands[i].equals("HEURISTIC")) {
                double minPathCost = map.tspHeuristic(1, currPath );
                System.out.println("cost = " + String.format("%.1f", minPathCost) + ", visitOrder = " + currPath);
            }
            if (commands[i].equals("BACKTRACK")) {
                double minPathCost = map.tspBacktrack(1, currPath );
                System.out.println("cost = " + String.format("%.1f", minPathCost) + ", visitOrder = " + currPath);
            }

            if (commands[i].equals("MINE")) {
                double minPathCost = map.tspMine(1, currPath );
                System.out.println("cost = " + String.format("%.1f", minPathCost) + ", visitOrder = " + currPath);
            }

            if (commands[i].equals("TIME")) {
                map.tspHeuristicTime();
                map.tspBacktrackTime();
                map.tspMineTime();
            }
        }
    }
}
