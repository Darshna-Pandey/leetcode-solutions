class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int cheapest = Integer.MAX_VALUE;
        int maxFactor = 0;

        
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            cheapest = Math.min(cheapest, item[1]);
        }

    
        int[] freq = new int[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

       
        for (int i = 1; i <= maxFactor; i++) {
            if (freq[i] > 0) {
                for (int multiple = 2 * i; multiple <= maxFactor; multiple += i) {
                    freq[i] += freq[multiple];
                }
            }
        }

        
        TreeMap<Long, Long> costCount = new TreeMap<>();
        for (int[] item : items) {
            long cost = item[1];
            
            long validTargets = freq[item[0]] - 1; 

            if (validTargets > 0 && cost <= 2L * cheapest) {
                costCount.put(cost, costCount.getOrDefault(cost, 0L) + validTargets);
            }
        }
        long totalItems = 0;
        long remBudget = budget;

        for (Map.Entry<Long, Long> entry : costCount.entrySet()) {
            long cost = entry.getKey();
            long count = entry.getValue();

            long canBuy = Math.min(count, remBudget / cost);
            totalItems += 2 * canBuy;
            remBudget -= canBuy * cost;

            if (remBudget < cost) {
                break;
            }
        }

        
        totalItems += remBudget / cheapest;

        return (int) totalItems;
    }
}
       