import java.util.*;

public class UGraph {
    private int numVertices;
    private ArrayList<LinkedList<Integer>> adjList = new ArrayList<>();

    UGraph(int numVertices) {
        this.numVertices = numVertices;
        for (int i = 0; i < numVertices; i++)
            adjList.add(new LinkedList<Integer>());
    }

    void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    void BFS(int start) {
        boolean visited[] = new boolean[numVertices];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start]=true;
        while (queue.size() != 0)
        {
            int u = queue.pollFirst();
            System.out.print(u+" ");
            for (int i = 0; i < adjList.get(u).size(); i++)
            {
                int v = adjList.get(u).get(i);
                if (!visited[v])
                {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
    }

    void DFSI(int start) {
        boolean visited[] = new boolean[numVertices];
        LinkedList<Integer> stack = new LinkedList<>();
        stack.add(start);
        visited[start]=true;
        while (stack.size() != 0) {
            int u = stack.pollLast();
            System.out.print(u+" ");
            for (int i = 0; i < adjList.get(u).size(); i++) {
                int v = adjList.get(u).get(i);
                if (!visited[v]) {
                    visited[v] = true;
                    stack.add(v);
                }
            }
        }
    }

    void DFS(int u) {
        boolean visited[] = new boolean[numVertices];
        DFSR(u, visited);
    }

    void DFSR(int u, boolean visited[]) {
        visited[u] = true;
        System.out.print(u+" ");
        for (int i = 0; i < adjList.get(u).size(); i++)
        {
            int v = adjList.get(u).get(i);
            if (!visited[v])
                DFSR(v, visited);
        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < numVertices; i++)
        {
            str += i + ": [";
            LinkedList<Integer> list = adjList.get(i);
            for(int j = 0; j < list.size(); j++)
                str += list.get(j) + ", ";
            str += "]\n";
        }
        return str;
    }
}