package hangeunhyeong.pgs;

import java.util.ArrayList;

public class PGS_개인정보수집유효기간 {
    public int[] solution(String today, String[] terms, String[] privacies) {
        int n = privacies.length;
        // 약관종류에 따라 유효기간 구하기
        int[] alphabet = new int[26];
        for(String str : terms){
            String[] t = str.split(" ");
            char ch = t[0].charAt(0);
            alphabet[ch - 'A'] = Integer.parseInt(t[1]);
        }

        int[][] expire = new int[n][3];
        int[][] date = new int[n][3];
        String[][] info = new String[n][2];
        ArrayList<Integer> arr = new ArrayList<>();

        for(int i = 0; i < n; i++){
            info[i] = privacies[i].split(" ");
            for(int j = 0; j < 3; j++){
                date[i][j] = Integer.parseInt(info[i][0].split("\\.")[j]);
            }
            int duration = alphabet[info[i][1].charAt(0) - 'A'];
            // yyyy
            expire[i][0] = date[i][0];

            //mm
            expire[i][1] = date[i][1] + duration;
            expire[i][0] += expire[i][1] % 12 == 0 ? expire[i][1] / 12 - 1 : expire[i][1] / 12;
            expire[i][1] = expire[i][1] % 12 == 0 ? 12 : expire[i][1] % 12;
            //dd
            expire[i][2] = date[i][2];
            System.out.printf("만료일 : %d/%d/%d\n", expire[i][0], expire[i][1], expire[i][2]);
            if(isExpired(today, expire[i]))
                arr.add(i + 1);
        }

        int[] answer = new int[arr.size()];
        for(int i = 0; i < answer.length; i++){
            answer[i] = arr.get(i);
        }
        return answer;
    }
    public boolean isExpired(String today, int[] expired){
        // expired를 문자열로 바꾸기
        String zero1 = "", zero2 = "";
        if(expired[1] < 10)
            zero1 = "0";
        if(expired[2] < 10)
            zero2 = "0";
        String expiredStr = expired[0] + "." + zero1 + expired[1] + "." + zero2 + expired[2];

        if(expiredStr.compareTo(today) <= 0){
            return true;
        }
        return false;
    }
    public static void main(String[] args){
        PGS_개인정보수집유효기간 sol = new PGS_개인정보수집유효기간();
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
        System.out.println(sol.solution(today, terms, privacies).toString());
    }
}
