import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
백준 / 사다리 조작 / 골드3
https://www.acmicpc.net/problem/15684
 */
public class BOJ_15684 {

    private static int N, H;
    private static int answer = -1;
    private static boolean[][] map;
    private static int limit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        // 행: 0...H-1 -> H의 개수와 동일
        // 열: 0...N-2 -> 세로선-1
        map = new boolean[H][N - 1];  // 가로선을 놓을 세로선 사이

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            map[a - 1][b - 1] = true;
        }

        // 가로선을 최대 3개까지 놓음
        for (int i = 0; i <= 3; i++) {
            limit = i;
            recur(0, 0);
            if (answer != -1) {  // 성공하면 종료
                break;
            }
        }

        System.out.println(answer);
    }

    private static void recur(int depth, int start) {
        if (answer != -1) {  // 성공하면 종료
            return;
        }

        if (depth == limit) {
            if (canPlace()) {  // 현재 조합으로 성공할 경우
                answer = depth;
            }
            return;
        }

        // start부터 탐색
        for (int i = start; i < H * (N - 1); i++) {
            // 2차원 -> 1차원 (r, c)
            int r = i / (N - 1);
            int c = i % (N - 1);

            // 현재 위치에 가로선을 놓을 수 있는지 검증
            if (!isValid(r, c)) {
                continue;
            }

            map[r][c] = true;
            recur(depth + 1, i + 1);
            map[r][c] = false;
        }
    }

    private static boolean canPlace() {
        for (int c = 0; c < N; c++) {
            if (!canGo(c)) {
                return false;
            }
        }

        return true;
    }

    private static boolean canGo(int start) {
        int c = start;
        for (int r = 0; r < H; r++) {
            if (c == 0) {
                if (map[r][c]) {
                    c++;
                }
            } else if (c == N - 1) {
                if (map[r][c - 1]) {
                    c--;
                }
            } else {
                if (map[r][c - 1]) {
                    c--;
                } else if (map[r][c]) {
                    c++;
                }
            }
        }

        return start == c;
    }

    private static boolean isValid(int row, int col) {
        // 이미 가로선이 존재하면 false
        if (map[row][col]) {
            return false;
        }

        // 왼쪽 이웃이 존재하고 왼쪽에 가로선이 존재하면 false
        if (col - 1 >= 0 && map[row][col - 1]) {
            return false;
        }

        // 오른쪽 이웃이 존재하고 오른쪽에 가로선이 존재하면 false
        if (col + 1 < N - 1 && map[row][col + 1]) {
            return false;
        }

        return true;
    }
}
