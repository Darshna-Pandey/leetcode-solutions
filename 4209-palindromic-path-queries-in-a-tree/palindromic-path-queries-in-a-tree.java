import java.util.*;

class Solution {
    private List<Integer>[] adj;
    private int[] inTime;
    private int[] outTime;
    private int timer;
    private int[][] up;
    private int LOG;
    private class BIT {
        int size;
        int[] tree;
        
        BIT(int n) {
            this.size = n;
            this.tree = new int[n + 2];
        }
        
        void update(int idx, int val) {
            for (; idx <= size; idx += idx & -idx) {
                tree[idx] ^= val;
            }
        }
        
        int query(int idx) {
            int mask = 0;
            for (; idx > 0; idx -= idx & -idx) {
                mask ^= tree[idx];
            }
            return mask;
        }
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        inTime = new int[n];
        outTime = new int[n];
        timer = 1;
        
        LOG = 0;
        while ((1 << LOG) <= n) LOG++;
        up = new int[n][LOG];
        
        char[] currentChars = s.toCharArray();
        
       
        dfs(0, 0);
        
        BIT bit = new BIT(timer);
        
        
        for (int i = 0; i < n; i++) {
            int mask = 1 << (currentChars[i] - 'a');
            bit.update(inTime[i], mask);
            bit.update(outTime[i] + 1, mask);
        }
        
        List<Boolean> result = new ArrayList<>();
        
        
        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int node = Integer.parseInt(parts[1]);
                char newChar = parts[2].charAt(0);
                
                int oldMask = 1 << (currentChars[node] - 'a');
                int newMask = 1 << (newChar - 'a');
                int changeMask = oldMask ^ newMask;
                
                currentChars[node] = newChar;
                
                
                bit.update(inTime[node], changeMask);
                bit.update(outTime[node] + 1, changeMask);
                
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                
                int lcaNode = getLCA(u, v);
                
                int maskU = bit.query(inTime[u]);
                int maskV = bit.query(inTime[v]);
                int maskLCAChar = 1 << (currentChars[lcaNode] - 'a');
                
               
                int pathMask = maskU ^ maskV ^ maskLCAChar;
                
                
                result.add((pathMask == 0) || ((pathMask & (pathMask - 1)) == 0));
            }
        }
        
        return result;
    }
    
    private void dfs(int u, int p) {
        inTime[u] = timer++;
        up[u][0] = p;
        for (int i = 1; i < LOG; i++) {
            up[u][i] = up[up[u][i - 1]][i - 1];
        }
        
        for (int v : adj[u]) {
            if (v != p) {
                dfs(v, u);
            }
        }
        outTime[u] = timer++;
    }
    
    private boolean isAncestor(int u, int v) {
        return inTime[u] <= inTime[v] && outTime[u] >= outTime[v];
    }
    
    private int getLCA(int u, int v) {
        if (isAncestor(u, v)) return u;
        if (isAncestor(v, u)) return v;
        
        for (int i = LOG - 1; i >= 0; i--) {
            if (!isAncestor(up[u][i], v)) {
                u = up[u][i];
            }
        }
        return up[u][0];
    }
}
