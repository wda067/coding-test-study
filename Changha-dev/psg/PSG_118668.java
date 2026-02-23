/*
[풀이]
- 알고, 코딩력 높이기 
    - 각각 1의 시간 투자해서 1 증가 
    - 문제 풀고 reward로 증가
    - 목표는 maxAlp, maxCop이상이어야 됨
        - while문으로? 하기에는 복잡
        - dp문 활용해서 [i][j] 일때의 최소 시간
- 모든 문제를 풀 수 있는 알고력과 코딩력
    - 둘 다 최대 이상


*/
import java.util.*;
public class PSG_118668 {
	public int solution(int alp, int cop, int[][] problems) {
		int answer = 0;
		int maxAlp = 0; int maxCop = 0;
		for(int[] problem : problems){
			maxAlp = Math.max(problem[0], maxAlp);
			maxCop = Math.max(problem[1], maxCop);
		}

		alp = Math.min(alp, maxAlp);
		cop = Math.min(cop, maxCop);

		int[][] dp = new int[maxAlp + 2][maxCop + 2];
		for(int i = 0; i <= maxAlp; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
		dp[alp][cop] = 0;

		for(int i = alp; i <= maxAlp; i++){
			for(int j = cop; j <= maxCop; j++){
				int cur = dp[i][j];
				if(cur == Integer.MAX_VALUE) continue;

				dp[i+1][j] = Math.min(cur+1, dp[i+1][j]);
				dp[i][j+1] = Math.min(cur+1, dp[i][j+1]);

				for(int[] p : problems){
					int reqA = p[0]; int reqC = p[1];
					int rwdA = p[2]; int rwdC = p[3];
					int cost = p[4];

					if(i >= reqA && j >= reqC){
						int ni = Math.min(maxAlp, i+rwdA);
						int nj = Math.min(maxCop, j+rwdC);
						dp[ni][nj] = Math.min(dp[ni][nj], cur+cost);
					}
				}
			}
		}

		return dp[maxAlp][maxCop];
	}
}