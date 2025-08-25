import java.util.*;

public class Flow {

    public static List<int[]> findEdges(int[][] edges, int[] capacities, int s, int t) {
        int n = getNumberOfNodes(edges);
        ResidualGraph graph = new ResidualGraph(n);

        // Build the graph
        for (int i = 0; i < edges.length; i++) {
            graph.addEdge(edges[i][0], edges[i][1], capacities[i]);
        }

        // Compute the initial max flow
        int initialMaxFlow = fordFulkerson(graph, s, t);

        List<int[]> priorityEdges = new ArrayList<>();

        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];

            // Temporarily increase edge capacity
            graph.modifyCapacity(from, to, 1);

            // Recompute max flow
            int newMaxFlow = fordFulkerson(graph, s, t);

            // Check if the edge is a priority edge
            if (newMaxFlow > initialMaxFlow) {
                priorityEdges.add(new int[]{from, to});
            }

            // Restore original capacity
            graph.modifyCapacity(from, to, -1);
        }

        return priorityEdges;
    }

    private static int fordFulkerson(ResidualGraph graph, int source, int target) {
        int maxFlow = 0;

        while (true) {
            Map<Integer, Integer> parentMap = new HashMap<>();
            int bottleneck = bfs(graph, source, target, parentMap);

            if (bottleneck == 0) break;

            // Augment flow along the path
            int current = target;
            while (current != source) {
                int prev = parentMap.get(current);
                graph.addFlow(prev, current, bottleneck);
                current = prev;
            }

            maxFlow += bottleneck;
        }

        return maxFlow;
    }

    private static int bfs(ResidualGraph graph, int source, int target, Map<Integer, Integer> parentMap) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        parentMap.put(source, null);

        Map<Integer, Integer> bottleneck = new HashMap<>();
        bottleneck.put(source, Integer.MAX_VALUE);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Map.Entry<Integer, Integer> neighbor : graph.getNeighbors(current).entrySet()) {
                int next = neighbor.getKey();
                int residualCapacity = neighbor.getValue();

                if (!parentMap.containsKey(next) && residualCapacity > 0) {
                    parentMap.put(next, current);
                    bottleneck.put(next, Math.min(bottleneck.get(current), residualCapacity));

                    if (next == target) {
                        return bottleneck.get(target);
                    }

                    queue.add(next);
                }
            }
        }

        return 0; // No augmenting path found
    }

    private static int getNumberOfNodes(int[][] edges) {
        int maxNode = 0;
        for (int[] edge : edges) {
            maxNode = Math.max(maxNode, Math.max(edge[0], edge[1]));
        }
        return maxNode + 1;
    }

    static class ResidualGraph {
        private final int numVertices;
        private final Map<Integer, Map<Integer, Integer>> capacity;
        private final Map<Integer, Map<Integer, Integer>> flow;

        ResidualGraph(int numVertices) {
            this.numVertices = numVertices;
            this.capacity = new HashMap<>();
            this.flow = new HashMap<>();

            for (int i = 0; i < numVertices; i++) {
                capacity.put(i, new HashMap<>());
                flow.put(i, new HashMap<>());
            }
        }

        void addEdge(int from, int to, int capacityValue) {
            capacity.get(from).put(to, capacityValue);
            capacity.get(to).put(from, 0); // Reverse edge with 0 initial capacity
            flow.get(from).put(to, 0);
            flow.get(to).put(from, 0);
        }

        void modifyCapacity(int from, int to, int delta) {
            capacity.get(from).put(to, capacity.get(from).get(to) + delta);
        }

        void addFlow(int from, int to, int flowValue) {
            flow.get(from).put(to, flow.get(from).get(to) + flowValue);
            flow.get(to).put(from, flow.get(to).get(from) - flowValue);
        }

        Map<Integer, Integer> getNeighbors(int node) {
            return capacity.get(node);
        }
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {0, 2}, {1, 3}};
        int[] capacities = {10, 5, 10, 5, 5};
        int s = 0, t = 3;

        List<int[]> result = findEdges(edges, capacities, s, t);

        System.out.println("Priority edges:");
        for (int[] edge : result) {
            System.out.println("From " + edge[0] + " to " + edge[1]);
        }
    }
}
