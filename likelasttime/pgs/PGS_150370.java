import java.util.*;
/*
    1~n번으로 분류되는 개인정보 n개가 있다.
    각 약관마다 개인정보 보관 유효기간이 정해져 있다.
    모든 달은 28일까지 있다
    오늘 날짜로 파기해야 할 개인정보 번호들을 구하자.
*/
class PGS_150370 {
    final static int MAX = 28;
    final static int TOTAL_DAY = 336;   // 1년은 336일

    /*
        날짜를 일로 변환
    */
    public int trans(String d) {
        String[] arr = d.split("\\.");
        return Integer.parseInt(arr[0]) * TOTAL_DAY
                + Integer.parseInt(arr[1]) * MAX
                + Integer.parseInt(arr[2]);
    }

    public int[] solution(String today, String[] terms, String[] privacies) {
        List<Integer> lst = new ArrayList();
        int todayDate = trans(today);
        int[] kind = new int[26];
        for(String term : terms) {
            String[] str = term.split(" ");
            kind[str[0].charAt(0) - 'A'] = Integer.parseInt(str[1]);
        }

        for(int i=0; i<privacies.length; i++) {
            String[] privacyArr = privacies[i].split(" ");
            int result = trans(privacyArr[0]) + (kind[privacyArr[1].charAt(0) - 'A'] * MAX);
            if(result <= todayDate) {
                lst.add(i + 1);
            }
        }

        int[] answer = new int[lst.size()];
        for(int i=0; i<lst.size(); i++) {
            answer[i] = lst.get(i);
        }
        return answer;      // 오름차순
    }
}