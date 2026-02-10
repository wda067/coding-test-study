package hangeunhyeong.pgs;
/*
PGS 118669 - 등산코스 정하기
완전탐색 : 시간복잡도 O(gates 길이 * summits 길이) 즉, O(n^2)을 의미
그리디 : 탐욕해 != 최적해 => X
DP : 출입구에서 시작해서 max(최솟값, 가중치)가 가려는 노드의 최솟값보다 작으면 갱신하는 방식
그래프 표현 방식 : 인접행렬, 인접리스트
- 인접행렬 : 그래프 탐색시 간선으로 연결되지 않은 노드들도 연결여부를 확인해 시간복잡도가 O(n^2)이 되고 공간복잡도 또한 O(n^2)이 된다
- 인접리스트 : 그래프 탐색시 간선으로 연결된 노드들만 확인하므로 시간복잡도가 O(n + m)이고 공간복잡도는 O(2*m)이 된다.
 */
import java.util.*;
class PGS_118669 {
    class Edge{
        int w, to;  // 가중치, 연결된 노드번호
        public Edge(int to, int w){
            this.to = to;
            this.w = w;
        }
    }
    // 인접리스트로 그래프 표현
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        ArrayList<Edge>[] graph = new ArrayList[n + 1];
        // 인접리스트 초기화
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<Edge>();
        }
        // 간선을 인접리스트에 추가
        for(int[] path : paths){
            int node1 = path[0];
            int node2 = path[1];
            int distance = path[2];
            graph[node1].add(new Edge(node2, distance));
            graph[node2].add(new Edge(node1, distance));
        }

        int INF = 10_000_000;
        int[] dp = new int[n + 1];  // 지나온 간선 가중치들의 최댓값
        Arrays.fill(dp, INF);
        // 봉우리인지 확인하는 배열
        boolean[] isSummit = new boolean[n + 1];
        for(int summit : summits){
            isSummit[summit] = true;
        }
        // 큐에 출입구 추가, dp초기값 세팅
        Queue<Integer> queue = new LinkedList<>();
        for(int gate : gates){
            queue.add(gate);
            dp[gate] = 0;
        }

        while(!queue.isEmpty()){
            int current = queue.poll();
            // 현재 방문한 노드가 산봉우리이면 더이상 등산로를 갈 필요가 없음(등산로를 더 가면 intensity가 증가할 위험만 존재)
            if(isSummit[current])
                continue;
            // 연결된 노드들 방문
            for(Edge edge : graph[current]){
                int next = edge.to; // 연결된 노드번호
                int distance = edge.w;  // 가중치
                int intensity = Math.max(dp[current], distance);    // 지나온 간선 가중치들의 최댓값
                // 현재 노드에서 갈 수 있고 가려는 경로의 intensity가 next 노드의 intensity보다 작은 경우에만 방문
                if(distance > 0 && intensity < dp[next]){
                    dp[next] = intensity;
                    queue.add(next);
                }
            }
        }
        // for(int summit : summits){
        //     System.out.printf("dp[%d] : %d\n", summit, dp[summit]);
        // }
        int[] answer = new int[2];
        answer[0] = -1; // 봉우리 번호
        answer[1] = INF + 1;    // 최소 intensity
        for(int summit : summits){
            if(answer[1] > dp[summit]){
                answer[0] = summit;
                answer[1] = dp[summit];
            }
            else if(answer[1] == dp[summit] && summit < answer[0]){
                answer[0] = summit;
            }
        }
        return answer;
    }
    // 인접행렬로 그래프 표현
    public int[] solution2(int n, int[][] paths, int[] gates, int[] summits) {
        int[][] graph = new int[n + 1][n + 1];
        for(int[] path : paths){
            int node1 = path[0];
            int node2 = path[1];
            int distance = path[2];
            graph[node1][node2] = distance;
            graph[node2][node1] = distance;
        }

        int INF = 10_000_000;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        // 봉우리인지 확인하는 배열
        boolean[] isSummit = new boolean[n + 1];
        for(int summit : summits){
            isSummit[summit] = true;
        }
        // 큐에 출입구 추가, dp초기값 세팅
        Queue<Integer> queue = new LinkedList<>();
        for(int gate : gates){
            queue.add(gate);
            dp[gate] = 0;
        }

        while(!queue.isEmpty()){
            int current = queue.poll();
            // 현재 방문한 노드가 산봉우리이면
            if(isSummit[current])
                continue;
            for(int next = 1; next <= n; next++){
                // 자기자신을 방문한 경우
                if(next == current)
                    continue;
                int distance = graph[current][next];
                int intensity = Math.max(dp[current], distance);
                // 현재 노드에서 갈 수 있고 가려는 경로의 intensity가 next 노드의 intensity 최솟값보다 작은 경우에만 방문
                if(distance > 0 && intensity < dp[next]){
                    dp[next] = intensity;
                    queue.add(next);
                }
            }
        }
        // for(int summit : summits){
        //     System.out.printf("dp[%d] : %d\n", summit, dp[summit]);
        // }
        int[] answer = new int[2];
        answer[0] = -1;
        answer[1] = INF + 1;
        for(int summit : summits){
            if(answer[1] > dp[summit]){
                answer[0] = summit;
                answer[1] = dp[summit];
            }
            else if(answer[1] == dp[summit] && summit < answer[0]){
                answer[0] = summit;
            }
        }
        return answer;
    }
}
