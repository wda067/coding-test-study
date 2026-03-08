import java.util.*;
import java.lang.*;
import java.io.*;

/*
최소 1개, 최대 P개를 반전
int[][] -> 0에서 9까지 미리 세팅

n : 최대 층
k : k 자리 디스플레이
p : 최소 1개 ~ 최대 p개 반전
x : 현재 x층에 멈춰있음

특정 경우 고려 -> 그거 유지한채로 다른것도 해야 하므로 dfs?
dfs(curIdx, remainP)


생각 못 한 점
- a -> b 로 바꿀 때 필요한 개수도 알아야 함 : cost[a][b]
- x를 k자리로 만들어야 함

[출력]
X층에 멈춰있을 때, 반전시킬 LED를 고를 수 있는 경우의 수
*/
public class BOJ_22251 {

	static int[][] numbers = {{1,1,1,0,1,1,1}, // 0
		{0,0,1,0,0,1,0}, // 1
		{1,0,1,1,1,0,1}, // 2
		{1,0,1,1,0,1,1}, // 3
		{0,1,1,1,0,1,0}, // 4
		{1,1,0,1,0,1,1}, // 5
		{1,1,0,1,1,1,1}, // 6
		{1,0,1,0,0,1,0}, // 7
		{1,1,1,1,1,1,1}, // 8
		{1,1,1,1,0,1,1}}; // 9
	static int[][] cost = new int[10][10];
	static int[] xDigits;
	static int n, k, p, x;
	static int ans = 0;
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		p = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());

		costSetting();
		digitsSetting();

		dfs(0, 0, 0);

		System.out.println(ans);
	}

	static void dfs(int idx, int used, int value){
		if (used > p) return;

		if (idx == k) {
			if (value >= 1 && value <= n && value != x) {
				ans++;
			}
			return;
		}
		int curDigit = xDigits[idx]; // 현재 자리의 원래 숫자

		for(int d = 0; d <= 9; d++){
			int add = cost[curDigit][d];
			if (used + add > p) continue;

			dfs(idx + 1, used+add, value * 10 + d);
		}
	}

	static void digitsSetting(){
		xDigits = new int[k];
		int tmp = x;
		for(int i = k - 1; i >= 0; i--){
			xDigits[i] = tmp % 10;
			tmp /= 10;
		}
	}

	static void costSetting() {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				int cnt = 0;
				for (int s = 0; s < 7; s++) {
					if (numbers[i][s] != numbers[j][s]) cnt++;
				}
				cost[i][j] = cnt;
			}
		}
	}
}
