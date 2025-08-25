import java.util.ArrayList;
import java.util.Arrays;

public class Bellman {
    
    public ArrayList<Integer> bellman(int n, int t, int[][] edges, double[] weights) {
        double[] dist = new double[n];
        int[] predecessor = new int[n];
        
        // Step 1: Initialize distances and predecessors
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[0] = 0; // Start from vertex 0
        Arrays.fill(predecessor, -1); // Initialize predecessor array

        // Step 2: Relax edges n-1 times
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < edges.length; j++) {
                int u = edges[j][0];
                int v = edges[j][1];
                double weight = weights[j];
                
                if (dist[u] != Double.POSITIVE_INFINITY && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    predecessor[v] = u;
                }
            }
        }

        // Step 3: Perform an additional nth iteration to detect negative cycles
        for (int j = 0; j < edges.length; j++) {
            int u = edges[j][0];
            int v = edges[j][1];
            double weight = weights[j];
            
            if (dist[u] != Double.POSITIVE_INFINITY && dist[u] + weight < dist[v]) {
                return null; // Negative cycle detected
            }
        }

        // Step 4: Reconstruct the shortest path from 0 to t
        ArrayList<Integer> path = new ArrayList<>();
        if (dist[t] == Double.POSITIVE_INFINITY) {
            return path; // No path to target
        }

        for (int at = t; at != -1; at = predecessor[at]) {
            path.add(0, at); // Add vertex to path, prepending to maintain order
        }

        return path;
    }
}
