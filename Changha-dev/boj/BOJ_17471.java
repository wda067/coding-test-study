import java.io.*;
import java.util.*;

/*

[풀이]
- N(<=10)이 작으므로 모든 선거구 분할을 비트마스크로 완전탐색한다.
- mask를 A구역, (~mask)를 B구역으로 두고
  1) A가 연결 그래프인지 BFS로 확인
  2) B도 연결 그래프인지 BFS로 확인
  3) 둘 다 연결이면 인구수 차이 최소값 갱신
- 공집합/전체집합은 제외한다.
- 대칭( A/B 뒤집기 )은 같은 케이스라 mask를 절반까지만 보아도 된다.

[복잡도]
- 부분집합: O(2^N)
- 각 부분집합마다 BFS 2번: O(N+E)
- N<=10 이라 충분.
*/
public class BOJ_17471 {

	static int N;
	static int[] people;
	static ArrayList<Integer>[] adj;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine().trim());
		people = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			people[i] = Integer.parseInt(st.nextToken());
		}

		adj = new ArrayList[N];
		for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int cnt = Integer.parseInt(st.nextToken());
			for (int j = 0; j < cnt; j++) {
				int v = Integer.parseInt(st.nextToken()) - 1;
				adj[i].add(v);
			}
		}

		int allMask = (1 << N) - 1;
		int ans = Integer.MAX_VALUE;

		// 대칭 제거: A만 절반(1 ~ (1<<N)/2 - 1)만 보면 됨
		int limit = (1 << N) / 2;
		for (int mask = 1; mask < limit; mask++) {
			int other = allMask ^ mask;

			// 연결성 검사
			if (!isConnected(mask)) continue;
			if (!isConnected(other)) continue;

			int sumA = sum(mask);
			int sumB = sum(other);
			ans = Math.min(ans, Math.abs(sumA - sumB));
		}

		System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
	}

	/* mask에 포함된 구역 인구 합 */
	static int sum(int mask) {
		int s = 0;
		for (int i = 0; i < N; i++) {
			if ((mask & (1 << i)) != 0) s += people[i];
		}
		return s;
	}

	/*
	mask에 포함된 정점들이 "서로 연결"인지 BFS로 확인
	- start: mask 안에 있는 아무 정점
	- BFS는 mask 내부로만 이동
	- 방문 수 == mask 크기면 연결
	*/
	static boolean isConnected(int mask) {
		int start = -1;
		int need = 0;
		for (int i = 0; i < N; i++) {
			if ((mask & (1 << i)) != 0) {
				need++;
				if (start == -1) start = i;
			}
		}
		if (need == 0) return false;

		boolean[] visited = new boolean[N];
		ArrayDeque<Integer> q = new ArrayDeque<>();
		visited[start] = true;
		q.add(start);

		int cnt = 1;
		while (!q.isEmpty()) {
			int cur = q.poll();
			for (int nxt : adj[cur]) {
				if (visited[nxt]) continue;
				if ((mask & (1 << nxt)) == 0) continue; // 같은 그룹만
				visited[nxt] = true;
				q.add(nxt);
				cnt++;
			}
		}
		return cnt == need;
	}
}