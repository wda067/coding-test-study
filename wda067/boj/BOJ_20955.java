import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
백준 / 민서의 응급 수술 / 골드4
https://www.acmicpc.net/problem/20955
 */
public class BOJ_20955 {

    private static int[] parent;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            parent[i] = i;
        }

        int cycle = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 이미 부모가 같을 경우 다시 연결하면 사이클이 생성됨
            if (check(u, v)) {
                cycle++;
            } else {
                union(u, v);
            }
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= N; i++) {
            set.add(find(i));
        }

        // 끊어야 할 간선 수 + 추가해야 할 간선 수
        System.out.println(cycle + set.size() - 1);
    }

    private static int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    private static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x < y) {
            parent[y] = x;
        } else if (x > y) {
            parent[x] = y;
        }
    }

    private static boolean check(int x, int y) {
        return find(x) == find(y);
    }
}
