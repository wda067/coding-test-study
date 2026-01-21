package hangeunhyeong.pgs;

import java.util.ArrayList;

public class PGS_개인정보수집유효기간 {
    public int[] solution(String today, String[] terms, String[] privacies) {
        int n = privacies.length;
        // 약관종류에 따른 유효기간 분류(idx : 알파벳 순서 (A는 0 B는 1...), val : 유효기간 개월수)
        int[] alphabet = new int[26];
        for(String str : terms){
            String[] t = str.split(" ");
            char ch = t[0].charAt(0);
            alphabet[ch - 'A'] = Integer.parseInt(t[1]);
        }

        ArrayList<Integer> arr = new ArrayList<>();
        // privacies에서 날짜와 약관종류 분리
        for(int i = 0; i < n; i++){
            int last = privacies[i].length() - 1;
            int idx = privacies[i].charAt(last) - 'A';
            String[] date = privacies[i].split(" ")[0].split("\\.");

            // 날짜 : 문자열->int
            int y = Integer.parseInt(date[0]);
            int m = Integer.parseInt(date[1]);
            int d = Integer.parseInt(date[2]);

            // 약관종류에 따른 유효기간 구하기
            int duration = alphabet[idx] * 28;
            // 만료일 구하기 (날짜 + 유효기간 = 만료일)
            int total = y * 12 * 28 + m * 28 + d;
            int expire = total + duration;

            if(isExpired(today, expire))
                arr.add(i + 1);
        }

        int[] answer = new int[arr.size()];
        for(int i = 0; i < answer.length; i++){
            answer[i] = arr.get(i);
        }
        return answer;
    }
    public boolean isExpired(String today, int expired){
        String[] todayDate = today.split("\\.");
        int y = Integer.parseInt(todayDate[0]);
        int m = Integer.parseInt(todayDate[1]);
        int d = Integer.parseInt(todayDate[2]);
        int t = y * 12 * 28 + m * 28 + d;
        return t >= expired;
    }
    public static void main(String[] args){
        PGS_개인정보수집유효기간 sol = new PGS_개인정보수집유효기간();
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
        System.out.println(sol.solution(today, terms, privacies).toString());
    }
}
