import java.util.*;

/*
    이진 트리 모양의 각 노드에 늑대와 양이 한 마리씩 놓여 있다.
    루트 노드에서 출발하여 각 노드를 돌아다니며 양을 모으려 한다.
    각 노드를 방문할 때 마다 해당 노드에 있던 양과 늑대가 따라온다.
    모은 양의 수보다 늑대의 수가 같거나 더 많아지면 모든 양을 잡아먹는다.
    최대한 많은 수의 양을 모아서 다시 루트 노드로 돌아가기

    [풀이]
        리스트 배열에 자식 노드를 저장
        dfs를 이용한 탐색
*/
class Solution {
    final static int WOLF = 1;
    final static int SHEEP = 0;

    static List<Integer>[] tree; // 자식 리스트
    static int[] arr;            // info 배열
    static int answer;      // 모은 양의 수(최대)

    public void dfs(int wolf, int sheep, List<Integer> candidates) {
        answer = Math.max(answer, sheep);       // 모은 양의 수 갱신

        for (int i = 0; i < candidates.size(); i++) {
            int cur = candidates.get(i);        // 현재 노드

            int nextWolf = wolf + (arr[cur] == WOLF ? 1 : 0);   // 늑대 수
            int nextSheep = sheep + (arr[cur] == SHEEP ? 1 : 0);    // 양의 수

            if (nextWolf >= nextSheep) continue;    // 늑대가 양의 수보다 더 많거나 같다면

            List<Integer> next = new ArrayList<>(candidates);
            next.remove(i);
            next.addAll(tree[cur]);     // 현재 노드 cur의 자식 노드들을 추가

            dfs(nextWolf, nextSheep, next);
        }
    }

    public int solution(int[] info, int[][] edges) {
        int n = info.length;        // 노드의 수
        arr = info;
        answer = 0;

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();

        for (int[] edge : edges) {
            // 부모 edge[0] 노드에 자식 노드 edge[1]을 추가
            tree[edge[0]].add(edge[1]);
        }

        List<Integer> start = new ArrayList<>();
        start.add(0); // 루트부터 시작

        dfs(0, 0, start);
        return answer;
    }
}
