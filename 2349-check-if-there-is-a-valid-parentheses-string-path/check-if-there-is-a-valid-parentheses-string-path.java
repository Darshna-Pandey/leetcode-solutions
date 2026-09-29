class Solution {
    private int m, n;
    private char[][] grid;
    private boolean[][][] memo;
    private boolean[][][] visited;

    public boolean hasValidPath(char[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        if ((m + n - 1) % 2 != 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        
        
        memo = new boolean[m][n][(m + n) / 2 + 1];
        visited = new boolean[m][n][(m + n) / 2 + 1];
        
        return dfs(0, 0, 0);
    }
    
    private boolean dfs(int i, int j, int k) {
        
        k += (grid[i][j] == '(') ? 1 : -1;
        
        if (k < 0) {
            return false;
        }
        
        
        if (k > (m - 1 - i) + (n - 1 - j)) {
            return false;
        }
        
        
        if (i == m - 1 && j == n - 1) {
            return k == 0;
        }
        
        if (visited[i][j][k]) {
            return memo[i][j][k];
        }
        
        boolean res = false;
        
        if (i + 1 < m && dfs(i + 1, j, k)) {
            res = true;
        }
        
        if (!res && j + 1 < n && dfs(i, j + 1, k)) {
            res = true;
        }
        
        visited[i][j][k] = true;
        return memo[i][j][k] = res;
    }
}
        
    