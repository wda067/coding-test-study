import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/*
백준 / 이차원 배열과 연산 / 골드4
https://www.acmicpc.net/problem/17140
 */
public class BOJ_17140 {

    private static int[][] A;
    private static int maxR = 3, maxC = 3;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken()) - 1;
        int c = Integer.parseInt(st.nextToken()) - 1;
        int k = Integer.parseInt(st.nextToken());
        A = new int[100][100];

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int t = -1;
        while (t++ <= 100) { // 0~100초 검사
            if (A[r][c] == k) {
                System.out.println(t);
                return;
            }

            if (maxR >= maxC) {
                doRowOperation();  // R 연산
            } else {
                doColOperation();  // C 연산
            }
        }

        System.out.println(-1);
    }

    private static void doRowOperation() {
        int newMaxC = 0;

        for (int r = 0; r < maxR; r++) {
            int[] count = new int[101];  // 수의 등장 횟수 저장

            for (int c = 0; c < maxC; c++) {
                int v = A[r][c];
                if (v != 0) {
                    count[v]++;
                }
            }

            // 등장하는 수의 값과 등장 횟수를 저장
            List<Number> numbers = new ArrayList<>();
            for (int v = 1; v <= 100; v++) {
                if (count[v] > 0) {
                    numbers.add(new Number(v, count[v]));
                }
            }

            // 등장 횟수 오름차순 -> 값 오름차순
            numbers.sort(Comparator.comparingInt((Number o) -> o.count)
                    .thenComparingInt(o -> o.value));

            // 기존 행 초기화
            Arrays.fill(A[r], 0);

            int index = 0;
            // 값, 등장 횟수 순으로 배열에 기록
            // 길이가 100이 되면 스톱
            for (Number n : numbers) {
                if (index >= 100) {
                    break;
                }
                A[r][index++] = n.value;
                if (index >= 100) {
                    break;
                }
                A[r][index++] = n.count;
            }

            newMaxC = Math.max(newMaxC, index);
        }

        maxC = Math.min(100, newMaxC);
    }

    private static void doColOperation() {
        int newMaxR = 0;

        for (int c = 0; c < maxC; c++) {
            int[] count = new int[101];

            for (int r = 0; r < maxR; r++) {
                int v = A[r][c];
                if (v != 0) {
                    count[v]++;
                }
            }

            List<Number> numbers = new ArrayList<>();
            for (int v = 1; v <= 100; v++) {
                if (count[v] > 0) {
                    numbers.add(new Number(v, count[v]));
                }
            }

            numbers.sort(Comparator.comparingInt((Number o) -> o.count)
                    .thenComparingInt(o -> o.value));

            // 기존 열 초기화
            for (int r = 0; r < 100; r++) {
                A[r][c] = 0;
            }

            int index = 0;
            for (Number n : numbers) {
                if (index >= 100) {
                    break;
                }
                A[index++][c] = n.value;
                if (index >= 100) {
                    break;
                }
                A[index++][c] = n.count;
            }

            newMaxR = Math.max(newMaxR, index);
        }

        maxR = Math.min(100, newMaxR);
    }

    private static class Number {
        int value, count;

        public Number(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
}
