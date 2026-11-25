class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE1 = 131_542_391L;
    private static final long BASE2 = 265_443_576L;

    private long[] h1, h2, p1, p2;
    private int n;

    public int smallestUniqueSubarray(int[] nums) {
        n = nums.length;
        h1 = new long[n + 1];
        h2 = new long[n + 1];
        p1 = new long[n + 1];
        p2 = new long[n + 1];

        p1[0] = 1;
        p2[0] = 1;

        for (int i = 0; i < n; i++) {
            p1[i + 1] = (p1[i] * BASE1) % MOD1;
            p2[i + 1] = (p2[i] * BASE2) % MOD2;
            h1[i + 1] = (h1[i] * BASE1 + nums[i]) % MOD1;
            h2[i + 1] = (h2[i] * BASE2 + nums[i]) % MOD2;
        }

        int low = 1, high = n;
        int ans = n;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUnique(mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUnique(int len) {
        int count = n - len + 1;
        long[] hashes = new long[count];

        for (int i = 0; i < count; i++) {
            long hash1 = (h1[i + len] - (h1[i] * p1[len]) % MOD1 + MOD1) % MOD1;
            long hash2 = (h2[i + len] - (h2[i] * p2[len]) % MOD2 + MOD2) % MOD2;
            
            hashes[i] = (hash1 << 32) | hash2;
        }

        Arrays.sort(hashes);

        for (int i = 0; i < count; i++) {
            boolean sameAsPrev = (i > 0 && hashes[i] == hashes[i - 1]);
            boolean sameAsNext = (i < count - 1 && hashes[i] == hashes[i + 1]);
            if (!sameAsPrev && !sameAsNext) {
                return true;
            }
        }

        return false;
    }
}