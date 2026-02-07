import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
백준 / 크게 만들기 / 골드3
https://www.acmicpc.net/problem/2812
 */
public class BOJ_2812 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1 ≤ K < N ≤ 500,000 -> O(n)
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        String input = br.readLine();  // N자리 숫자

        StringBuilder stack = new StringBuilder();  // 최적의 숫자를 저장(스택)
        int index = 0;          // 현재 보고 있는 숫자의 인덱스
        int removeCount = 0;    // 지금까지 제거한 숫자 개수

        // 숫자를 좌측부터 탐색
        while (index < N) {
            char cur = input.charAt(index);

            // 스택의 마지막 숫자가 현재 숫자보다 작을 경우
            // 그리고 아직 지울 수 있는 경우
            // 마지막 숫자를 삭제하고 카운트
            // cur = 7, stack = [4, 1] -> [4] -> []
            while (stack.length() > 0 &&
                    stack.charAt(stack.length() - 1) < cur &&
                    removeCount < K) {
                stack.deleteCharAt(stack.length() - 1);  // stack pop
                removeCount++;
            }

            // 현재 숫자를 추가하고 인덱스 증가
            stack.append(cur);
            index++;
        }

        // 지울 수 있는 횟수가 남았을 경우 뒤부터 제거
        if (removeCount < K) {
            stack.setLength(stack.length() - (K - removeCount));
        }

        System.out.println(stack);
    }
}
