/*
[풀이]
- 시작 지점 저장, 끝지점 저장
- 레버로 먼저 간다.
- bfs 로 O 인곳 이동
    - E를 봐도 L을 들리지 않았으면 false 
O에서 L 로 최소 시간 
+
L에서 E 로 최소 시간

- 만약 둘 중 하나가 불가하면 -1

S : 시작지점
E : 출구
L : 레버
O : 통로
X : 벽

[출력]
- 미로를 탈출하는데 필요한 최소 시간 
    - 불가능 -> -1 return

*/
import java.util.*;
public class PSG_159993 {
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int n, m;
	static char[][] map;

	public int solution(String[] maps) {
		n = maps.length;
		m = maps[0].length();
		map = new char[n][m];

		int[] start = null;
		int[] lever = null;

		// 맵 파싱 + S/L 위치 찾기
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				map[i][j] = maps[i].charAt(j);
				if (map[i][j] == 'S') start = new int[]{i, j};
				else if (map[i][j] == 'L') lever = new int[]{i, j};
			}
		}

		int dist1 = bfs(start[0], start[1], 'L'); // S -> L
		if (dist1 == -1) return -1;

		int dist2 = bfs(lever[0], lever[1], 'E'); // L -> E
		if (dist2 == -1) return -1;

		return dist1 + dist2;
	}

	static int bfs(int sx, int sy, char target) {
		Queue<int[]> q = new LinkedList<>();
		int[][] dist = new int[n][m];
		for (int i = 0; i < n; i++) Arrays.fill(dist[i], -1);

		dist[sx][sy] = 0;
		q.add(new int[]{sx, sy});

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int x = cur[0], y = cur[1];

			if (map[x][y] == target) return dist[x][y];

			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];

				if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue; // 범위
				if (map[nx][ny] == 'X') continue;                      // 벽
				if (dist[nx][ny] != -1) continue;                      // 방문

				dist[nx][ny] = dist[x][y] + 1;
				q.add(new int[]{nx, ny});
			}
		}

		return -1; // target까지 못 감
	}
}