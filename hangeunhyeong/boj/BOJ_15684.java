package hangeunhyeong.boj;

import java.io.*;
import java.util.*;

public class BOJ_15684 {
    static int N, M, H;
    static boolean[][] ladder; // ladder[row][col] = 연결 (col <-> col+1)
    static int answer = -1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        ladder = new boolean[H + 1][N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            ladder[a][b] = true;
        }

        // 0~3개 추가 시도
        for (int limit = 0; limit <= 3; limit++) {
            if (dfs(0, limit, 1, 1)) { // (추가한 개수, 목표, 시작 row, 시작 col)
                answer = limit;
                break;
            }
        }

        System.out.println(answer);
    }

    // depth: 지금까지 추가한 개수, limit: 목표 추가 개수
    // sr, sc: 다음 탐색 시작 위치 (중복 방지용)
    static boolean dfs(int depth, int limit, int sr, int sc) {
        if (depth == limit) {
            return check();
        }

        // (row, col) 순서로 훑기
        for (int r = sr; r <= H; r++) {
            int cStart = (r == sr ? sc : 1);
            for (int c = cStart; c <= N - 1; c++) {
                if (canPlace(r, c)) {
                    ladder[r][c] = true;
                    // 같은 행에서는 c+2부터 보는 게 효율적(인접 금지라 c+1은 어차피 불가)
                    int nextR = r;
                    int nextC = c + 2;
                    if (nextC > N - 1) { // 다음 행으로 넘어갈 때 col reset
                        nextR = r + 1;
                        nextC = 1;
                    }
                    if (dfs(depth + 1, limit, nextR, nextC)) return true;
                    ladder[r][c] = false;
                }
            }
        }
        return false;
    }

    static boolean canPlace(int r, int c) {
        if (ladder[r][c]) return false;
        if (c > 1 && ladder[r][c - 1]) return false;
        if (c < N - 1 && ladder[r][c + 1]) return false;
        return true;
    }

    static boolean check() {
        for (int start = 1; start <= N; start++) {
            int pos = start;
            for (int r = 1; r <= H; r++) {
                if (ladder[r][pos]) {
                    pos++;
                } else if (pos > 1 && ladder[r][pos - 1]) {
                    pos--;
                }
            }
            if (pos != start) return false;
        }
        return true;
    }
}