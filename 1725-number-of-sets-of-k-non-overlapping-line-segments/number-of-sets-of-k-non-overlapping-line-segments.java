
class Solution {
    static final int MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {
        return (int) nCr(n + k - 1, 2 * k);
    }

    private long nCr(int n, int r) {
        if (r > n) return 0;
        long[] fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
        long numerator = fact[n];
        long denominator = (fact[r] * fact[n - r]) % MOD;
        return (numerator * modInverse(denominator)) % MOD;
    }

    private long modInverse(long x) {
        return pow(x, MOD - 2);
    }

    private long pow(long base, long exp) {
        long result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) result = (result * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }
}
