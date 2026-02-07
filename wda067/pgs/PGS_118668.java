import java.util.Arrays;

/*
프로그래머스 / 코딩 테스트 공부 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/118668
 */
class PGS_118668 {

    public int solution(int alp, int cop, int[][] problems) {
        // 목표 설정
        int maxAlp = 0, maxCop = 0;
        for (int[] p : problems) {
            maxAlp = Math.max(maxAlp, p[0]);
            maxCop = Math.max(maxCop, p[1]);
        }

        // 초기값이 목표보다 크면 잘라낸다.
        if (alp > maxAlp) {
            alp = maxAlp;
        }
        if (cop > maxCop) {
            cop = maxCop;
        }

        // dp[i][j]: (alp=i, cop=j) 상태까지 도달하는 데 걸린 최소 시간
        // +2 -> 아래 반복문에서 i = maxAlp + 1 접근 보호
        int[][] dp = new int[maxAlp + 2][maxCop + 2];

        for (int[] d : dp) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }

        dp[alp][cop] = 0;  // 초기 상태는 시간 0

        for (int i = alp; i <= maxAlp; i++) {
            for (int j = cop; j <= maxCop; j++) {
                // 공부만 해서 올리는 경우
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);  // 알고력
                dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);  // 코딩력

                for (int[] p : problems) {
                    int reqAlp = p[0], reqCop = p[1];
                    int rwdAlp = p[2], rwdCop = p[3], cost = p[4];

                    // 문제를 풀 수 있는 경우
                    if (i >= reqAlp && j >= reqCop) {
                        // 문제를 풀면 능력치 증가
                        // 목표보다 커질 필요는 없으므로 제한
                        int nextAlp = Math.min(maxAlp, i + rwdAlp);
                        int nextCop = Math.min(maxCop, j + rwdCop);

                        // 현재 상태에서 cost만큼 추가 후 갱신
                        dp[nextAlp][nextCop] = Math.min(dp[nextAlp][nextCop], dp[i][j] + cost);
                    }
                }
            }
        }

        return dp[maxAlp][maxCop];
    }
}
