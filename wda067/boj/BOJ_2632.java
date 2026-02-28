import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
백준 / 피자판매 / 골드2
https://www.acmicpc.net/problem/2632
 */
public class BOJ_2632 {

    private static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());  // 2,000,000
        String[] s = br.readLine().split(" ");

        // 피자조각 개수 1,000
        int m = Integer.parseInt(s[0]);
        int n = Integer.parseInt(s[1]);

        int[] A = new int[m];
        int[] B = new int[n];

        for (int i = 0; i < m; i++) {
            A[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < n; i++) {
            B[i] = Integer.parseInt(br.readLine());
        }

        // 원형 연속 부분합의 개수 계산
        int[] cntA = build(A, m);
        int[] cntB = build(B, n);

        // A에서 i를 만드는 방법의 수 * B에서 (N-i)를 만드는 방법의 수 -> 모두 더함
        long answer = 0;
        for (int i = 0; i <= N; i++) {
            answer += (long) cntA[i] * cntB[N - i];
        }

        System.out.println(answer);
    }

    private static int[] build(int[] arr, int num) {
        // count[i]: i를 만들 수 있는 연속 부분합의 개수
        int[] count = new int[N + 1];
        count[0] = 1;  // 아무것도 선택하지 않는 경우 1개

        // 원형으로 2n까지 누적합 생성
        int[] S = new int[2 * num];
        S[0] = arr[0];
        for (int i = 1; i < 2 * num; i++) {
            S[i] = S[i - 1] + arr[i % num];
        }

        // 전체합
        int total = S[num - 1];
        if (total <= N) {
            count[total]++;  // 전체합을 만들 수 있는 경우 1개
        }

        for (int start = 0; start < num; start++) {

            // 길이 1 ~ n-1
            for (int len = 1; len < num; len++) {

                int end = start + len - 1;
                int sum;

                // start ~ end의 누적합
                if (start == 0) {
                    sum = S[end];
                } else {
                    sum = S[end] - S[start - 1];
                }

                if (sum <= N) {
                    count[sum]++;
                }
            }
        }

        return count;
    }
}
