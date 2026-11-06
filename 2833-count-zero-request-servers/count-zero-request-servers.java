class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        int[] ans = new int[m];
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i]; 
            sortedQueries[i][1] = i;          
        }
        
       
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));
        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[0], b[0]));
        int[] serverCount = new int[n + 1]; 
        int uniqueActiveServers = 0;
        int left = 0;
        int right = 0;
        
        for (int i = 0; i < m; i++) {
            int queryTime = sortedQueries[i][0];
            int originalIdx = sortedQueries[i][1];
            
            int windowStart = queryTime - x;
            int windowEnd = queryTime;
            while (right < logs.length && logs[right][1] <= windowEnd) {
                int serverId = logs[right][0];
                if (serverCount[serverId] == 0) {
                    uniqueActiveServers++;
                }
                serverCount[serverId]++;
                right++;
            }
        
            while (left < logs.length && logs[left][1] < windowStart) {
                int serverId = logs[left][0];
                serverCount[serverId]--;
                if (serverCount[serverId] == 0) {
                    uniqueActiveServers--;
                }
                left++;
            }
        
            ans[originalIdx] = n - uniqueActiveServers;
        }
        
        return ans;
    }
}
       
    