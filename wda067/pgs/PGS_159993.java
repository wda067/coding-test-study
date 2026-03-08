import java.util.LinkedList;
import java.util.Queue;

/*
프로그래머스 / 미로 탈출 / Level 2
https://school.programmers.co.kr/learn/courses/30/lessons/159993
 */
class PGS_159993 {

    private int[] dr = {-1, 0, 1, 0};
    private int[] dc = {0, 1, 0, -1};

    private char[][] map;
    private int N, M;
    private int[] start, lever, exit;
    private int[][] dist;

    public int solution(String[] maps) {
        N = maps.length;
        M = maps[0].length();

        map = new char[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char cur = maps[i].charAt(j);
                map[i][j] = cur;

                if (cur == 'S') {
                    start = new int[]{i, j};
                } else if (cur == 'L') {
                    lever = new int[]{i, j};
                } else if (cur == 'E') {
                    exit = new int[]{i, j};
                }
            }
        }

        bfs();

        int answer = dist[exit[0]][exit[1]];
        if (answer != 0) {
            return answer;
        }

        return -1;
    }

    void bfs() {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        dist = new int[N][M];

        // S -> L
        q.add(start);
        visited[start[0]][start[1]] = true;

        int distSL = -1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            if (cur[0] == lever[0] && cur[1] == lever[1]) {
                distSL = dist[cur[0]][cur[1]];
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    continue;
                }

                if (map[nr][nc] == 'X') {
                    continue;
                }

                if (!visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                }
            }
        }

        // 레버에 도달하지 못할 경우
        if (distSL == -1) {
            dist[exit[0]][exit[1]] = 0;
            return;
        }

        // L -> E
        q.clear();
        visited = new boolean[N][M];
        dist = new int[N][M];

        q.add(lever);
        visited[lever[0]][lever[1]] = true;

        int distLE = -1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            if (cur[0] == exit[0] && cur[1] == exit[1]) {
                distLE = dist[cur[0]][cur[1]];
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    continue;
                }

                if (map[nr][nc] == 'X') {
                    continue;
                }

                if (!visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                }
            }
        }

        // 출구에 도달하지 못할 경우
        if (distLE == -1) {
            dist[exit[0]][exit[1]] = 0;
            return;
        }

        dist[exit[0]][exit[1]] = distSL + distLE;
    }
}
