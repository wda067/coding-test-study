import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
프로그래머스 / 등산코스 정하기 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/118669
 */
class PGS_118669 {

    private List<List<Edge>> adjList = new ArrayList<>();
    private int n;
    private int[] dist, gates;
    private boolean[] isGate, isSummit;

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        this.n = n;
        this.gates = gates;

        // dist[i]: i까지 가는 경로에 포함된 최대 가중치가 최소가 되는 값
        dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // 출입구/산봉우리 여부 저장
        isGate = new boolean[n + 1];
        isSummit = new boolean[n + 1];
        for (int g : gates) isGate[g] = true;
        for (int s : summits) isSummit[s] = true;

        for (int i = 0; i <= n; i++) adjList.add(new ArrayList<>());

        for (int[] p : paths) {
            int u = p[0], v = p[1], w = p[2];
            adjList.get(u).add(new Edge(v, w));
            adjList.get(v).add(new Edge(u, w));
        }

        dijkstra();

        // 작은 산봉우리부터 탐색
        Arrays.sort(summits);
        int bestSummit = summits[0];
        int bestW = dist[bestSummit];

        // 해당 산봉우리까지의 경로 중 최소 intensity를 가지는 산봉우리 탐색
        for (int s : summits) {
            if (dist[s] < bestW) {
                bestW = dist[s];
                bestSummit = s;
            }
        }

        return new int[]{bestSummit, bestW};
    }

    private void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.w));

        // 출입구 모두 삽입
        for (int g : gates) {
            dist[g] = 0;
            pq.add(new Edge(g, 0));
        }

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            int from = cur.to;
            int w = cur.w;

            // 현재 노드까지의 기존 비용보다 클 경우 패스
            // 1 ->(3) 2
            // 3 ->(5) 2
            // 일 경우 두번째는 생각할 필요 없음
            if (w > dist[from]) continue;

            // 산봉우리에 도착하면 해당 경로 종료
            // 다시 돌아가는 경우는 생각할 필요 없음 (이미 최적화된 편도 경로)
            if (isSummit[from]) continue;

            for (Edge next : adjList.get(from)) {
                int to = next.to;

                // 중간에 출입구로 들어가면 안됨
                if (isGate[to]) continue;

                int newW = Math.max(w, next.w);
                if (newW < dist[to]) {
                    dist[to] = newW;
                    pq.add(new Edge(to, newW));
                }
            }
        }
    }

    private static class Edge {
        int to, w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }
}
