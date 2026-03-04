package hangeunhyeong.boj;

import java.io.*;
import java.util.*;
/*
BOJ 22251 - 빌런 호석
[접근]
현재 층과 1 ~ N 층을 비교하여 반전되는 디스플레이의 LED 개수가 P이하인 층의 개수를 센다
[풀이]
- 현재층과 비교할 층을 각각 K개의 칸으로 나타낸다(0포함)
- 현재층과 비교할 층을 칸별로 몇개가 반전되는지 XOR을 이용하여 알아낸다
    (칸끼리 XOR한 결과에서 1의 개수를 세면 한 칸에서 몇개가 반전되었는지 알 수 있다)
 */
public class BOJ_22251 {
    public static int N, K, P, X;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        int[] x = display(X);
        int[] bit = {0b1110111, 0b0100100, 0b1011101, 0b1101101, 0b0101110, 0b1101011, 0b1111011, 0b0100101, 0b1111111, 0b1101111, 0b1110111};
        int answer = 0;
        for(int i = 1; i <= N; i++){
            int[] cmp = display(i);
            int cnt = 0;
            for(int j = 0; j < K; j++){
                int a = bit[x[j]];   int b = bit[cmp[j]];
                cnt += count(a ^ b);
            }
            if(cnt <= P)
                answer++;
        }
        System.out.println(answer - 1);
    }
    public static int[] display(int X){
        int[] x = new int[K];
        for(int i = 0; X != 0 && i < K; i++){
            x[i] = X % 10;
            X = X / 10;
        }
        return x;
    }
    // 2진수 x에서 1의 개수 세기
    public static int count(int x){
        int result = 0;
        while(x > 0){
            if(x % 2 == 1)
                result++;
            x >>= 1;
        }
        return result;
    }
}
