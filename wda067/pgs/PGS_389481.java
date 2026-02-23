import java.util.HashSet;
import java.util.Set;

/*
프로그래머스 / 봉인된 주문 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/389481
 */
class PGS_389481 {

    public String solution(long n, String[] bans) {

        // 삭제 문제열을 순번으로 변환해 저장
        Set<Long> set = new HashSet<>();
        int count = 0;

        for (String s : bans) {
            set.add(parseLong(s));
        }

        // n 이하에 존재하는 삭제 문자열 카운트
        for (long l : set) {
            if (l <= n) {
                count++;
            }
        }

        // count 만큼 n을 증가시켜 실제 순번 보정
        for (int i = 0; i < count; i++) {
            n++;

            // 증가한 n이 삭제 순번이라면 계속 증가
            while (set.contains(n)) {
                n++;
            }
        }

        // 최종 순번으로 문자열 변환
        return calOrder(n);
    }

    // 문자열 -> 숫자 변환
    // a=1, z=26, aa=27, ab=28, ...
    private long parseLong(String s) {
        long total = 0;
        long c = 1;  // 1, 26, 26^2, ...

        // 뒤에서부터 자리수 계산
        for (int i = s.length() - 1; i >= 0; i--) {
            total += c * (s.charAt(i) - 'a' + 1);
            c *= 26;
        }

        return total;
    }

    // 숫자 -> 문자열 변환
    // 1=a, 26=z, 27=aa, 28=ab, ...
    private String calOrder(long n) {
        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            // 0~25 범위로 변환
            char c = (char) ('a' + (n - 1) % 26);
            sb.append(c);

            // 다음 자리 계산
            n = (n - 1) / 26;
        }

        return sb.reverse().toString();
    }
}
