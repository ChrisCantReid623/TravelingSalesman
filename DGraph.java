import java.util.*;

/** Models a directed graph where edges between vertices can only be traversed in one direction. */
public class DGraph {

    private ArrayList<LinkedList<Edge>> adjList = new ArrayList<>();
    private int numVertices;

    /** Represents the cost of traversing between adjacent edges. */
    private class Edge {
        int label;
        double weight;

        /** Constructor */
        Edge(int inbound, double inboundWeight) {
            label = inbound;
            weight = inboundWeight;
        }
    }

    /** Constructor
     * @param numVertices the number of vertices. */
    DGraph(int numVertices) {
        this.numVertices = numVertices;
        adjList.add(null);
        for (int i = 1; i < (numVertices + 1); i++)
            adjList.add(new LinkedList<Edge>());
    }

    /** Adds an edge between outbound and inbound vertex destinations.
     * @param outbound outbound vertex
     * @param inbound inbound vertex
     * @param weight edge weight */
    void addEdge(int outbound, int inbound, double weight) {
        adjList.get(outbound).add(new Edge(inbound, weight));
    }

    /** Breadth-First search algorithm.
     * @param start staring vertex */
    void BFS(int start) {
        boolean[] visited = new boolean[numVertices];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start]=true;
        while (queue.size() != 0) {
            int u = queue.pollFirst();
            System.out.print(u+" ");
            for (int i = 0; i < adjList.get(u).size(); i++) {
                int v = adjList.get(u).get(i).label;
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
    }

    /** Depth-First search algorithm
     * @param start starting vertex */
    void DFSI(int start) {
        boolean[] visited = new boolean[numVertices];
        LinkedList<Integer> stack = new LinkedList<>();
        stack.add(start);
        visited[start]=true;
        while (stack.size() != 0) {
            int u = stack.pollLast();
            System.out.print(u+" ");
            for (int i = 0; i < adjList.get(u).size(); i++) {
                int v = adjList.get(u).get(i).label;
                if (!visited[v]) {
                    visited[v] = true;
                    stack.add(v);
                }
            }
        }
    }

    /** Depth-First search algorithm (Recursive)
     * @param start starting vertex */
    void DFS(int start) {
        boolean[] visited = new boolean[numVertices];
        DFSR(start, visited);
    }

    /** Depth-First search algorithm (Recursive)
     * @param start starting vertex */
    void DFSR(int start, boolean[] visited) {
        visited[start] = true;
        System.out.print(start+" ");
        for (int i = 0; i < adjList.get(start).size(); i++) {
            int v = adjList.get(start).get(i).label;
            if (!visited[v])
                DFSR(v, visited);
        }
    }

    /** Optimized traveling salesman search algorithm
     * @param start starting vertex
     * @param currPath empty array object tracking traversal */
    public double tspHeuristic(int start, List<Integer> currPath) {
        boolean[] visited = new boolean[numVertices];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start]=true;
        double tspCost = 0.0;
        while (queue.size() != 0) {
            int queueFirst = queue.pollFirst();
            currPath.add(queueFirst);
            double minEdgeWeight = Double.MAX_VALUE;
            int minVertex = -1;
            for (int i = 0; i < adjList.get(queueFirst).size(); i++) {
                if (adjList.get(queueFirst).get(i).weight < minEdgeWeight
                        && !currPath.contains(adjList.get(queueFirst).get(i).label) ) {
                    minEdgeWeight = adjList.get(queueFirst).get(i).weight;
                    minVertex = adjList.get(queueFirst).get(i).label;
                }
                if (currPath.size() == numVertices && adjList.get(queueFirst).get(i).label == 1) {
                    tspCost += adjList.get(queueFirst).get(i).weight;
                }
            }
            if (minVertex != -1) {
                tspCost += minEdgeWeight;
                queue.add(minVertex);
            }
        }
        return tspCost;
    }

    /** Backtracking traveling salesman search algorithm (Recursive).
     * @param start starting vertex
     * @param currPath empty array object tracking traversal */
    public double tspBacktrack(int start, List<Integer> currPath ) {
        //boolean[] visited = new boolean[numVertices];
        currPath.add(start);
        List<Integer> minPath = new ArrayList<>();
        double minPathCost = tspBacktrack(start, currPath, 0.0, minPath, Integer.MAX_VALUE);
        currPath.clear();
        currPath.addAll(minPath);
        return minPathCost;
    }

