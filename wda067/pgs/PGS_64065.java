import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
프로그래머스 / 튜플 / Level 2
https://school.programmers.co.kr/learn/courses/30/lessons/64065
 */
class PGS_64065 {

    public List<Integer> solution(String s) {
        s = s.substring(2, s.length() - 2);  // 처음 {{, 끝 }} 제거
        String[] split = s.split("\\},\\{");  // 원소를 },{으로 구분
        Arrays.sort(split, Comparator.comparingInt(String::length));  // split을 원소 개수로 정렬

        List<Integer> result = new ArrayList<>();
        for (String str : split) {
            String[] nums = str.split(",");

            for (String num : nums) {
                int x = Integer.parseInt(num);
                if (!result.contains(x)) {  // 새로운 숫자일 경우 추가
                    result.add(x);
                }
            }
        }

        return result;
    }
}
