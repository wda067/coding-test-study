import java.io.*;
import java.util.*;

public class Main {

    static int N1, M1, N2, M2;
    static boolean[][] A;
    static List<int[]> B = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 퍼즐 A
        st = new StringTokenizer(br.readLine());
        N1 = Integer.parseInt(st.nextToken());
        M1 = Integer.parseInt(st.nextToken());
        A = new boolean[N1][M1];

        for (int i = 0; i < N1; i++) {
            String s = br.readLine();
            for (int j = 0; j < M1; j++) {
                if (s.charAt(j) == '1') {
                    A[i][j] = true;
                }
            }
        }

        // 퍼즐 B
        st = new StringTokenizer(br.readLine());
        N2 = Integer.parseInt(st.nextToken());
        M2 = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N2; i++) {
            String s = br.readLine();
            for (int j = 0; j < M2; j++) {
                if (s.charAt(j) == '1') {
                    B.add(new int[]{i, j});
                }
            }
        }

        int answer = Integer.MAX_VALUE;

        List<int[]> curB = B;
        int curN = N2, curM = M2;

        for (int rot = 0; rot < 4; rot++) {

            for (int dx = -50; dx <= 50; dx++) {
                for (int dy = -50; dy <= 50; dy++) {

                    if (overlap(curB, dx, dy)) continue;

                    int minX = 0, minY = 0;
                    int maxX = N1 - 1, maxY = M1 - 1;

                    for (int[] p : curB) {
                        int x = p[0] + dx;
                        int y = p[1] + dy;
                        minX = Math.min(minX, x);
                        minY = Math.min(minY, y);
                        maxX = Math.max(maxX, x);
                        maxY = Math.max(maxY, y);
                    }

                    int area = (maxX - minX + 1) * (maxY - minY + 1);
                    answer = Math.min(answer, area);
                }
            }

            // B 회전
            List<int[]> next = new ArrayList<>();
            for (int[] p : curB) {
                next.add(new int[]{p[1], curN - 1 - p[0]});
            }
            curB = next;
            int tmp = curN;
            curN = curM;
            curM = tmp;
        }

        System.out.println(answer);
    }

    static boolean overlap(List<int[]> curB, int dx, int dy) {
        for (int[] b : curB) {
            int x = b[0] + dx;
            int y = b[1] + dy;
            if (x >= 0 && x < N1 && y >= 0 && y < M1) {
                if (A[x][y]) return true;
            }
        }
        return false;
    }
}