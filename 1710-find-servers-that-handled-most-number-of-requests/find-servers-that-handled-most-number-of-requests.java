class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int[] cnt = new int[k];
        PriorityQueue<int[]> busy = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        TreeSet<Integer> free = new TreeSet<>();
        
        for (int i = 0; i < k; ++i) {
            free.add(i);
        }
        
        int maxCnt = 0;
        
        for (int i = 0; i < arrival.length; ++i) {
            int start = arrival[i];
            
            
            while (!busy.isEmpty() && busy.peek()[0] <= start) {
                free.add(busy.poll()[1]);
            }
            
            if (free.isEmpty()) {
                continue; 
            }
            
            Integer server = free.ceiling(i % k);
            if (server == null) {
                server = free.first(); 
            }
            
            free.remove(server);
            cnt[server]++;
            maxCnt = Math.max(maxCnt, cnt[server]);
            busy.offer(new int[]{start + load[i], server});
        }
        
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; ++i) {
            if (cnt[i] == maxCnt) {
                ans.add(i);
            }
        }
        
        return ans;
    }
}
        