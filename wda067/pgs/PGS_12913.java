/*
프로그래머스 / 땅따먹기 / Level 2
https://school.programmers.co.kr/learn/courses/30/lessons/12913
 */
class PGS_12913 {

    int solution(int[][] land) {
        int N = land.length;
        // dp[i][j]: (i, j)일 때 최고점
        int[][] dp = new int[N][4];
        dp[0] = land[0];

        for (int i = 1; i < N; i++) {
            int[] prev = dp[i - 1];

            dp[i][0] = land[i][0] + Math.max(Math.max(prev[1], prev[2]), prev[3]);
            dp[i][1] = land[i][1] + Math.max(Math.max(prev[0], prev[2]), prev[3]);
            dp[i][2] = land[i][2] + Math.max(Math.max(prev[0], prev[1]), prev[3]);
            dp[i][3] = land[i][3] + Math.max(Math.max(prev[0], prev[1]), prev[2]);
        }

        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < dp[N - 1][i])
                max = dp[N - 1][i];
        }
        return max;
    }
}
