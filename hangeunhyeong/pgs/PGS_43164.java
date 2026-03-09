package hangeunhyeong.pgs;

import java.util.*;

public class PGS_43164 {
    static String[][] t;
    static boolean[] used;
    static ArrayList<String> answer = new ArrayList<>();
    public String[] solution(String[][] tickets) {
        t = tickets;
        used = new boolean[tickets.length];
        dfs(0, "ICN", "ICN");
        Collections.sort(answer);
        return answer.get(0).split(" ");
    }
    public static void dfs(int cnt, String current, String records){
        if(cnt == t.length){
            answer.add(records);
            return;
        }
        for(int i = 0; i < t.length; i++){
            String departure = t[i][0];
            String destination = t[i][1];
            if(!used[i] && current.equals(departure)){
                used[i] = true;
                dfs(cnt + 1, destination, records + " " + destination);
                used[i] = false;
            }
        }
    }
}
