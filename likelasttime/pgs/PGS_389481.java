/*
    알파벳 소문자 11글자 이하로 쓸 수 있는 모든 문자열이 규칙에 따라 정렬되어 있다
    1. 글자 수가 적은 주문부터 먼저 기록
    2. 글자 수가 같으면, 사전 순으로 기록
    삭제가 완료된 주문서에서 n번째 주문을 찾아야 한다.
    삭제가 완료된 주문서의 n번째 주문을 리턴

    bans를 오름차순 정렬
    bans 맨앞과 비교
*/
import java.util.*;

class Solution {

    static String[] bans;
    static long n;

    public String solution(long n, String[] bans) {
        this.bans = bans;
        this.n = n;

        Arrays.sort(this.bans);

        int length = findLength();          // 문자열 길이 결정
        return buildString(length);         // 사전순으로 문자열 구성
    }

    /*
     * n이 속한 문자열 길이 찾기
     */
    private int findLength() {
        for (int len = 1; len <= 11; len++) {
            long total = pow26(len);
            long banned = countBans("", len);
            long valid = total - banned;

            if (n > valid) {
                n -= valid;
            } else {
                return len;
            }
        }
        return -1;
    }

    /*
     * 사전순으로 문자열 만들기
     */
    private String buildString(int len) {
        StringBuilder sb = new StringBuilder();

        while (sb.length() < len) {
            for (char c = 'a'; c <= 'z'; c++) {
                String next = sb.toString() + c;
                long cnt = countValid(next, len);

                if (n > cnt) {
                    n -= cnt;     // 이 블록 통째로 스킵
                } else {
                    sb.append(c); // 이 글자 확정
                    break;
                }
            }
        }
        return sb.toString();
    }

    /*
     * prefix로 시작하는 "유효한" 문자열 개수
     */
    private long countValid(String prefix, int len) {
        long total = pow26(len - prefix.length());
        long banned = countBans(prefix, len);
        return total - banned;
    }

    /*
     * bans 중에서
     * - 길이 == len
     * - prefix로 시작하는 문자열 개수
     */
    private long countBans(String prefix, int len) {
        long cnt = 0;
        for (String b : bans) {
            if (b.length() != len) continue;
            if (b.startsWith(prefix)) cnt++;
        }
        return cnt;
    }

    private long pow26(int exp) {
        long r = 1;
        for (int i = 0; i < exp; i++) r *= 26;
        return r;
    }
}
