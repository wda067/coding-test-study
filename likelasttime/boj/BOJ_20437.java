import java.util.*;

/*
 * 알파벳 소문자로 이루어진 문자열 W가 주어진다.
 * 양의 정수 K가 주어진다.
 * 어떤 문자를 정확히 K개를 포함하는 가장 짧은 연속 문자열의 길이를 구하기
 * 어떤 문자를 정확히 K개를 포함하고, 문자열의 첫 번째와 마지막 글자가 해당 문자로 같은 가장 긴 연속 문자열의 길이를 구하기
 *
 * 위와 같은 방식으로 게임을 T회 진행
 */
public class BOJ_20437 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            String W = sc.next();
            int K = sc.nextInt();

            // 알파벳별 위치 저장
            ArrayList<Integer>[] pos = new ArrayList[26];
            for (int i = 0; i < 26; i++) pos[i] = new ArrayList<>();

            for (int i = 0; i < W.length(); i++) {
                pos[W.charAt(i) - 'a'].add(i);
            }

            int minLen = Integer.MAX_VALUE;
            int maxLen = 0;

            // 각 문자에 대해 연속 K개 확인
            for (int c = 0; c < 26; c++) {
                if (pos[c].size() < K) continue;

                for (int i = 0; i + K - 1 < pos[c].size(); i++) {
                    int len = pos[c].get(i + K - 1) - pos[c].get(i) + 1;
                    minLen = Math.min(minLen, len);
                    maxLen = Math.max(maxLen, len);
                }
            }

            if (maxLen == 0) {
                System.out.println(-1);
            } else {
                System.out.println(minLen + " " + maxLen);
            }
        }
    }
}