    /** Backtracking traveling salesman search algorithm (Recursive).
     * @param start starting vertex
     * @param currPath worker variable for testing hamiltonian cycles
     * @param currCost worker variable for testing the cost of a hamiltonian cycle
     * @param minPath maintains track of the last achieved hamiltonian cycle
     * @param minCost maintains track of the cost of the last achieve hamiltonian cycle */
    double tspBacktrack(int start,
                        List<Integer> currPath, double currCost, List<Integer> minPath, double minCost) {
        if (!currPath.contains(start)) {
            currPath.add(start);
        }

        // Check for Remaining Unvisited Vertices
        if (currPath.size() == numVertices) {
            for (int n = 0; n < adjList.get(start).size(); n++) {
                if (adjList.get(start).get(n).label == 1) {
                    currCost += adjList.get(start).get(n).weight;
                    break;
                }
            }
            if (currCost < minCost) {
                minCost = currCost;
                minPath.clear();
                minPath.addAll(currPath);
                return minCost;
            }
        }

        // Explore Unvisited Vertices
        else {
            for (int i = 0; i < adjList.get(start).size(); i++) {

                int endVertex = adjList.get(start).get(i).label;
                double weight = adjList.get(start).get(i).weight;

                if (!currPath.contains(endVertex)) {

                    currPath.add(endVertex);
                    currCost += weight;
                    minCost = tspBacktrack(endVertex, currPath, currCost, minPath, minCost);

                    // Backtrack
                    currPath.remove(currPath.size()-1);
                    currCost -= weight;
                }
            }
        }
        return minCost;
    }

    /** Optimizes the Heuristic traveling salesman method.
     * @param start starting vertex
     * @param currPath empty array object tracking traversal */
    public double tspMine(int start, List<Integer> currPath) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        double tspCost = 0.0;
        while (queue.size() != 0) {
            int queueFirst = queue.pollFirst();
            currPath.add(queueFirst);
            double minEdgeWeight = Double.MAX_VALUE;
            int minVertex = -1;
            for (int i = 0; i < adjList.get(queueFirst).size(); i++) {
                if (adjList.get(queueFirst).get(i).weight < minEdgeWeight
                        && !currPath.contains(adjList.get(queueFirst).get(i).label) ) {
                    minEdgeWeight = adjList.get(queueFirst).get(i).weight;
                    minVertex = adjList.get(queueFirst).get(i).label;
                }
                if (currPath.size() == numVertices && adjList.get(queueFirst).get(i).label == 1) {
                    tspCost += adjList.get(queueFirst).get(i).weight;
                }
            }
            if (minVertex != -1) {
                tspCost += minEdgeWeight;
                queue.add(minVertex);
            }
        }
        return tspCost;
    }

    /** Outputs time completion results for HEURISTIC method. */
    public void tspHeuristicTime() {
        List<Integer> tspCycle = new ArrayList<>();
        long startTime = System.nanoTime();
        double tspCost = this.tspHeuristic(1, tspCycle);
        long endTime = System.nanoTime();
        System.out.println("heuristic: cost = " + String.format("%.1f",tspCost) + ", " +
                (endTime - startTime)/1000000.0f + " milliseconds");
    }

    /** Outputs time completion results for BACKTRACK method. */
    public void tspBacktrackTime() {
        List<Integer> tspCycle = new ArrayList<>();
        long startTime = System.nanoTime();
        double tspCost = this.tspBacktrack(1, tspCycle);
        long endTime = System.nanoTime();
        System.out.println("backtrack: cost = " + String.format("%.1f",tspCost) + ", " +
                (endTime - startTime)/1000000.0f + " milliseconds");
    }

    /** Outputs time completion results for MINE method. */
    public void tspMineTime() {
        List<Integer> tspCycle = new ArrayList<>();
        long startTime = System.nanoTime();
        double tspCost = this.tspMine(1, tspCycle);
        long endTime = System.nanoTime();
        System.out.println("mine: cost = " + String.format("%.1f",tspCost) + ", " +
                (endTime - startTime)/1000000.0f + " milliseconds");
    }

}