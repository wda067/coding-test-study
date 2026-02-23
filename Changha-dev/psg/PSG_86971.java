/*
[input]
n : 송전탑 개수
wires : [v1,v2]

[풀이]
- 최대한 비슷하게 -> 2 / n
- 하나의 송전탑에 주변 ArrayList로 담기

- cut method
    - 전선 자르고 cnt
- 사전 세팅
    - 각 송전탑에서 이웃하는 송전탑을 추가
*/
import java.util.*;
public class PSG_86971 {
	static ArrayList<Integer>[] lists;
	static int n;
	public int solution(int n, int[][] wires) {
		int answer = Integer.MAX_VALUE;
		this.n = n;
		lists = new ArrayList[n+1];
		for(int i = 1; i <= n; i++) lists[i] = new ArrayList<>();

		for(int[] wire : wires){
			int a = wire[0]; int b = wire[1];
			lists[a].add(b);
			lists[b].add(a);
		}

		for(int[] wire : wires){
			int a = wire[0]; int b = wire[1];

			int cnt = cutAndrestore(a, b);
			int diff = Math.abs(n - 2*cnt);
			answer = Math.min(answer, diff);

			if(answer == 0) break;
		}
		return answer;
	}
	static int search(int start, boolean[] visited) {
		Queue<Integer> q = new LinkedList<>();
		visited[start] = true;
		q.add(start);

		int cnt = 1;
		while (!q.isEmpty()) {
			int cur = q.poll();
			for (int next : lists[cur]) {
				if (visited[next]) continue;
				visited[next] = true;
				cnt+=1;
				q.add(next);
			}
		}
		return cnt;
	}

	static int cutAndrestore(int a, int b) {
		lists[a].remove(Integer.valueOf(b));
		lists[b].remove(Integer.valueOf(a));

		boolean[] visited = new boolean[n + 1];
		int cnt = search(a, visited);

		lists[a].add(b);
		lists[b].add(a);
		return cnt;
	}
}