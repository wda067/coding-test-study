/*
[input]
- 행의 개수 N
- 열의 개수 4

[풀이]
- dp[i][cur][prev] = land[i][a] + dp[i-1][(max)]

[output]
얻을 수 있는 최대값

[느낀점]
dp 3차원으로 바로 생각해서 잘 푼 것 같다.
근데 a,b 이런식으로 해서 변수명을 cur, prev처럼 더 직관적으로 하면 좋을 것 같다.
*/
public class PSG_12913 {
	static int[][][] dp;
	int solution(int[][] land) {
		int answer = 0;
		int n = land.length;
		dp = new int[n][4][4]; // (i, a, b) i idx, a <- b

		if(n == 1){
			int tmp = 0;
			for(int i = 0; i < 4; i++){
				tmp = Math.max(tmp, land[0][i]);
			}
			return tmp;
		}

		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				dp[0][a][b] = land[0][a];
			}
		}

		for(int i = 1; i < n; i++){
			for(int a = 0; a < 4; a++){
				for(int b = 0; b < 4; b++){
					if(a == b) continue;
					int max = findMax(i-1, b);
					dp[i][a][b] = land[i][a] + max;
				}
			}
		}


		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				if(a == b) continue;
				answer = Math.max(answer, dp[n-1][a][b]);
			}
		}

		return answer;
	}

	static int findMax(int idx, int b){
		int tmp = 0;
		for(int i = 0; i < 4; i++){
			tmp = Math.max(tmp, dp[idx][b][i]);
		}
		return tmp;
	}
}
