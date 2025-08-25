import java.util.*;

public class NotUnited {

    public static int[][] notUnited(int[] A, int[] B) {
        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(B));
        
        int m = A.length;
        int n = B.length;

        // Check if the sums of A and B are equal
        if (Arrays.stream(A).sum() != Arrays.stream(B).sum()) {
            return null;
        }

        // Create a list of pairs (connections, index) for A, and sort by connections in descending order
        List<int[]> A_sorted = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            A_sorted.add(new int[] {A[i], i});
        }

        A_sorted.sort((a, b) -> Integer.compare(b[0], a[0]));  // Sort in descending order by connections

        // Initialize the connection matrix with 0s
        int[][] f_matrix = new int[m][n];

        // Create a max-heap for B using a priority queue (we invert the connection counts to simulate max-heap behavior)
        PriorityQueue<int[]> B_heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for (int j = 0; j < B.length; j++) {
            if (B[j] > 0) {
                B_heap.offer(new int[] {-B[j], j});  // Negative count for max-heap behavior
            }
        }

        // Iterate through each node in A (sorted by number of connections)
        for (int[] a_node : A_sorted) {
            int connections = a_node[0];
            int a_idx = a_node[1];

            // If the current node requires more connections than available in B, return null
            if (connections > B_heap.size()) {
                return null;
            }

            List<int[]> cols_used = new ArrayList<>();
            for (int i = 0; i < connections; i++) {
                if (B_heap.isEmpty()) {
                    return null;
                }

                // Get the node from B with the most remaining connections
                int[] b_node = B_heap.poll();
                int b_count = -b_node[0];  // Convert back to positive
                int b_idx = b_node[1];

                // Mark this connection in the matrix
                f_matrix[a_idx][b_idx] = 1;
                b_count--;

                // If there are no more connections left, skip pushing it back
                if (b_count < 0) {
                    return null;  // This check is the same as in the Python code
                }
                if (b_count > 0) {
                    cols_used.add(new int[] {-b_count, b_idx});  // Add remaining connections back to the heap
                }
            }

            // Push back the nodes in B that were used but still have available connections
            for (int[] item : cols_used) {
                B_heap.offer(item);
            }
        }

        // Return the connection matrix
        return f_matrix;
    }

    public static void main(String[] args) {
        // Example input
        int[] A = {3, 2, 1};
        int[] B = {2, 2, 2};
        
        int[][] result = notUnited(A, B);
        if (result != null) {
            System.out.println("Connection matrix:");
            for (int[] row : result) {
                System.out.println(Arrays.toString(row));
            }
        } else {
            System.out.println("No valid connection matrix found.");
        }
    }
}
