/*
    루트 노드가 설정되지 않은 1개 이상의 트리가 있다.
    모든 노드들은 서로 다른 번호를 가지고 있다.
    각 노드는 홀수 노드, 짝수 노드, 역홀수 노드, 역짝수 노드 중 하나다.
    0은 짝수다.
    홀수 노드:
        노드의 번호가 홀수, 자식 노드의 개수가 홀수인 노드
    짝수 노드:
        노드의 번호가 짝수, 자식 노드의 개수가 짝수인 노드
    역홀수 노드:
        노드의 번호가 홀수이며 자식 노드의 개수가 짝수인 노드
    역짝수 노드:
        노드의 번호가 짝수이며 자식 노드의 개수가 홀수인 노드
    홀짝 트리:
        홀수 노드와 짝수 노드로만 이루어진 트리
    역홀짝 트리:
        역홀수 노드와 역짝수 노드로만 이루어진 트리
    홀짝 트리가 될 수 있는 트리의 개수, 역홀짝 트리가 될수 있는 트리의 개수를 구한다.
*/
import java.util.*;

class PGS_388354 {

    static List<Integer>[] graph;
    static boolean[] visited;

    public int[] solution(int[] nodes, int[][] edges) {

        int max = 0;
        for (int node : nodes)
            max = Math.max(max, node);

        graph = new ArrayList[max + 1];
        visited = new boolean[max + 1];

        for (int i = 0; i <= max; i++)
            graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int oddEvenTreeCount = 0;
        int reverseTreeCount = 0;

        for (int start : nodes) {
            if (visited[start]) continue;

            Queue<Integer> q = new LinkedList<>();
            List<Integer> comp = new ArrayList<>();

            q.add(start);
            visited[start] = true;

            int edgeCount = 0;

            while (!q.isEmpty()) {
                int cur = q.poll();
                comp.add(cur);
                edgeCount += graph[cur].size();

                for (int next : graph[cur]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        q.add(next);
                    }
                }
            }

            edgeCount /= 2;

            // 트리 아니면 패스
            if (edgeCount != comp.size() - 1)
                continue;

            int equalCount = 0;
            int diffCount = 0;

            for (int node : comp) {
                int degree = graph[node].size();

                boolean nodeOdd = (node % 2 == 1);
                boolean degreeOdd = (degree % 2 == 1);

                if (nodeOdd == degreeOdd)
                    equalCount++;
                else
                    diffCount++;
            }

            if (equalCount == 1)
                oddEvenTreeCount++;

            if (diffCount == 1)
                reverseTreeCount++;
        }

        return new int[]{oddEvenTreeCount, reverseTreeCount};
    }
}