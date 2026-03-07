import java.util.*;
/*
    1층부터 N층까지 이용이 가능한 엘리베이터가 있다.
    엘리베이터의 층수를 보여주는 디스플레이에는 K자리의 수가 보인다.
    수는 0으로 시작할 수 있다.
    최소 1개, 최대 P개를 반전시킬 계획을 세우고 있다.
    반전은 켜진 부분은 끄고, 꺼진 부분은 켜는 것이다.
    반전 이후에 디스플레이에 올바른 수가 보여지면서 1이상 N이하가 되도록 바꾼다.
    X층에 멈춰있을 때 호석이가 반전시킬 LED를 고를 수 있는 경우의 수를 계산한다.
*/
class BOJ_22251 {

    // a b c d e f g 순서 (7비트)
    static int[] seg = new int[10];

    static {
        seg[0] = 0b1111110;
        seg[1] = 0b0110000;
        seg[2] = 0b1101101;
        seg[3] = 0b1111001;
        seg[4] = 0b0110011;
        seg[5] = 0b1011011;
        seg[6] = 0b1011111;
        seg[7] = 0b1110000;
        seg[8] = 0b1111111;
        seg[9] = 0b1111011;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int P = sc.nextInt();
        int X = sc.nextInt();

        int answer = 0;

        for (int i = 1; i <= N; i++) {

            int diff = 0;
            int a = X;
            int b = i;

            for (int k = 0; k < K; k++) {
                int d1 = a % 10;
                int d2 = b % 10;

                // XOR → 서로 다른 비트 수가 반전 개수
                diff += Integer.bitCount(seg[d1] ^ seg[d2]);

                if (diff > P) break;

                a /= 10;
                b /= 10;
            }

            if (diff >= 1 && diff <= P)
                answer++;
        }

        System.out.println(answer);
    }
}