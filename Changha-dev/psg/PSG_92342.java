/*
[풀이]
- 라이언 vs 어피치
     - a = b 일 경우 어피치가 k점을 가져감
- n, 0~10 배열 info
     - 가장 큰 점수 차이로 우승하기 위해 n 발의 화살

1. 고려해야 될 우선순위가 무엇인가..
    - 과녁점수 순? x
        - 특정 규칙을 찾기에는 어려워 보임
    - 완탐으로 해서 가장 큰경우를 찾아야되나?
        - 가장 큰 점수차가 여러개일 경우
            - 가장 낮은 점수가 많은 경우를 return -> sort 하면 될듯?
            - HashMap<Integer, ArrayList<>> 로 해야되나

[output]
- 라이언이 우승해야 함
    - 가장 큰 점수 차이로 우승하기
- 우승할 수 없는 경우
    - -1

*/
import java.util.*;
public class PSG_92342 {
	static int[] arr = new int[11];
	static int[] info;
	static int oppoSum;
	static int n;
	static ArrayList<int[]> list = new ArrayList<>();
	static HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();
	public int[] solution(int n, int[] info) {
		this.info = info; this.n = n;

		backtracking(0, 0);

		if (map.isEmpty()) return new int[]{-1};

		int bestDiff = Collections.max(map.keySet());
		ArrayList<int[]> candidates = map.get(bestDiff);

		int[] best = candidates.get(0);
		for(int i = 1; i < candidates.size(); i++){
			int[] cur = candidates.get(i);
			if(better(cur,best)) best = cur;
		}
		return best;
	}

	static void backtracking(int dep, int cnt){
		if(dep == 11) {
			int remain = n - cnt;
			if(remain < 0) return;

			arr[10] += remain;

			int diff = cal();
			if(diff > 0) {
				map.computeIfAbsent(diff, k -> new ArrayList<>())
					.add(arr.clone());
			}

			arr[10] -= remain;
			return;
		}

		arr[dep] = 0;
		backtracking(dep+1, cnt);

		int need = info[dep]+1;
		if(cnt+need <= n){
			arr[dep] = need;
			backtracking(dep+1, cnt + need);
			arr[dep] = 0;
		}
	}

	static int cal(){
		int me = 0; int oppo = 0;
		for(int i = 0; i < 11; i++){
			int score = 10 - i;

			if(arr[i] == 0 && info[i] == 0) continue;

			if(arr[i] > info[i]) me += score;
			else oppo += score;
		}
		return me - oppo;
	}

	static boolean better(int[] a, int[] b){
		for(int i = 10; i >= 0; i--){
			if(a[i] != b[i]) return a[i] > b[i];
		}
		return false;
	}
}
