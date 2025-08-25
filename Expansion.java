import java.util.*;

public class Expansion {
    
    public double expansion(int n, float z, int[][] edges, double[] weights) {
        // Step 1: Create adjacency list representation for the graph
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Populate the graph with edges
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0], v = edges[i][1];
            double weight = weights[i];
            graph.get(u).add(new Edge(v, weight));
            graph.get(v).add(new Edge(u, weight));  // since it's an undirected graph
        }
        
        // Step 2: Implement Prim's with modification to handle power plant costs
        boolean[] inMST = new boolean[n];  // Track nodes that are part of the MST
        PriorityQueue<Pair> minHeap = new PriorityQueue<>(Comparator.comparingDouble(pair -> pair.cost));
        
        // Start with the first node by considering building a plant there
        for (int i = 0; i < n; i++) {
            minHeap.offer(new Pair(i, z));  // Initial cost to power each node is building a plant there
        }
        
        double totalCost = 0.0;
        int nodesAdded = 0;
        
        while (!minHeap.isEmpty() && nodesAdded < n) {
            Pair current = minHeap.poll();
            
            if (inMST[current.node]) {
                continue;  // Skip if already in MST
            }
            
            // Mark this node as powered
            inMST[current.node] = true;
            totalCost += current.cost;  // Add the cost to the total (either power plant or edge)
            nodesAdded++;
            
            // Consider the neighboring nodes and add their edge weights if it's cheaper to connect than to build a new plant
            for (Edge neighbor : graph.get(current.node)) {
                if (!inMST[neighbor.node]) {
                    minHeap.offer(new Pair(neighbor.node, Math.min(z, neighbor.weight)));  // Choose minimum between building a plant or connecting via edge
                }
            }
        }
        
        return totalCost;
    }
    
    // Helper class to represent edges in the graph
    class Edge {
        int node;
        double weight;
        
        Edge(int node, double weight) {
            this.node = node;
            this.weight = weight;
        }
    }
    
    // Helper class to represent a node and its associated cost
    class Pair {
        int node;
        double cost;
        
        Pair(int node, double cost) {
            this.node = node;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        Expansion expansion = new Expansion();
        
        // Example usage
        int[][] edges = { {0, 1}, {1, 2}, {0, 2} };
        double[] weights = { 4.0, 2.0, 5.0 };
        double result = expansion.expansion(3, 6.0f, edges, weights);
        System.out.println("Minimum cost: " + result);  // Expected output will depend on the inputs
    }
}
