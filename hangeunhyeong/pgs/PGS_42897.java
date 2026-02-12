package hangeunhyeong.pgs;
/*
PGS 42897 - 도둑질
n이 최대 100만이므로 완전탐색 X
그리디도 아님
DP : dp[i] 번째 집을 털었을때(dp[i - 2] + money[i])와 털지 않았을때(dp[i - 1])의 값을 비교하여 그중 큰 값을 기록
 */
public class PGS_42897 {
    public int solution(int[] money) {
        int n = money.length;
        int[] include = new int[n];
        int[] exclude = new int[n];
        include[0] = money[0];
        for(int i = 1; i < n - 1; i++){
            if(i - 2 >= 0){
                include[i] = Math.max(money[i] + include[i - 2], include[i - 1]);
                exclude[i] = Math.max(money[i] + exclude[i - 2], exclude[i - 1]);
            }
            else{
                include[i] = include[0];
                exclude[i] = money[i];
            }
        }
        include[n - 1] = include[n - 2];
        exclude[n - 1] = Math.max(money[n - 1] + exclude[n - 3], exclude[n - 2]);
        int answer = Math.max(include[n - 1], exclude[n - 1]);
        return answer;
    }
}