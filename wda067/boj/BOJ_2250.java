import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
백준 / 트리의 높이와 너비 / 골드2
https://www.acmicpc.net/problem/2250
 */
public class BOJ_2250 {

    private static List<int[]> adjList = new ArrayList<>();         // 자식 노드 저장
    private static List<List<Integer>> depths = new ArrayList<>();  // 노드의 깊이 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());  // 노드 개수

        for (int i = 0; i <= N; i++) {
            adjList.add(new int[2]);
        }

        for (int i = 0; i <= N; i++) {
            depths.add(new ArrayList<>());
        }

        int[] parent = new int[N + 1];  // 각 노드의 부모 저장

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());  // 노드 번호
            int b = Integer.parseInt(st.nextToken());  // 왼쪽 자식
            int c = Integer.parseInt(st.nextToken());  // 오른쪽 자식

            int[] child = adjList.get(a);
            child[0] = b;
            child[1] = c;

            if (b != -1) {
                parent[b] = a;
            }
            if (c != -1) {
                parent[c] = a;
            }
        }

        // 루트 찾기
        int root = 1;
        for (int i = 1; i <= N; i++) {
            if (parent[i] == 0) {
                root = i;
                break;
            }
        }

        List<Integer> order = new ArrayList<>();  // 노드 방문 순서
        dfs(root, order, 1);

        // 노드 번호 -> 방문 순서 매핑
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int node = order.get(i);
            map.put(node, i + 1);
        }

        int level = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            List<Integer> cur = depths.get(i);  // i번 노드의 깊이
            if (cur.isEmpty()) {
                break;
            }

            int first = cur.get(0);                             // 첫번째 노드
            int last = cur.get(cur.size() - 1);                 // 마지막 노드
            int width = map.get(last) - map.get(first) + 1;     // 둘의 너비

            if (width > max) {
                max = width;
                level = i;
            }
        }

        System.out.println(level + " " + max);
    }

    private static void dfs(int node, List<Integer> order, int depth) {
        int[] child = adjList.get(node);
        int left = child[0];
        int right = child[1];

        // 중위 순회: 왼쪽 -> 자기 자신 -> 오른쪽

        // 왼쪽 서브트리 방문
        if (left != -1) {
            dfs(left, order, depth + 1);
        }

        // 현재 노드 방문
        order.add(node);

        // 현재 depth에 현재 노드 저장
        depths.get(depth).add(node);

        // 오른쪽 서브트리 방문
        if (right != -1) {
            dfs(right, order, depth + 1);
        }
    }
}
