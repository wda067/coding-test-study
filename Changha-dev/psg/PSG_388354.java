/*
mismatch가 1이면 홀짝 가능인 이유 
deg(v) = v의 연결 개수 
p(v) = v % 2 = 노드 번호 홀짝
qParity(v) = (deg(v) - 1) % 2 = v가 루트가 아닐때 자식 수 홀짝
- 루트 아닌 v는 p(v) == qParity(v)
- 루트 r 은 qParity(r) != p(r)
*/
import java.util.*;

public class PSG_388354 {
	public int[] solution(int[] nodes, int[][] edges) {
		// 1) 좌표 압축: 노드 번호가 커도 인접리스트를 작게 만들기
		Map<Integer, Integer> id = new HashMap<>(nodes.length * 2);
		int[] valByIdx = new int[nodes.length];
		int n = 0;
		for (int v : nodes) {
			if (!id.containsKey(v)) {
				id.put(v, n);
				valByIdx[n] = v;
				n++;
			}
		}

		// 2) 인접리스트 구성 (포레스트/트리니까 무방향)
		ArrayList<Integer>[] g = new ArrayList[n];
		for (int i = 0; i < n; i++) g[i] = new ArrayList<>();

		for (int[] e : edges) {
			Integer a = id.get(e[0]);
			Integer b = id.get(e[1]);
			if (a == null || b == null) continue; // 혹시 nodes에 없는 값이 들어오면 방어
			g[a].add(b);
			g[b].add(a);
		}

		// 3) 컴포넌트(트리)별로 mismatch 계산
		boolean[] visited = new boolean[n];
		int holJjak = 0;
		int revHolJjak = 0;

		ArrayDeque<Integer> q = new ArrayDeque<>();

		for (int start = 0; start < n; start++) {
			if (visited[start]) continue;

			visited[start] = true;
			q.add(start);

			int compSize = 0;
			int mismatch = 0;
			// mismatch: "v가 비루트라고 가정했을 때" 홀짝 조건(번호홀짝 == 자식수홀짝)을 깨는 노드 수

			while (!q.isEmpty()) {
				int vIdx = q.poll();
				compSize++;

				int deg = g[vIdx].size();          // 무방향 degree
				int p = valByIdx[vIdx] & 1;        // 노드 번호 홀짝 (짝=0, 홀=1)

				// 비루트일 때 자식 수 = deg - 1
				// parity만 필요하므로 (deg - 1) % 2 == (deg + 1) % 2 (mod 2)
				int nonRootChildParity = (deg + 1) & 1;

				// 홀짝 트리의 비루트 조건은: nonRootChildParity == p
				if (nonRootChildParity != p) mismatch++;

				for (int nb : g[vIdx]) {
					if (!visited[nb]) {
						visited[nb] = true;
						q.add(nb);
					}
				}
			}

			// 홀짝 트리 가능 ⇔ 비루트 조건을 깨는 노드가 정확히 1개 (그 1개를 루트로 잡으면 뒤집혀서 전부 만족)
			if (mismatch == 1) holJjak++;

			// 역홀짝 트리 가능 ⇔ 비루트 조건(홀짝) "만족" 노드가 정확히 1개 (그 1개를 루트로 잡으면 뒤집혀서 전부 불일치)
			// 만족 노드 수 = compSize - mismatch
			if (compSize - mismatch == 1) revHolJjak++;
		}

		return new int[] { holJjak, revHolJjak };
	}
}