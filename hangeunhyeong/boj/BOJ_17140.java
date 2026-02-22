package hangeunhyeong.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class BOJ_17140 {
    static class Pair {
        int num, cnt;
        Pair(int num, int cnt) { this.num = num; this.cnt = cnt; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken()) - 1;
        int c = Integer.parseInt(st.nextToken()) - 1;
        int k = Integer.parseInt(st.nextToken());

        int[][] A = new int[3][3];
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) A[i][j] = Integer.parseInt(st.nextToken());
        }

        for (int t = 0; t <= 100; t++) {
            if (r < A.length && c < A[0].length && A[r][c] == k) {
                System.out.println(t);
                return;
            }
            if (t == 100) break;

            if (A.length >= A[0].length) A = opR(A);
            else A = opC(A);
        }

        System.out.println(-1);
    }

    static int[][] opR(int[][] A) {
        int R = A.length, C = A[0].length;
        List<int[]> rows = new ArrayList<>();
        int maxLen = 0;

        for (int i = 0; i < R; i++) {
            int[] newRow = transformLine(A[i]);
            maxLen = Math.max(maxLen, newRow.length);
            rows.add(newRow);
        }

        maxLen = Math.min(100, maxLen);
        int[][] B = new int[R][maxLen];

        for (int i = 0; i < R; i++) {
            int[] row = rows.get(i);
            int len = Math.min(maxLen, row.length);
            System.arraycopy(row, 0, B[i], 0, len);
        }
        return B;
    }

    static int[][] opC(int[][] A) {
        int R = A.length, C = A[0].length;
        List<int[]> cols = new ArrayList<>();
        int maxLen = 0;

        for (int j = 0; j < C; j++) {
            int[] col = new int[R];
            for (int i = 0; i < R; i++) col[i] = A[i][j];

            int[] newCol = transformLine(col);
            maxLen = Math.max(maxLen, newCol.length);
            cols.add(newCol);
        }

        maxLen = Math.min(100, maxLen);
        int[][] B = new int[maxLen][C];

        for (int j = 0; j < C; j++) {
            int[] col = cols.get(j);
            int len = Math.min(maxLen, col.length);
            for (int i = 0; i < len; i++) B[i][j] = col[i];
        }
        return B;
    }

    // 한 행/열을 규칙대로 (num,cnt) 정렬 후 펼친 배열로 변환
    static int[] transformLine(int[] line) {
        int[] freq = new int[101]; // 문제에서 수는 <= 100
        for (int x : line) {
            if (x == 0) continue;
            freq[x]++;
        }

        List<Pair> list = new ArrayList<>();
        for (int num = 1; num <= 100; num++) {
            if (freq[num] > 0) list.add(new Pair(num, freq[num]));
        }

        list.sort((a, b) -> {
            if (a.cnt != b.cnt) return a.cnt - b.cnt;
            return a.num - b.num;
        });

        // (num,cnt)로 펼치되 최대 100개
        int size = Math.min(100, list.size() * 2);
        int[] res = new int[size];
        int idx = 0;
        for (Pair p : list) {
            if (idx >= 100) break;
            res[idx++] = p.num;
            if (idx >= 100) break;
            res[idx++] = p.cnt;
        }
        return res;
    }
}