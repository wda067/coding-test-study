import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
백준 / Puyo Puyo / 골드4
https://www.acmicpc.net/problem/11559
 */
public class BOJ_11559 {

    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};
    private static char[][] map;
    private static boolean flag;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new char[12][6];
        for (int i = 0; i < 12; i++) {
            map[i] = br.readLine().toCharArray();
        }

        int count = 0;
        while (true) {
            flag = false;

            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 6; j++) {
                    if (map[i][j] != '.') {  // 빈칸이 아닌 경우
                        bfs(i, j);  // 터트림
                    }
                }
            }

            if (!flag) {  // 연쇄가 일어나지 않았을 경우
                break;
            }
            moveDown();
            count++;
        }

        System.out.println(count);
    }

    private static void bfs(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[12][6];
        q.add(new int[]{r, c});
        int count = 1;
        List<int[]> list = new ArrayList<>();

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cr = cur[0];
            int cc = cur[1];
            char color = map[cr][cc];
            visited[cr][cc] = true;
            list.add(new int[]{cr, cc});

            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];

                if (nr < 0 || nr >= 12 || nc < 0 || nc >= 6) {
                    continue;
                }

                // 같은 색인 부분 기록
                if (map[nr][nc] == color && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                    count++;
                }
            }
        }

        // 4개 이상 연결된 경우 터트림
        if (count >= 4) {
            flag = true;
            for (int[] ints : list) {
                map[ints[0]][ints[1]] = '.';
            }
        }
    }

    private static void moveDown() {
        for (int c = 0; c < 6; c++) {
            int writeRow = 11;

            // 아래부터 차례대로 쌓음
            for (int r = 11; r >= 0; r--) {
                if (map[r][c] != '.') {  // 빈칸이 아닐 때
                    char temp = map[r][c];
                    map[r][c] = '.';
                    map[writeRow--][c] = temp;
                }
            }
        }
    }
}
