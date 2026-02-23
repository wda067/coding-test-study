import java.util.ArrayList;
import java.util.List;

/*
프로그래머스 / 양과 늑대 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/92343
 */
class PGS_92343 {

    private int N, max;
    private List<List<Integer>> adjList = new ArrayList<>();
    private int[] info;
    private boolean[] selected;  // 현재 상태에서 선택된 노드

    public int solution(int[] info, int[][] edges) {
        N = info.length;
        this.info = info;

        for (int i = 0; i < N; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
        }

        selected = new boolean[N];
        selected[0] = true;  // 루트 노드 선택
        dfs(1, 0);

        return max;
    }

    private void dfs(int sheep, int wolf) {
        if (sheep <= wolf) {  // 양이 늑대보다 작거나 같으면 중지
            return;
        }

        // 선택된 모든 노드들의 자식들을 탐색한다.
        // -> 현재 상태로 가능한 모든 경로 탐색
        for (int cur = 0; cur < N; cur++) {
            if (selected[cur]) {
                // 자식 노드 탐색
                for (int next : adjList.get(cur)) {
                    if (!selected[next]) {
                        selected[next] = true;

                        if (info[next] == 0) {
                            dfs(sheep + 1, wolf);  // 양 카운트
                        } else {
                            dfs(sheep, wolf + 1);  // 늑대 카운트
                        }

                        selected[next] = false;  // 백트래킹
                    }
                }
            }
        }

        max = Math.max(max, sheep);
    }
}
