import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
백준 / 문자열 게임 2 / 골드5
https://www.acmicpc.net/problem/20437
 */
public class BOJ_20437 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 0; testCase < T; testCase++) {
            String W = br.readLine();   // 알파벳 소문자로 이루어진 문자열
            int K = Integer.parseInt(br.readLine());

            // 3. 어떤 문자를 K개 포함하는 가장 짧은 연속 문자열의 길이
            // 4. 어떤 문자를 K개 포함하고, 첫번째와 마지막 글자가 같은 가장 긴 연속 문자열의 길이

            // 문자 -> 등장 인덱스들
            Map<Character, List<Integer>> map = new HashMap<>();

            for (int i = 0; i < W.length(); i++) {
                char c = W.charAt(i);
                map.computeIfAbsent(c, k -> new ArrayList<>())
                        .add(i);
            }

            int min = Integer.MAX_VALUE;  // 3번
            int max = Integer.MIN_VALUE;  // 4번

            // 각 문자별로 K개 확인
            for (Entry<Character, List<Integer>> entry : map.entrySet()) {
                List<Integer> idxs = entry.getValue();  // 현재 문자가 등장하는 인덱스 리스트
                if (idxs.size() < K) {  // K개 미만이면 패스
                    continue;
                }

                // (idxs[i], idxs[i+k-1]) 구간은 해당 문자가 K번 포함되고, 시작/끝 문자가 해당 문자
                for (int i = 0; i + K - 1 < idxs.size(); i++) {
                    int left = idxs.get(i);
                    int right = idxs.get(i + K - 1);
                    int len = right - left + 1;

                    if (len < min) {
                        min = len;
                    }
                    if (len > max) {
                        max = len;
                    }
                }
            }

            if (min == Integer.MAX_VALUE) {  // min이 존재하지 않으면 max도 존재하지 않음
                sb.append(-1).append("\n");
            } else {
                sb.append(min).append(" ").append(max).append("\n");
            }
        }

        System.out.println(sb);
    }
}
