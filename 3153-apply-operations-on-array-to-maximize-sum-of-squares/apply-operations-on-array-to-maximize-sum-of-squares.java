

public class Solution {
    public int maxSum(List<Integer> nums, int k) {
        final int MOD = 1_000_000_007;
        final int MAX_BIT = 30; 
        int[] bitCount = new int[MAX_BIT];
        for (int num : nums) {
            for (int i = 0; i < MAX_BIT; i++) {
                if (((num >> i) & 1) == 1) {
                    bitCount[i]++;
                }
            }
        }

        long result = 0;
        
        for (int i = 0; i < k; i++) {
            long val = 0;
            for (int j = 0; j < MAX_BIT; j++) {
                if (bitCount[j] > i) {
                    val |= (1L << j);
                }
            }
            result = (result + (val * val) % MOD) % MOD;
        }

        return (int) result;
    }
}
        
    