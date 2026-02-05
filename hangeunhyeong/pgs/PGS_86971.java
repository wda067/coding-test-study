package hangeunhyeong.pgs;
/*
PGS 86971 - 전력망 둘로 나누기
모든 전선들을 하나씩 끊어가며 최솟값을 구하는 방식인 완전탐색을 고려했을 때 시간복잡도는 O(n(n-1))이고 n은 100이하의 자연수이므로 가능
전력망에 속한 송전탑의 개수는 어떻게 셀 수 있을까? dfs or bfs
bfs : queue에서 송전탑을 뺄때마다 개수를 센다
dfs : sub전력망들의 송전탑개수의 합 + 1 = 현재전력망의 송전탑개수의 합 임을 이용해 재귀적으로 개수를 센다

 */
import java.util.*;
class PGS_86971 {
    public static int[][] graph;
    public static boolean[] visited;
    public int solution(int n, int[][] wires) {
        graph = new int[n + 1][n + 1];
        visited = new boolean[n + 1];
        // 그래프 설정
        for(int[] wire : wires){
            graph[wire[0]][wire[1]] = 1;
            graph[wire[1]][wire[0]] = 1;
        }
        int dif = 0;
        int min = n;
        for(int[] wire : wires){
            // 전력망 끊기
            graph[wire[0]][wire[1]] = 0;
            graph[wire[1]][wire[0]] = 0;
            // visited배열 초기화
            Arrays.fill(visited, false);
            dif = 0;
            // bfs 또는 dfs
            for(int i = 1; i <= n; i++){
                if(!visited[i]){
                    dif -= dfs(i);
                    dif = Math.max(dif, -dif);
                    // System.out.println(dif);
                }
            }
            min = Math.min(min, dif);
            // 원상복귀
            graph[wire[0]][wire[1]] = 1;
            graph[wire[1]][wire[0]] = 1;
        }
        return min;
    }
    public static int dfs(int n){
        int cnt = 1;
        visited[n] = true;
        for(int i = 1; i < visited.length; i++){
            if(!visited[i] && graph[n][i] == 1)
                cnt += dfs(i);
        }
        // System.out.println(cnt);
        return cnt;
    }
}