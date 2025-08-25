public class Mousepads {

    // Method to find the maximum revenue from a given a x b sheet
    public double maxRevenue(int a, int b, double[][] P) {
        // Create a DP table to store the maximum revenue for each sub-rectangle
        double[][] dp = new double[a + 1][b + 1];

        // Step 1: Initialize the DP table with the given price array
        for (int i = 1; i <= a; i++) {
            for (int j = 1; j <= b; j++) {
                dp[i][j] = P[i][j];
            }
        }

        // Step 2: Fill the DP table using dynamic programming
        for (int i = 1; i <= a; i++) {
            for (int j = 1; j <= b; j++) {
                // Check vertical cuts
                for (int k = 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[i][j - k]);
                }

                // Check horizontal cuts
                for (int k = 1; k < i; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[k][j] + dp[i - k][j]);
                }
            }
        }

        // Return the maximum revenue for the original a x b sheet
        return dp[a][b];
    }

    public static void main(String[] args) {
        int a = 3;
        int b = 5;
        double[][] P = {
            {0, 0, 0, 0, 0, 0},
            {0, 1, 12, 13, 3, 7},
            {0, 8, 27, 30, 11, 9},
            {0, 8, 7, 0, 8, 16}
        };

        Mousepads mp = new Mousepads();
        double result = mp.maxRevenue(a, b, P);
      
    }
}
