import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
프로그래머스 / 전력망을 둘로 나누기 / Level 2
https://school.programmers.co.kr/learn/courses/30/lessons/86971
 */
class PGS_86971 {

    private List<List<Integer>> adjList = new ArrayList<>();
    private int n;

    public int solution(int n, int[][] wires) {
        this.n = n;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i <= n; i++)
            adjList.add(new ArrayList<>());

        // 인접 리스트 세팅
        for (int[] w : wires) {
            adjList.get(w[0]).add(w[1]);
            adjList.get(w[1]).add(w[0]);
        }

        // 간선을 순회하며 제거 -> 원복
        for (int[] w : wires) {
            int a = w[0];
            int b = w[1];

            // 간선 제거
            adjList.get(a).remove((Integer) b);
            adjList.get(b).remove((Integer) a);

            // 서브트리 노드의 수 차이 갱신
            int aSize = bfs(a);
            int bSize = n - aSize;
            int diff = Math.abs(aSize - bSize);
            min = Math.min(min, diff);

            // 간선 원복
            adjList.get(a).add(b);
            adjList.get(b).add(a);
        }

        return min;
    }

    private int bfs(int node) {
        Queue<Integer> q = new LinkedList<>();
        q.add(node);
        boolean[] visited = new boolean[n + 1];
        visited[node] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : adjList.get(cur)) {
                if (!visited[next]) {
                    q.add(next);
                    visited[next] = true;
                    count++;
                }
            }
        }

        return count;
    }
}
