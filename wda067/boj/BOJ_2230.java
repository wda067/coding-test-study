import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
백준 / 수 고르기 / 골드5
https://www.acmicpc.net/problem/2230
 */
public class BOJ_2230 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());  // 100,000
        int M = Integer.parseInt(st.nextToken());  // 2,000,000,000
        int[] A = new int[N];

        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(br.readLine());  // 1,000,000,000
        }

        // 투 포인터 -> 차이가 M 이상이 되는 순간 찾기
        Arrays.sort(A);
        int s = 0;
        int e = 0;
        int answer = Integer.MAX_VALUE;

        while (s < N && e < N) {
            int diff = A[e] - A[s];

            // M 보다 작으면 차이를 늘려야 함
            if (diff < M) {
                e++;
            }

            // M 이상이면 값 갱신 후 포인터 이동
            else {
                answer = Math.min(answer, diff);
                s++;
                // s < e 보장
                if (s == e) {
                    e++;
                }
            }
        }

        System.out.println(answer);
    }
}
