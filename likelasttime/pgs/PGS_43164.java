import java.util.*;

class PGS_43164 {
    boolean[] visited;
    List<String> answers;
    public String[] solution(String[][] tickets) {
        answers=new LinkedList<>();
        visited=new boolean[tickets.length];
        dfs(tickets, "ICN", "ICN", 0); // 티켓 배열, 출발, 경로, 갯수
        Collections.sort(answers);
        String[] ans=answers.get(0).split(" ");
        return ans;
    }

    public void dfs(String[][] tickets, String start, String str, int cnt){
        if(cnt == tickets.length){
            answers.add(str);
            return;
        }
        for(int i=0; i<tickets.length; i++){
            if(visited[i]) continue;
            if(start.equals(tickets[i][0])){
                visited[i]=true;
                dfs(tickets, tickets[i][1], str+" "+tickets[i][1], cnt+1);
                visited[i]=false;
            }

        }
    }
}