package hangeunhyeong.pgs;
/*
PGS 12913 - 땅따먹기
[문풀 방향잡기]
- 완전탐색 : 시간복잡도 O(4^10만) => X
- greedy : 탐욕해 != 최적해 => X
- dp : YES! 땅을 밟았을 때의 최댓값을 갱신하는 방식으로 진행
[문제풀이]
- 0번째 행은 그대로 가져온다
- 해당 땅을 밟았을 때의 최댓값 = 바로 윗행에서의 최댓값 + 땅을 밟았을 때 획득하는 점수
- 단, 바로 아래칸은 밟지 못하므로 해당 땅을 밟았을 떄의 최댓값 = 바로 윗행에서의 두번째로 큰 점수 + 땅을 밟았을 때 획득하는 점수
 */
public class PGS_12913 {
    int solution(int[][] land) {
        int answer = 0;
        int N = land.length;
        int[][] dp = new int[N][4];
        dp[0] = land[0];
        int max = 0, maxIdx = 0;
        int second = 0;
        for(int i = 0; i < N; i++){
            max = 0;
            maxIdx = 0;
            second = 0;
            // 행마다 최댓값과 두번째로 큰 값 구하기
            for(int j = 0; j < 4; j++){
                // 현재위치가 최댓값일 때
                if(max < dp[i][j]){
                    second = max;   // second값 갱신
                    max = dp[i][j];
                    maxIdx = j;
                }
                // 현재위치가 최댓값은 아니지만 second보다 클 때
                else if(second < dp[i][j]){
                    second = dp[i][j];
                }
            }

            // System.out.println(max + " " + maxIdx + " " + second + " " + secondIdx);
            for(int j = 0; i + 1 < N && j < 4; j++){
                if(maxIdx != j)
                    dp[i + 1][j] = max + land[i + 1][j];
                else
                    dp[i + 1][j] = second + land[i + 1][j];
            }
        }
        // printDp(dp);

        return max;
    }
    public void printDp(int[][] dp){
        for(int i = 0; i < dp.length; i++){
            for(int j = 0; j < dp[0].length; j++){
                System.out.printf("%d ", dp[i][j]);
            }
            System.out.println("");
        }
    }
}