package hangeunhyeong.pgs;

import java.util.*;
/*
PGS 43162 - 네트워크
 */
public class PGS_43162 {
    public boolean[] visited;
    public int[][] adjMatrix;
    public int N;
    public int solution(int n, int[][] computers) {
        visited = new boolean[n];
        adjMatrix = computers;
        N = n;
        int answer = 0;

        for(int i = 0; i < n; i++){
            if(!visited[i]){
                bfs(i);
                answer++;
            }
        }
        return answer;
    }
    public void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        while(!queue.isEmpty()){
            int computer = queue.poll();
            for(int i = 0; i < N; i++){
                //연결되어있지 않다면
                if(adjMatrix[computer][i] == 0)
                    continue;
                // i = computer일때도 방문처리가 이미 되어있으므로 여기서 걸러짐
                if(visited[i])
                    continue;
                queue.add(i);
                visited[i] = true;
            }
        }
    }
}
