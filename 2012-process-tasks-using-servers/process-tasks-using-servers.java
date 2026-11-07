class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = tasks.length;
        int[] ans = new int[n];

        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) return a[0] - b[0];
                return a[1] - b[1];
            }
        );

      
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) return Long.compare(a[0], b[0]);
                if (a[1] != b[1]) return Long.compare(a[1], b[1]);
                return Long.compare(a[2], b[2]);
            }
        );

        for (int i = 0; i < servers.length; i++) {
            available.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int i = 0; i < n; i++) {
            time = Math.max(time, i);

            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                long[] server = busy.poll();
                available.offer(new int[]{
                    (int) server[1],
                    (int) server[2]
                });
            }

            if (available.isEmpty()) {
                time = busy.peek()[0];

                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    long[] server = busy.poll();
                    available.offer(new int[]{
                        (int) server[1],
                        (int) server[2]
                    });
                }
            }

            int[] server = available.poll();
            ans[i] = server[1];

            busy.offer(new long[]{
                time + tasks[i],
                server[0],
                server[1]
            });
        }

        return ans;
    }
}        