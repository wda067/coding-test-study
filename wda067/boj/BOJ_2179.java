import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/*
백준 / 비슷한 단어 / 골드4
https://www.acmicpc.net/problem/2179
 */
public class BOJ_2179 {

    private static int maxLen = -1;
    private static int maxS = Integer.MAX_VALUE;
    private static int maxT = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = br.readLine();
        }

        // 접두사 -> 첫 번째 단어 인덱스
        HashMap<String, Integer> map = new HashMap<>();

        // 단어 입력 순으로 탐색
        for (int i = 0; i < N; i++) {
            String word = words[i];

            // 현재 단어의 모든 접두사 생성
            for (int len = 1; len <= word.length(); len++) {
                String prefix = word.substring(0, len);

                Integer prev = map.get(prefix);  // 생성한 접두사의 단어 인덱스 조회

                // 첫 접두사일 경우 최초 단어 삽입
                if (prev == null) {
                    map.put(prefix, i);
                    continue;
                }

                // 갱신 여부 판단
                if (compare(len, prev, i)) {
                    maxLen = len;
                    maxS = prev;
                    maxT = i;
                }
            }
        }

        System.out.println(words[maxS]);
        System.out.println(words[maxT]);
    }

    private static boolean compare(int len, int a, int b) {
        if (len != maxLen) {
            return len > maxLen;  // 길이가 더 크면 true
        }
        if (a != maxS) {
            return a < maxS;  // 첫 단어가 더 먼저면 true
        }
        return b < maxT;  // 둘째 단어가 더 먼저면 true
    }
}
