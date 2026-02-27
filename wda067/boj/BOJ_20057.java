import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
백준 / 마법사 상어와 토네이도 / 골드3
https://www.acmicpc.net/problem/20057
 */
public class BOJ_20057 {

    private static int[] dr = {0, 1, 0, -1};
    private static int[] dc = {-1, 0, 1, 0};

    private static int[][] spreadR = {
            {-1, 1, -1, 1, -1, 1, -2, 2, 0, 0},
            {0, 0, 1, 1, 2, 2, 1, 1, 3, 2},
            {-1, 1, -1, 1, -1, 1, -2, 2, 0, 0},
            {0, 0, -1, -1, -2, -2, -1, -1, -3, -2}
    };
    private static int[][] spreadC = {
            {0, 0, -1, -1, -2, -2, -1, -1, -3, -2},
            {-1, 1, -1, 1, -1, 1, -2, 2, 0, 0},
            {0, 0, 1, 1, 2, 2, 1, 1, 3, 2},
            {-1, 1, -1, 1, -1, 1, -2, 2, 0, 0}
    };
    private static int[] percent = {1, 1, 7, 7, 10, 10, 2, 2, 5};

    private static int N;
    private static int[][] grid;
    private static long answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        grid = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시작 위치
        int r = N / 2, c = N / 2;
        int dir = 0;  // 현재 방향 (서)
        int len = 1;  // 현재 이동 길이

        while (true) {
            // 길이 len으로 2번 이동
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < len; j++) {
                    spreadSand(r, c, dir);  // 현재 위치에서 방향 d로 모래 확산

                    r += dr[dir];
                    c += dc[dir];

                    // (0, 0) 도달 시 종료
                    if (r == 0 && c == 0) {
                        System.out.println(answer);
                        return;
                    }
                }

                dir = (dir + 1) & 3;  // 방향 전환
            }

            len++;
        }
    }

    private static void spreadSand(int r, int c, int dir) {
        int nr = r + dr[dir];
        int nc = c + dc[dir];

        int cur = grid[nr][nc];
        if (cur == 0) {
            return;
        }

        int moved = 0;

        // 9개 퍼센트 위치에 분배
        for (int i = 0; i < 9; i++) {
            int plus = cur * percent[i] / 100;
            moved += plus;

            int rr = r + spreadR[dir][i];
            int cc = c + spreadC[dir][i];

            if (rr < 0 || cc < 0 || rr >= N || cc >= N) {  // 격자 밖일 때
                answer += plus;
            } else {
                grid[rr][cc] += plus;
            }
        }

        // 남은 모래 처리
        int alpha = cur - moved;
        int ar = r + spreadR[dir][9];
        int ac = c + spreadC[dir][9];

        if (ar < 0 || ac < 0 || ar >= N || ac >= N) {
            answer += alpha;
        } else {
            grid[ar][ac] += alpha;
        }

        // 기존 칸 모래 제거
        grid[nr][nc] = 0;
    }
}
