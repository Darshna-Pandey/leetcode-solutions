import java.util.*;

class Solution {
    
    static class State {
        long weight;
        List<Integer> indices;
        State(long w, List<Integer> idx) {
            weight = w;
            indices = idx;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        
        int[][] arr = new int[n][4]; 
        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        
        Arrays.sort(arr, (a, b) -> Integer.compare(a[1], b[1]));

        
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            int l = 0, r = i - 1, p = -1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (arr[mid][1] < arr[i][0]) {
                    p = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            prev[i] = p;
        }

        
        State[][] dp = new State[5][n];
        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i < n; i++) {
                dp[k][i] = new State(0, new ArrayList<>());
            }
        }

        for (int k = 1; k <= 4; k++) {
            for (int i = 0; i < n; i++) {
                
                if (i > 0) {
                    dp[k][i] = dp[k][i - 1];
                }

                
                long val = arr[i][2];
                List<Integer> candidate = new ArrayList<>();
                if (prev[i] != -1) {
                    val += dp[k - 1][prev[i]].weight;
                    candidate.addAll(dp[k - 1][prev[i]].indices);
                }
                candidate.add(arr[i][3]);

                if (val > dp[k][i].weight) {
                    dp[k][i] = new State(val, candidate);
                } else if (val == dp[k][i].weight) {
                    
                    List<Integer> current = new ArrayList<>(dp[k][i].indices);
                    Collections.sort(candidate);
                    Collections.sort(current);
                    if (isLexSmaller(candidate, current)) {
                        dp[k][i] = new State(val, candidate);
                    }
                }
            }
        }

       
        State best = dp[4][n - 1];
        Collections.sort(best.indices);
        return best.indices.stream().mapToInt(x -> x).toArray();
    }

    private boolean isLexSmaller(List<Integer> a, List<Integer> b) {
        int m = Math.min(a.size(), b.size());
        for (int i = 0; i < m; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }
        return a.size() < b.size();
    }
}
