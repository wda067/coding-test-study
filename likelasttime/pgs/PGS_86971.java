import java.util.*;
/*
    n개의 송전탑이 전선을 통해 하나의 트리로 연결되어있다.
    전선들 중 하나를 끊어서 현재의 전력망 네트워크를 2개로 분할한다.
    두 전력망이 갖게 되는 송전탑의 개수를 최대한 비슷하게 맞추자.
    두 전력망이 가지고 있는 송전탑 개수의 차이 절대값을 구하자.
*/
class PGS_86971 {
    static List<Integer>[] tree;

    // start에서 시작해서, (cutA - cutB) 간선을 끊은 상태로 컴포넌트 크기 계산
    static int bfs(int start, int cutA, int cutB, int n) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visit = new boolean[n + 1];

        q.offer(start);
        visit[start] = true;

        int cnt = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();
            cnt++;

            for (int next : tree[cur]) {
                if (visit[next]) continue;

                // 끊은 간선만 제외
                if ((cur == cutA && next == cutB) ||
                        (cur == cutB && next == cutA)) {
                    continue;
                }

                visit[next] = true;
                q.offer(next);
            }
        }
        return cnt;
    }

    public int solution(int n, int[][] wires) {
        // 인접 리스트 구성
        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int[] w : wires) {
            int a = w[0];
            int b = w[1];
            tree[a].add(b);
            tree[b].add(a);
        }

        int answer = Integer.MAX_VALUE;

        // 각 전선을 하나씩 끊어본다
        for (int[] w : wires) {
            int a = w[0];
            int b = w[1];

            // a 쪽 컴포넌트 크기
            int cnt = bfs(a, a, b, n);

            // 두 컴포넌트 크기 차이
            int diff = Math.abs(n - 2 * cnt);
            answer = Math.min(answer, diff);
        }

        return answer;
    }
}
