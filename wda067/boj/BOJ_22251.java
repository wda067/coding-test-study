import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
백준 / 빌런 호석 / 골드5
https://www.acmicpc.net/problem/22251
 */
public class BOJ_22251 {

    private static final boolean[][] DIGIT = {
            {true, true, true, true, true, true, false},        // 0
            {false, true, true, false, false, false, false},    // 1
            {true, true, false, true, true, false, true},       // 2
            {true, true, true, true, false, false, true},       // 3
            {false, true, true, false, false, true, true},      // 4
            {true, false, true, true, false, true, true},       // 5
            {true, false, true, true, true, true, true},        // 6
            {true, true, true, false, false, false, false},     // 7
            {true, true, true, true, true, true, true},         // 8
            {true, true, true, true, false, true, true}         // 9
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        // cost[a][b] = 숫자 a를 숫자 b로 바꾸는 데 필요한 LED 반전 개수
        int[][] cost = new int[10][10];
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                cost[a][b] = diff(a, b);
            }
        }

        // 현재 층 X를 K자리 숫자로 분해 (선행 0 포함)
        int[] xDigits = toKDigits(X, K);

        int answer = 0;

        // 가능한 모든 층 Y를 검사 (1 ~ N)
        for (int y = 1; y <= N; y++) {

            // 같은 층은 최소 1개의 LED를 바꿔야 한다는 조건 때문에 제외
            if (y == X) {
                continue;
            }

            // Y도 동일하게 K자리 숫자로 변환
            int[] yDigits = toKDigits(y, K);

            int flips = 0; // 필요한 LED 반전 개수

            // 각 자리 숫자를 비교하여 LED 반전 개수 누적
            for (int i = 0; i < K; i++) {
                flips += cost[xDigits[i]][yDigits[i]];

                // 이미 P를 초과하면 더 볼 필요 없음
                if (flips > P) {
                    break;
                }
            }

            // 최소 1개 이상, 최대 P개 이하 반전이면 가능한 층
            if (1 <= flips && flips <= P) {
                answer++;
            }
        }

        System.out.println(answer);
    }

    // 숫자 a를 b로 바꿀 때 다른 세그먼트 개수 계산
    private static int diff(int a, int b) {
        int cnt = 0;

        for (int i = 0; i < 7; i++) {
            if (DIGIT[a][i] != DIGIT[b][i]) {
                cnt++;
            }
        }

        return cnt;
    }

    // 숫자를 K자리 배열로 변환 (1의 자리부터 저장)
    private static int[] toKDigits(int num, int K) {
        int[] digits = new int[K];

        for (int i = 0; i < K; i++) {
            digits[i] = num % 10;
            num /= 10;
        }

        return digits;
    }
}
