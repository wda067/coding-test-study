import java.util.*;
/*
 * N개의 스위치와 N개의 전구가 있다.
 * 각각의 전구는 켜져 있는 상태와 꺼져 있는 상태 중 하나의 상태를 가진다.
 * i(1 < i < N)번 스위치를 누르면
 * 	i - 1, i, i + 1의 3개의 전구의 상태가 바뀐다.
 * 	꺼져 있는 전구는 켜지고, 켜져 있는 친구는 꺼진다.
 *
 * 스위치를 최소 몇 번 누르면 되는지 구하기(불가능한 경우는 -1을 출력)
 *
 */
class Main {
    static int N;	// 2 <= 스위치의 갯수 <= 100,000
    static int[] origin;
    static String target;
    static final int INF = (int)1e9;

    static void press(int[] arr, int idx) {
        for (int i = idx - 1; i <= idx + 1; i++) {
            if (i >= 0 && i < N) {
                arr[i] = 1 - arr[i];
            }
        }
    }

    static int simulate(boolean pressFirst) {
        int[] bulbs = origin.clone();
        int cnt = 0;

        if (pressFirst) {
            press(bulbs, 0);
            cnt++;
        }

        for (int i = 1; i < N; i++) {
            int prevTarget = target.charAt(i - 1) - '0';
            if (bulbs[i - 1] != prevTarget) {
                press(bulbs, i);
                cnt++;
            }
        }

        // 최종 상태 확인
        for (int i = 0; i < N; i++) {
            if (bulbs[i] != target.charAt(i) - '0') {
                return INF;
            }
        }

        return cnt;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        origin = new int[N];
        String start = sc.next();
        target = sc.next();

        for (int i = 0; i < N; i++) {
            origin[i] = start.charAt(i) - '0';
        }

        int ans = Math.min(simulate(true), simulate(false));
        System.out.println(ans == INF ? -1 : ans);
    }
}