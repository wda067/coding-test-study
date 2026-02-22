/*
    땅은 총 N행 4열
    1행부터 땅을 밟으며 한 행씩 내려온다.
    각 행의 4칸 중 한 칸만 밟으면서 내려온다.
    같은 열을 연속해서 밟을 수 없다.
    마지막 행까지 모두 내려왔을 때 얻을 수 있는 점수의 최대값을 반환
*/
class Solution {
    int solution(int[][] land) {
        int[] dp = land[0].clone();

        for (int i = 1; i < land.length; i++) {
            int[] next = new int[4];
            for (int j = 0; j < 4; j++) {
                int maxPrev = 0;
                for (int k = 0; k < 4; k++) {
                    if (k == j) continue;
                    maxPrev = Math.max(maxPrev, dp[k]);
                }
                next[j] = land[i][j] + maxPrev;
            }
            dp = next;
        }

        int answer = 0;
        for (int v : dp) answer = Math.max(answer, v);
        return answer;
    }
}
