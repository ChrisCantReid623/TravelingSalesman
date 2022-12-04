import java.util.*;

public class DGraph {

    private ArrayList<LinkedList<Edge>> adjList = new ArrayList<>();
    private int numVertices;

    private class Edge {
        int label;
        double weight;

        Edge(int v, double w) {
            label = v;
            weight = w;
        }
    }

    DGraph(int numVertices) {
        this.numVertices = numVertices;
        adjList.add(null);
        for (int i = 1; i < (numVertices + 1); i++)
            adjList.add(new LinkedList<Edge>());
    }

    void addEdge(int u, int v, double w) {
        adjList.get(u).add(new Edge(v, w));
    }

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

    void DFS(int u) {
        boolean[] visited = new boolean[numVertices];
        DFSR(u, visited);
    }

    void DFSR(int u, boolean[] visited) {
        visited[u] = true;
        System.out.print(u+" ");
        for (int i = 0; i < adjList.get(u).size(); i++) {
            int v = adjList.get(u).get(i).label;
            if (!visited[v])
                DFSR(v, visited);
        }
    }

    public double tspHeuristic(int start, List<Integer> path) {
        boolean[] visited = new boolean[numVertices];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start]=true;
        double tspCost = 0.0;
        while (queue.size() != 0) {
            int u = queue.pollFirst();
            path.add(u);
            double minEdgeWeight = Double.MAX_VALUE;
            int minVertex = -1;
            for (int i = 0; i < adjList.get(u).size(); i++) {
                if (adjList.get(u).get(i).weight < minEdgeWeight && !path.contains(adjList.get(u).get(i).label) ) {
                    minEdgeWeight = adjList.get(u).get(i).weight;
                    minVertex = adjList.get(u).get(i).label;
                }
                if (path.size() == numVertices && adjList.get(u).get(i).label == 1) {
                    tspCost += adjList.get(u).get(i).weight;
                }
            }
            if (minVertex != -1) {
                tspCost += minEdgeWeight;
                queue.add(minVertex);
            }
        }
        System.out.println("cost = " + String.format("%.1f", tspCost) + ", visitOrder = " + path);
        return tspCost;
    }

    public double tspBacktracking( int start, List<Integer> currPath ) {
        boolean[] visited = new boolean[numVertices];
        currPath.add(start);
        List<Integer> minPath = new ArrayList<>();
        double minTripCost = tspBacktracking(start, visited, currPath, 0.0, minPath, Integer.MAX_VALUE);

        System.out.println("cost = " + String.format("%.1f", minTripCost) + ", visitOrder = " + minPath);
        return minTripCost;
    }

    double tspBacktracking(int startVertex, boolean[] visited,
                           List<Integer> currPath, double currCost,
                           List<Integer> minPath, double minCost)
    {
        visited[startVertex-1] = true;

        // Checks for Remaining Unvisited Vertices
        boolean visitedAll = true;
        for (boolean vertexVisited: visited) {
            if (!vertexVisited) {
                visitedAll = false;
                break;
            }
        }

        // BASE CASE: All Vertices Visited (A Hamiltonian Cycle Exists)
        if (visitedAll) {
            for (int n = 0; n < adjList.get(startVertex).size(); n++) {
                if (adjList.get(startVertex).get(n).label == 1) {
                    currCost += adjList.get(startVertex).get(n).weight;
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

        // Explore Vertices Unvisited
        else {
            for (int i = 0; i < adjList.get(startVertex).size(); i++) {
                int endVertex = adjList.get(startVertex).get(i).label;
                double weight = adjList.get(startVertex).get(i).weight;
                if (!visited[endVertex - 1]) {
                    currPath.add(endVertex);
                    currCost += weight;
                    minCost = tspBacktracking(endVertex, visited, currPath, currCost, minPath, minCost);
                    currPath.remove(currPath.size()-1);
                    currCost -= weight;
                    visited[endVertex -1] = false;
                }
            }
        }
        return minCost;
    }

}