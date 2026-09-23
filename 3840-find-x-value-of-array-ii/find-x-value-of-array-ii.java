

public class Solution {

    static class Node {
        int[] remain;
        int prod;

        public Node(int k) {
            this.remain = new int[k];
            this.prod = 1;
        }
    }

    private int n;
    private int k;
    private Node[] tree;

    private Node merge(Node leftChild, Node rightChild) {
        Node parent = new Node(k);
        parent.prod = (leftChild.prod * rightChild.prod) % k;

        
        for (int i = 0; i < k; i++) {
            parent.remain[i] += leftChild.remain[i];
        }

        
        for (int i = 0; i < k; i++) {
            int newRem = (leftChild.prod * i) % k;
            parent.remain[newRem] += rightChild.remain[i];
        }

        return parent;
    }

    private void build(int[] nums, int cur, int left, int right) {
        if (left == right) {
            tree[cur] = new Node(k);
            int val = nums[left] % k;
            tree[cur].remain[val] = 1;
            tree[cur].prod = val;
            return;
        }
        int mid = left + (right - left) / 2;
        build(nums, 2 * cur + 1, left, mid);
        build(nums, 2 * cur + 2, mid + 1, right);
        tree[cur] = merge(tree[2 * cur + 1], tree[2 * cur + 2]);
    }

    private void update(int cur, int left, int right, int idx, int val) {
        if (left == right) {
            tree[cur] = new Node(k);
            int modernVal = val % k;
            tree[cur].remain[modernVal] = 1;
            tree[cur].prod = modernVal;
            return;
        }
        int mid = left + (right - left) / 2;
        if (idx <= mid) {
            update(2 * cur + 1, left, mid, idx, val);
        } else {
            update(2 * cur + 2, mid + 1, right, idx, val);
        }
        tree[cur] = merge(tree[2 * cur + 1], tree[2 * cur + 2]);
    }

    private Node query(int cur, int left, int right, int ql, int qr) {
        if (ql <= left && right <= qr) {
            return tree[cur];
        }
        int mid = left + (right - left) / 2;
        if (qr <= mid) {
            return query(2 * cur + 1, left, mid, ql, qr);
        }
        if (ql > mid) {
            return query(2 * cur + 2, mid + 1, right, ql, qr);
        }
        Node leftResult = query(2 * cur + 1, left, mid, ql, mid);
        Node rightResult = query(2 * cur + 2, mid + 1, right, mid + 1, qr);
        return merge(leftResult, rightResult);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.tree = new Node[4 * n];

        
        build(nums, 0, 0, n - 1);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int xi = queries[i][3];

           
            update(0, 0, n - 1, idx, val);

            Node queryNode = query(0, 0, n - 1, start, n - 1);
            
            result[i] = queryNode.remain[xi];
        }

        return result;
    }
}
