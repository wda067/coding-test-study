import java.util.*;
/*
	N자리 숫자가 주어졌을 때 숫자 K개를 지워서 얻을 수 있는 가장 큰 수를 구하라.
 */
class BOJ_2812 {
    // 1 <= K < N <= 500,000
    static int N;
    static int K;
    static char[] NUM;		// 0으로 시작하지 않는 N자리 숫자

    public static String simulate() {
        Deque<Character> deque = new ArrayDeque();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++) {
            // deque의 가장 마지막 원소 값이 배열의 값 NUM[I]보다 작으면
            while(K > 0 && !deque.isEmpty() && NUM[i] > deque.peekLast()) {
                deque.pollLast();
                K--;
            }
            deque.offer(NUM[i]);
        }

        while(K > 0) {	// 아직 K개를 모두 제거하지 않았다면
            deque.pollLast();
            K--;
        }

        while(!deque.isEmpty()) {
            sb.append(deque.pollFirst());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        NUM = sc.next().toCharArray();
        System.out.print(simulate());
    }
}
