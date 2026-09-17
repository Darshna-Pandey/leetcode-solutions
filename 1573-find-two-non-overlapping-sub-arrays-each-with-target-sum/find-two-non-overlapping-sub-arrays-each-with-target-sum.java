class Solution {
    public int minSumOfLengths(int[] arr, int target) {
    int n = arr.length;
        
        
        int[] dp = new int[n];
        
       
        int INF = 100000000; 
        for (int i = 0; i < n; i++) {
            dp[i] = INF;
        }
        
        
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); 
        
        int currentSum = 0;
        int minTotalLength = INF;
        
        for (int i = 0; i < n; i++) {
            currentSum += arr[i];
            map.put(currentSum, i);
            
           
            if (i > 0) {
                dp[i] = dp[i - 1];
            }
            
           
            int neededSum = currentSum - target;
            if (map.containsKey(neededSum)) {
                int startIndex = map.get(neededSum);
                int currentSubarrayLen = i - startIndex;
                
               
                if (startIndex >= 0 && dp[startIndex] != INF) {
                    minTotalLength = Math.min(minTotalLength, currentSubarrayLen + dp[startIndex]);
                }
                
               
                dp[i] = Math.min(dp[i], currentSubarrayLen);
            }
        }

        return minTotalLength >= INF ? -1 : minTotalLength;
       
    }
}