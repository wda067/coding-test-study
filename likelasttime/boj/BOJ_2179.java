import java.io.*;

public class BOJ_2179 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 단어 개수 (2 ≤ n ≤ 20,000)
        int n = Integer.parseInt(br.readLine());

        // 입력 순서를 그대로 유지하기 위해 배열 사용
        String[] strings = new String[n];

        for (int i = 0; i < n; i++) {
            strings[i] = br.readLine();
        }

        // 지금까지 발견한 최대 접두사 길이
        int maxLength = -1;

        // 정답으로 출력할 두 단어
        String a = "";
        String b = "";

        /*
         * 모든 서로 다른 두 단어 쌍 (i < j)을 비교
         * → 입력 순서가 곧 우선순위이므로
         *   같은 길이 접두사가 나와도 먼저 발견된 쌍이 유지됨
         */
        for (int i = 0; i < n - 1; i++) {
            String now = strings[i];

            // 현재 단어 길이가 이미 찾은 최대 접두사보다 짧거나 같으면
            // 더 긴 접두사를 만들 수 없으므로 스킵
            if (now.length() <= maxLength) continue;

            for (int j = i + 1; j < n; j++) {
                String next = strings[j];

                // 비교 대상 단어도 마찬가지로 길이가 부족하면 스킵
                if (next.length() <= maxLength) continue;

                // 두 단어의 공통 접두사 길이 계산
                int length = commonLength(now, next);

                /*
                 * 더 긴 접두사를 발견한 경우에만 갱신
                 * 같은 길이일 경우에는 갱신하지 않음
                 * → 문제 조건:
                 *   "입력되는 순서대로 제일 앞쪽에 있는 단어"
                 *   즉, 먼저 나온 쌍이 우선
                 */
                if (length > maxLength) {
                    maxLength = length;
                    a = now;
                    b = next;
                }
            }
        }

        // 정답 출력
        System.out.println(a);
        System.out.println(b);
    }

    /**
     * 두 문자열의 공통 접두사 길이를 구하는 함수
     * 예) "abcd", "abce" → 3
     */
    static int commonLength(String a, String b) {
        int length = Math.min(a.length(), b.length());
        int idx = 0;

        // 앞에서부터 한 글자씩 비교
        while (idx < length && a.charAt(idx) == b.charAt(idx)) {
            idx++;
        }

        return idx;
    }
}
