import java.io.*;
import java.util.*;

public class Main {
    static int T, N, M;
    static int[] A, B;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        A = new int[N];
        B = new int[M];

        for (int i = 0; i < N; i++) A[i] = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) B[i] = Integer.parseInt(br.readLine());

        ArrayList<Integer> sumA = getAllSums(A, N);
        ArrayList<Integer> sumB = getAllSums(B, M);

        Collections.sort(sumB);

        long answer = 0;

        for (int a : sumA) {
            int target = T - a;
            answer += upperBound(sumB, target) - lowerBound(sumB, target);
        }

        System.out.println(answer);
    }

    static ArrayList<Integer> getAllSums(int[] arr, int n) {
        ArrayList<Integer> sums = new ArrayList<>();
        int[] extended = new int[2 * n];

        for (int i = 0; i < n; i++) {
            extended[i] = extended[i + n] = arr[i];
        }

        // 부분합
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int len = 1; len < n; len++) {
                sum += extended[i + len - 1];
                sums.add(sum);
            }
        }

        // 전체 피자 합 (한 번만)
        int total = 0;
        for (int x : arr) total += x;
        sums.add(total);

        // 아무것도 안 고른 경우
        sums.add(0);

        return sums;
    }

    static int lowerBound(ArrayList<Integer> list, int target) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (list.get(mid) >= target) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    static int upperBound(ArrayList<Integer> list, int target) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (list.get(mid) > target) r = mid;
            else l = mid + 1;
        }
        return l;
    }
}