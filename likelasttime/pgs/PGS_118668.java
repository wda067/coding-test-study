class Solution {
    /*
    	0 <= 초기 알고력 alp <= 150
        0 <= 초기 코딩력 cop <= 150
        problems의 원소는 [alp_req, cop_req, alp_rwd, cop_rwd, cost]로 이루어짐
        alp_req: 문제를 푸는데 필요한 알고력
        cop_req: 문제를 푸는데 필요한 코딩력
        alp_rwd: 문제를 풀었을 때 증가하는 알고력
        cop_rwd: 문제를 풀었을 때 증가하는 코딩력
        cost: 문제를 푸는데 드는 시간

        시간 복잡도: O(NMK)
        공간 복잡도: O(NM)
    */
    public int PGS_118668(int alp, int cop, int[][] problems) {
        int maxAlp = 0;		// 필요한 알고력
        int maxCop = 0;		// 필요한 코딩력
        int[][] dp;			// dp[알고력][코딩력] = 최소 시간

        // 필요한 알고력, 코딩력 구하기
        for(int[] problem : problems) {			// 문제
            maxAlp = Math.max(maxAlp, problem[0]);
            maxCop = Math.max(maxCop, problem[1]);
        }

        if(alp >= maxAlp && cop >= maxCop) {		// 필요한 알고력, 코딩력을 갖춤
            return 0;		// 드는 시간이 없음
        }

        // 초기 알고력/코딩력이 목표로 하는 알고력/코딩력보다 클 경우
        alp = Math.min(alp, maxAlp);
        cop = Math.min(cop, maxCop);

        // DP 초기화 (굳이 0부터 초기화할 필요없음)
        dp = new int[maxAlp + 2][maxCop + 2];	// maxAlp, maxCop를 포함해야하니까 +1이고, 알고력/코딩력을 1 올리는데 1 시간이 드니까 +2를 함
        for(int i=alp; i<=maxAlp; i++) {		// 알고력
            for(int j=cop; j<=maxCop; j++) {	// 코딩력
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        dp[alp][cop] = 0;		// 현재 알고력, 코딩력에서는 시간이 0

        for(int i=alp; i<=maxAlp; i++) {		// 알고력
            for(int j=cop; j<=maxCop; j++) {	// 코딩력
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);		// 알고력 증가
                dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);		// 코딩력 증가
            }
        }

        for(int i=alp; i<=maxAlp; i++) {		// 알고력
            for(int j=cop; j<=maxCop; j++) {	// 코딩력
                for(int[] problem : problems) {		// 문제
                    int alpReq = problem[0];		// 필요한 알고력
                    int copReq = problem[1];		// 필요한 코딩력
                    int alpRwd = problem[2];		// 증가하는 알고력
                    int copRwd = problem[3];		// 증가하는 코딩력
                    int cost = problem[4];			// 드는 시간

                    if(i >= alpReq && j >= copReq) {		// 필요한 알고력, 코딩력보다 그 이상이라면
                        if(i + alpRwd >= maxAlp && j + copRwd >= maxCop) {	// 이 과목을 수강한 후 필요한 알고력과 코딩력이 크거나 같다면
                            dp[maxAlp][maxCop] = Math.min(dp[maxAlp][maxCop], dp[i][j] + cost);
                        } else if(i + alpRwd >= maxAlp) {		// 필요한 알고력보다 크거나 같다면
                            dp[maxAlp][j + copRwd] = Math.min(dp[maxAlp][j + copRwd], dp[i][j] + cost);
                        } else if(j + copRwd >= maxCop) {		// 필요한 코딩력보다 크거나 같다면
                            dp[i + alpRwd][maxCop] = Math.min(dp[i + alpRwd][maxCop], dp[i][j] + cost);
                        } else {	// 필요한 알고력, 코딩력보다 작다면
                            dp[i + alpRwd][j + copRwd] = Math.min(dp[i + alpRwd][j + copRwd], dp[i][j] + cost);
                        }
                    }
                }
            }
        }
        return dp[maxAlp][maxCop];		// 모든 문제를 풀 수 있는 알고력과 코딩력을 얻는 최단시간
    }
}