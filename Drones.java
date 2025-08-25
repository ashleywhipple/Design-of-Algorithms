import java.util.ArrayList;
import java.util.List;

public class Drones {
    
    /**
     * Reduce the DroneFrequency problem to a 3-SAT instance.
     * 
     * @param n     The number of drones (vertices).
     * @param pairs A 2D array where each element is a 2-length array indicating
     *              a pair of drones that cannot use the same frequency.
     * @return A list of clauses representing the 3-SAT problem. Each clause is an
     *         array of integers corresponding to a disjunction of up to 3 literals.
     */
    public List<int[]> reduceTo3SAT(int n, int[][] pairs) {
        List<int[]> clauses = new ArrayList<>();
        
        // Step 1: Ensure each drone is assigned at least one frequency.
        for (int i = 0; i < n; i++) {
            // (x1 OR x2 OR x3) for each drone i (frequencies F1, F2, F3)
            clauses.add(new int[] { getLiteral(i, 1), getLiteral(i, 2), getLiteral(i, 3) });
        }
        
        // Step 2: Ensure no drone is assigned more than one frequency.
        for (int i = 0; i < n; i++) {
            // (-x1 OR -x2), (-x1 OR -x3), (-x2 OR -x3)
            clauses.add(new int[] { -getLiteral(i, 1), -getLiteral(i, 2) });
            clauses.add(new int[] { -getLiteral(i, 1), -getLiteral(i, 3) });
            clauses.add(new int[] { -getLiteral(i, 2), -getLiteral(i, 3) });
        }
        
        // Step 3: Ensure drones that are too close do not use the same frequency.
        for (int[] pair : pairs) {
            int droneA = pair[0];
            int droneB = pair[1];
            // (-x1_A OR -x1_B), (-x2_A OR -x2_B), (-x3_A OR -x3_B)
            for (int f = 1; f <= 3; f++) {
                clauses.add(new int[] { -getLiteral(droneA, f), -getLiteral(droneB, f) });
            }
        }
        
        return clauses;
    }

    /**
     * Get the integer literal representation for a given drone and frequency.
     * 
     * @param drone     The index of the drone (0-based).
     * @param frequency The frequency (1, 2, or 3).
     * @return The integer literal representing the Boolean variable.
     */
    private int getLiteral(int drone, int frequency) {
        // Convert (drone, frequency) to a unique positive integer:
        // (drone * 3 + frequency)
        return drone * 3 + frequency;
    }

    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
        Drones drones = new Drones();
        int n = 3; // Number of drones
        int[][] pairs = { { 0, 1 }, { 1, 2 } }; // Pairs of conflicting drones

        // // Reduce to 3-SAT and print the resulting clauses
        // List<int[]> clauses = drones.reduceTo3SAT(n, pairs);
        // for (int[] clause : clauses) {
        //     System.out.print("[ ");
        //     for (int literal : clause) {
        //         System.out.print(literal + " ");
        //     }
        //     System.out.println("]");
        // }
    }
}
