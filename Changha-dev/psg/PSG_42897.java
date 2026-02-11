/*
[풀이]
- 인접하지 않은 집
    - idx (1,2,3...n)   
    - 0, n-1은 주의
        - 0 : n-1, 2
        - n-1 : n-2, 0
    - 2~n-2는 양 옆
    -> a->b로 가는거 모든 경우하면 O(N)에 어긋나는데..
-> 선형으로 풀어서 case2개로 나누어서 생각

[output]
도둑이 훔칠 수 있는 돈의 최댓값 return
*/
public class PSG_42897 {
	static int[] money;
	public int solution(int[] money) {
		this.money = money;
		int answer = 0;
		int n = money.length;
		if(n == 1) return money[0];
		if(n == 2) return Math.max(money[0], money[1]);

		int case1 = dp(0, n-2);
		int case2 = dp(1, n-1);
		return Math.max(case1, case2);
	}

	static int dp(int s, int e){
		int prev2 = 0; // i번째 집을 턴 경우
		int prev1 = 0; // i번째 집을 안 턴 여우
		for(int i = s; i <= e; i++){
			int cur = Math.max(prev1, prev2+money[i]);
			prev2 = prev1;
			prev1 = cur;
		}
		return prev1;
	}
}