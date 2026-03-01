package hangeunhyeong.boj;
import java.io.*;
import java.util.*;
/*
BOJ 2632 - 피자판매
풀이가 생각이 안나 GPT 참고했습니다..
 */
public class BOJ_2632 {
    static int S;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        S = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] A = new int[m];
        int[] B = new int[n];

        for (int i = 0; i < m; i++) A[i] = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < n; i++) B[i] = Integer.parseInt(br.readLine().trim());

        int[] cntA = buildCounts(A, m);
        int[] cntB = buildCounts(B, n);

        long ans = 0;
        for (int a = 0; a <= S; a++) {
            ans += (long) cntA[a] * cntB[S - a];
        }

        System.out.println(ans);
    }

    // 원형 피자에서 만들 수 있는 "연속 부분합"의 빈도 배열 리턴 (0..S)
    static int[] buildCounts(int[] arr, int len) {
        int[] cnt = new int[S + 1];
        cnt[0] = 1; // 이 피자에서 아무것도 안 고르는 경우(혼합/단독 처리용)

        // 2배 길이 prefix
        int[] prefix = new int[2 * len + 1];
        for (int i = 0; i < 2 * len; i++) {
            prefix[i + 1] = prefix[i] + arr[i % len];
        }

        int total = prefix[len]; // 전체 피자 합

        // 길이 1..len-1 구간합 모두 카운트
        for (int start = 0; start < len; start++) {
            for (int l = 1; l <= len - 1; l++) {
                int sum = prefix[start + l] - prefix[start];
                if (sum <= S) cnt[sum]++;
            }
        }

        // 전체 피자(길이 len)는 시작점 여러 개여도 "같은 조합"으로 1번만
        if (total <= S) cnt[total]++;

        return cnt;
    }
}