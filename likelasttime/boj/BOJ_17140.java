import java.util.*;
/* 3 * 3 배열
 * 배열의 인덱스는 1부터 시작
 * 1초가 지날때마다 배열에 연산이 적용된다.
 * R 연산: 모든 행에 대해 정렬을 수행(행의 개수 >= 열의 개수인 경우)
 * C 연산: 모든 열에 대해 정렬을 수행(행의 개수 < 열의 개수)
 * 각각의 수가 몇 번 나왔는지 알아야 한다.
 * 수의 등장 횟수가 커지는 순 > 수가 커지는 순
 * 배열에 정렬된 결과를 다시 넣는다.
 * 수와 등장 횟수를 모두 넣는다.
 * R 연산이 적용된 경우에는 가장 큰 행을 기준으로 모든 행의 크기가 변한다.
 * C 연산이 적용된 경우에는 가장 큰 열을 기준으로 모든 열의 크기가 변한다.
 * 행 또는 열의 크기가 커진 곳에는 0이 채워진다.
 * 수를 정렬할 때 0은 무시해야 한다.
 * 행 또는 열의 크기가 100을 넘어가면 처음 100개를 제외한 나머지는 버린다.
 * A[r][c]에 들어있는 값이 k가 되기 위한 최소 시간 구하기
 *
 */
public class Main {
    static int r, c, k;
    static int[][] A = new int[101][101];
    static int rowSize = 3, colSize = 3;

    static class Pair {
        int num, cnt;
        Pair(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        r = sc.nextInt() - 1;
        c = sc.nextInt() - 1;
        k = sc.nextInt();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        System.out.println(simulate());
    }

    static int simulate() {
        for (int time = 0; time <= 100; time++) {
            if (r < rowSize && c < colSize && A[r][c] == k) {
                return time;
            }

            if (rowSize >= colSize) {
                R();
            } else {
                C();
            }
        }
        return -1;
    }

    // R 연산
    static void R() {
        int newColSize = 0;

        for (int i = 0; i < rowSize; i++) {
            Map<Integer, Integer> freq = new HashMap<>();

            for (int j = 0; j < colSize; j++) {
                if (A[i][j] == 0) continue;
                freq.put(A[i][j], freq.getOrDefault(A[i][j], 0) + 1);
            }

            List<Pair> list = new ArrayList<>();
            for (int key : freq.keySet()) {
                list.add(new Pair(key, freq.get(key)));
            }

            list.sort((a, b) -> {
                if (a.cnt != b.cnt) return a.cnt - b.cnt;
                return a.num - b.num;
            });

            int idx = 0;
            for (Pair p : list) {
                if (idx >= 100) break;
                A[i][idx++] = p.num;
                if (idx >= 100) break;
                A[i][idx++] = p.cnt;
            }

            newColSize = Math.max(newColSize, idx);

            while (idx < 100) {
                A[i][idx++] = 0;
            }
        }

        colSize = Math.min(100, newColSize);
    }

    // C 연산
    static void C() {
        int newRowSize = 0;

        for (int j = 0; j < colSize; j++) {
            Map<Integer, Integer> freq = new HashMap<>();

            for (int i = 0; i < rowSize; i++) {
                if (A[i][j] == 0) continue;
                freq.put(A[i][j], freq.getOrDefault(A[i][j], 0) + 1);
            }

            List<Pair> list = new ArrayList<>();
            for (int key : freq.keySet()) {
                list.add(new Pair(key, freq.get(key)));
            }

            list.sort((a, b) -> {
                if (a.cnt != b.cnt) return a.cnt - b.cnt;
                return a.num - b.num;
            });

            int idx = 0;
            for (Pair p : list) {
                if (idx >= 100) break;
                A[idx++][j] = p.num;
                if (idx >= 100) break;
                A[idx++][j] = p.cnt;
            }

            newRowSize = Math.max(newRowSize, idx);

            while (idx < 100) {
                A[idx++][j] = 0;
            }
        }

        rowSize = Math.min(100, newRowSize);
    }
}
