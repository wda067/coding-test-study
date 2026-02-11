class Solution {
    public int PGS_42897(int[] money) {
        int n = money.length;   // 집의 갯수

        if (n == 1) return money[0];        // 집이 1채라면

        // 첫 집 포함, 마지막 집 제외
        int[] dp1 = new int[n];
        dp1[0] = money[0];
        dp1[1] = money[0];

        for (int i = 2; i < n - 1; i++) {
            // i - 1번째 집을 털거나 i - 2번째 집을 털고 i번째 집을 턴다
            dp1[i] = Math.max(dp1[i - 1], dp1[i - 2] + money[i]);
        }

        // 첫 집 제외, 마지막 집 포함 가능
        int[] dp2 = new int[n];
        dp2[0] = 0;
        dp2[1] = money[1];

        for (int i = 2; i < n; i++) {
            // i - 1번째 집을 털거나 i - 2번째 집을 털고 i번째 집을 턴다
            dp2[i] = Math.max(dp2[i - 1], dp2[i - 2] + money[i]);
        }

        return Math.max(dp1[n - 2], dp2[n - 1]);
    }
}
