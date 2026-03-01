import java.util.*;
import java.io.*;

/*
 * 트리는 사이클이 존재하지 않는 연결 그래프
 * 연결되지 않은 두 뉴런을 연결하거나 이미 연결된 두 뉴런의 연결을 끊는다.
 * 모든 뉴런을 하나의 트리 형태로 연결하기 위해 필요한 최소 연산 횟수를 구하기
 * */
class BOJ_20955 {
    static int N, M;
    static int[] parent;

    static int find(int x) {
        if (x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa != pb) parent[pb] = pa;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) parent[i] = i;

        int answer = 0; // 최소 연산 횟수

        // 간선 처리
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 사이클이면 제거 필요
            if (find(u) == find(v)) {
                answer++;
            } else {
                union(u, v);
            }
        }

        // 컴포넌트 개수 계산
        Set<Integer> components = new HashSet<>();
        for (int i = 1; i <= N; i++) {
            components.add(find(i));
        }

        // 컴포넌트 연결 비용
        answer += components.size() - 1;

        System.out.println(answer);
    }
}