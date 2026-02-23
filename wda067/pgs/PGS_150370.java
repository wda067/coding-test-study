import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
프로그래머스 / 개인정보 수집 유효기간 / Level 1
https://school.programmers.co.kr/learn/courses/30/lessons/150370
 */
class PGS_150370 {

    public List<Integer> solution(String today, String[] terms, String[] privacies) {
        LocalDate todayDate = parse(today);

        // 약관 -> 유효기간 매핑
        Map<String, Integer> termMap = new HashMap<>();
        for (String term : terms) {
            String[] split = term.split(" ");
            termMap.put(split[0], Integer.parseInt(split[1]));
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < privacies.length; i++) {
            String[] split = privacies[i].split(" ");
            LocalDate localDate = parse(split[0]);              // 개인정보 수집 일자
            String type = split[1];                             // 약관 종류

            int months = termMap.get(type);                     // 유효기간
            LocalDate expire = localDate.plusMonths(months);    // 만료기간

            // 유효기간이 지났으면 추가
            if (expire.isBefore(todayDate) || expire.isEqual(todayDate)) {
                result.add(i + 1);
            }
        }

        return result;
    }

    // 2021.05.02 -> LocalDate로 변환
    private LocalDate parse(String raw) {
        String[] split = raw.split("\\.");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        return LocalDate.of(year, month, day);
    }
}
