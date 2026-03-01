import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
백준 / 짠돌이 호석 / 골드3
https://www.acmicpc.net/problem/21277
 */
public class BOJ_21277 {

    private static int[][] first = new int[150][150];
    private static int N1, M1;
    private static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("wda067/io/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N1 = Integer.parseInt(st.nextToken());
        M1 = Integer.parseInt(st.nextToken());

        // 첫번째 퍼즐 (50, 50)에 고정
        for (int i = 0; i < N1; i++) {
            String s = br.readLine();

            for (int j = 0; j < M1; j++) {
                first[50 + i][50 + j] = s.charAt(j) - '0';
            }
        }

        st = new StringTokenizer(br.readLine());
        int N2 = Integer.parseInt(st.nextToken());
        int M2 = Integer.parseInt(st.nextToken());

        int[][] second = new int[N2][M2];

        for (int i = 0; i < N2; i++) {
            String s = br.readLine();

            for (int j = 0; j < M2; j++) {
                second[i][j] = s.charAt(j) - '0';
            }
        }

        // 두번째 퍼즐을 90도 회전시키면서 탐색
        for (int d = 0; d < 4; d++) {

            // 두번째 퍼즐 좌측 상단을 (r, c)에 위치
            // 첫번째 퍼즐은 (50 ~ 99)에 위치
            for (int r = 0; r <= 100; r++) {
                for (int c = 0; c <= 100; c++) {
                    int simulation = simulation(r, c, second);
                    answer = Math.min(answer, simulation);
                }
            }

            // 90도 회전
            second = rotate(second);
        }

        System.out.println(answer);
    }

    /*
    두번째 퍼즐 좌측 상단이 (i, j)일 때
    겹치면 무효, 안 겹치면 최소 넓이 계산
     */
    private static int simulation(int i, int j, int[][] second) {
        int N2 = second.length;
        int M2 = second[0].length;

        // 겹침 검사
        for (int r = 0; r < N2; r++) {
            for (int c = 0; c < M2; c++) {
                if ((first[i + r][j + c] & second[r][c]) == 1) {
                    return Integer.MAX_VALUE;
                }
            }
        }

        // 넓이 계산
        int minR = Math.min(i, 50);
        int maxR = Math.max(50 + N1, i + N2);
        int minC = Math.min(j, 50);
        int maxC = Math.max(50 + M1, j + M2);

        return (maxR - minR) * (maxC - minC);
    }

    private static int[][] rotate(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] result = new int[M][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result[j][N - 1 - i] = matrix[i][j];
            }
        }

        return result;
    }
}
