package hangeunhyeong.pgs;
/*
PGS 118668 - 코딩 테스트 공부
처음에는 완전탐색으로 해결할 생각을 했으나 시간복잡도 때문에 다른 방법을 생각해내야했었다.
도무지 방법이 생각나지 않아 인터넷 검색 후 DP로 푼다는것을 알게됐었다.

dp[alp][cop] : 목표알고력이 alp, 목표코딩력이 cop 일 때 걸리는 최단시간
알고력과 코딩력을 높이는 방법은 총 세가지이다
- 알고력을 공부(시간 1)
- 코딩력을 공부(시간 1)
- 문제를 풀기(시간 problems[*][4])
주어진 alp, cop에서 시작해서 3가지 방법을 수행해나가며 최솟값을 비교하면 목표알고력, 코딩력에 걸리는 최단시간을 알 수 있다.

1. 목표 알고력, 목표 코딩력 알아내기
2. 현재 알고력, 코딩력이 목표 알고력, 코딩력보다 크다면 예외처리
3. 최단시간을 구해야하므로 dp배열을 큰 정수로 초기화
4. 코딩력 공부, 알고력 공부, 문제풀기 이후의 알고력, 코딩력에 해당하는 칸에 해당값과 비교를 하며 기록해나간다
    - 알고력과 코딩력을 높인 이후의 alp와 cop가 배열 범위를 초과하는 경우에 대한 예외처리도 필수
5. dp[maxAlp][maxCop] = 목표알고력, 코딩력에 도달하기 위한 최단시간이므로 해당 값을 반환한다.
=> 시간복잡도 : O(maxAlp * maxCop * 문제의 수)
 */
import java.util.*;
public class PGS_118668 {
    public int solution(int alp, int cop, int[][] problems) {
        int maxAlp = 0, maxCop = 0;
        // 목표 알고력, 목표 코딩력 알아내기
        for(int[] problem : problems){
            if(problem[0] > maxAlp)
                maxAlp = problem[0];
            if(problem[1] > maxCop)
                maxCop = problem[1];
        }
        maxAlp = Math.max(alp, maxAlp);
        maxCop = Math.max(cop, maxCop);
        // 예외 처리
        if(maxAlp == alp && maxCop == cop)
            return 0;

        int INF = 1000_000_000;
        int[][] dp = new int[maxAlp + 1][maxCop + 1];
        // dp배열의 값을 모두 INF로 초기화
        for(int i = 0; i < dp.length; i++)
            Arrays.fill(dp, INF);

        // 현재 alp, cop에 도달하기 위한 최단시간은 0이므로 0으로 초기화
        dp[alp][cop] = 0;
        for(int i = alp; i <= maxAlp; i++){
            for(int j = cop; j <= maxCop; j++){
                // 코딩력 공부
                if(j + 1 <= maxCop)
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);
                // 알고력 공부
                if(i + 1 <= maxAlp)
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                // 문제를 풀기 시도
                for(int[] problem : problems){
                    // 문제를 풀 수 있는 경우
                    if(i >= problem[0] && j >= problem[1] ){
                        // 문제를 풀고 난 이후 알고력, 코딩력이 목표알고력, 코딩력보다 큰 경우 예외처리
                        int afterAlp = Math.min(maxAlp, i + problem[2]);
                        int afterCop = Math.min(maxCop, j + problem[3]);
                        dp[afterAlp][afterCop] = Math.min(dp[afterAlp][afterCop], dp[i][j] + problem[4]);
                    }
                }
            }
        }
        return dp[maxAlp][maxCop];
    }
}