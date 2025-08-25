import java.util.Arrays;

public class Range {
    
    // Function to count the number of elements in the range [x, y]
    public int rangeCount(int[] A, int x, int y) {
        // Find the index of the first element >= x (left bound)
        int left = findLeftBound(A, x);
        
        // Find the index of the first element > y (right bound)
        int right = findRightBound(A, y);
        
        // Return the number of elements in the range [x, y]
        return right - left;
    }
    
    // Helper function to find the first element >= x using binary search
    private int findLeftBound(int[] A, int x) {
        int left = 0, right = A.length;
        
        // Binary search to find the left bound (first element >= x)
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (A[mid] < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    // Helper function to find the first element > y using binary search
    private int findRightBound(int[] A, int y) {
        int left = 0, right = A.length;
        
        // Binary search to find the right bound (first element > y)
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (A[mid] <= y) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    public static void main(String[] args) {
        Range range = new Range();
        
        int[] A = {1, 3, 5, 7, 9, 11, 13};
        int x = 4;
        int y = 10;
        
        // Test the rangeCount method
        System.out.println(range.rangeCount(A, x, y));  // Output: 3 (elements: 5, 7, 9)
    }
}
