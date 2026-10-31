class Solution {
    public int orangesRotting(int[][] grid) {
    if(grid==null || grid.length==0)
    return 0;
    int rows = grid.length;
    int cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int freshCount=0;
    for(int r=0;r<rows;r++){
        for(int c=0;c<cols;c++){
            if(grid[r][c] == 2){
                queue.offer(new int[]{r,c});
            }else if (grid[r][c] == 1){
                freshCount++;
            } 
            
        }

    }

        
                if (freshCount == 0) return 0;
        
        int minutes = 0;
        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean rottedAnyThisMinute = false;
            
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int r = current[0];
                int c = current[1];
                
                for (int[] dir : directions) {
                    int nextR = r + dir[0];
                    int nextC = c + dir[1];
                    if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols && grid[nextR][nextC] == 1) {
                        grid[nextR][nextC] = 2;
                        queue.offer(new int[]{nextR, nextC});
                        freshCount--;
                        rottedAnyThisMinute = true;
                    }
                }
            }
            if (rottedAnyThisMinute) {
                minutes++;
            }
        }
        return freshCount == 0 ? minutes : -1;
    }
}
    
    