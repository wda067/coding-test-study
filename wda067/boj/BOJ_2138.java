import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
백준 / 전구와 스위치 / 골드4
https://www.acmicpc.net/problem/2138
 */
public class BOJ_2138 {

    private static int N;
    private static int[] cur, target;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        // 현재 상태
        cur = new int[N];
        String s = br.readLine();
        for (int i = 0; i < N; i++) {
            cur[i] = s.charAt(i) - '0';
        }

        // 목표
        target = new int[N];
        s = br.readLine();
        for (int i = 0; i < N; i++) {
            target[i] = s.charAt(i) - '0';
        }

        /*
        왼쪽부터 탐색하면서 목표와 비교한다.
        i-1번째 전구가 목표와 같은지 여부에 따라 i번째 스위치를 누르는 것이 강제된다.
        -> 그리디
        첫번째 스위치는 이전이 없기 때문에 누르는 경우/ 안 누르는 경우로 케이스를 나눈다.
         */
        int pressFirst = process(true);
        int notPressFirst = process(false);

        int min = Math.min(pressFirst, notPressFirst);
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }

    private static int process(boolean firstPressed) {
        int count = 0;
        int[] clone = cur.clone();

        // 첫번째 스위치 확인
        if (firstPressed) {
            toggle(clone, 0);
            count++;
        }

        // i-1번 전구가 목표와 다르면 i번 스위치를 눌러야 한다.
        for (int i = 1; i < N; i++) {
            if (clone[i - 1] != target[i - 1]) {
                toggle(clone, i);
                count++;
            }
        }

        /*
        배열의 equals()는 Object.equals()를 사용하여 주소 비교라 원소 비고 X
        Arrays.equals()는 원소까지 비교 가능
         */
        if (Arrays.equals(clone, target)) {
            return count;
        }

        return Integer.MAX_VALUE;  // 목표 도달 불가
    }

    // arr[idx] 전구 토글
    private static void toggle(int[] arr, int idx) {
        /*
        xor 비트연산(두 비트가 다를 때 1)
        -> 1 ^ 1 = 0, 0 ^ 1 = 1
         */
        arr[idx] ^= 1;

        if (idx == 0) {             // 첫번째일 때
            arr[idx + 1] ^= 1;
        } else if (idx == N - 1) {  // 마지막일 때
            arr[idx - 1] ^= 1;
        } else {
            arr[idx - 1] ^= 1;
            arr[idx + 1] ^= 1;
        }
    }
}
