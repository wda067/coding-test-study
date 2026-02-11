/*
프로그래머스 / 도둑질 / Level 4
https://school.programmers.co.kr/learn/courses/30/lessons/42897
 */
class PGS_42897 {

    public int solution(int[] money) {
        int n = money.length;

        // dp[0][i]: 0번 집을 포함하는 케이스 -> i: 0..n-2 (마지막 집 제외)
        // dp[1][i]: 0번 집을 제외하는 케이스 -> i: 1..n-1
        // dp[i][j]: j번째 집까지의 최댓값
        int[][] dp = new int[2][n];

        // 0번 집 포함 케이스
        dp[0][0] = money[0];
        dp[0][1] = Math.max(money[0], money[1]);
        for (int i = 2; i <= n - 2; i++) { // 마지막 집(n-1)은 제외
            // dp[0][i] = max(i번째 안 턴 경우, i번째 턴 경우)
            dp[0][i] = Math.max(dp[0][i - 1], dp[0][i - 2] + money[i]);
        }

        // 0번 집 제외 케이스
        dp[1][0] = 0;
        dp[1][1] = money[1];
        for (int i = 2; i <= n - 1; i++) {
            dp[1][i] = Math.max(dp[1][i - 1], dp[1][i - 2] + money[i]);
        }

        return Math.max(dp[0][n - 2], dp[1][n - 1]);
    }
}
