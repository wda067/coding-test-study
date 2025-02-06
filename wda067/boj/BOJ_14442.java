import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
백준 / 벽 부수고 이동하기 2 / 골드3
https://www.acmicpc.net/problem/14442
 */
public class BOJ_14442 {

    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};
    private static int[][] map;
    private static int N, M, K;
    private static boolean[][][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M][K + 1];  //벽 부순 횟수를 포함하여 방문 체크

        for (int i = 0; i < N; i++) {
            char[] charArray = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                map[i][j] = charArray[j] - '0';
            }
        }

        //(0, 0)에서 출발, 거리는 1, 벽 부순 횟수는 0
        System.out.println(bfs(0, 0, 1, 0));
    }

    private static int bfs(int r, int c, int distance, int breakCount) {
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(r, c, distance, breakCount));
        visited[0][0][0] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            if (cur.r == N - 1 && cur.c == M - 1) {  //도착점에 도착하면 거리 반환
                return cur.dst;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nextR = cur.r + dr[dir];
                int nextC = cur.c + dc[dir];
                int nextDst = cur.dst + 1;
                int nextCnt = cur.cnt;

                if (nextR < 0 || nextR >= N || nextC < 0 || nextC >= M) {
                    continue;
                }

                //벽이 아닌 경우
                if (map[nextR][nextC] == 0 && !visited[nextR][nextC][nextCnt]) {
                    visited[nextR][nextC][nextCnt] = true;
                    q.add(new Node(nextR, nextC, nextDst, nextCnt));
                }

                if (nextCnt >= K) {  //벽을 부술 수 없으면 스킵
                    continue;
                }

                //벽이지만 부술 수 있는 경우
                if (map[nextR][nextC] == 1 && !visited[nextR][nextC][nextCnt + 1]) {
                    visited[nextR][nextC][nextCnt + 1] = true;
                    q.add(new Node(nextR, nextC, nextDst, nextCnt + 1));
                }
            }
        }

        return -1;
    }

    private static class Node {
        private int r, c, dst, cnt;

        public Node(int r, int c, int dst, int cnt) {
            this.r = r;
            this.c = c;
            this.dst = dst;
            this.cnt = cnt;
        }
    }
}
