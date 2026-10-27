class Solution {
    public int getMinDistance(int[] nums, int target, int start) {
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                st.push(i);
            }
        }

        int minDist = Integer.MAX_VALUE;

        while (!st.isEmpty()) {
            minDist = Math.min(minDist,
                               Math.abs(st.pop() - start));
        }

        return minDist;
    }
}               
    