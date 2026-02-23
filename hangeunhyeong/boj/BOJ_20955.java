package hangeunhyeong.boj;
/*
BOJ 20955 - 민서의 응급수술
[문제풀이]
1. 연결그래프 내에 있는 사이클 제거
    - 사이클이 있는 경우 : E > V - 1
    - 사이클이 없는 경우 연산횟수) E = V - 1
    - 연산횟수) Σ(E_i - (V_i - 1))
2. 연결그래프끼리 연결
    - 연산횟수) k - 1   (k는 연결그래프 개수를 의미)
=>  Σ{E_i - (V_i - 1)} + k - 1 = M - N + 2*k - 1
 */
import java.io.*;
import java.util.*;
public class BOJ_20955 {
    public static ArrayList<Integer>[] edges;
    public static void main(String[] args) throws IOException {
        int N, M, k = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        visited = new boolean[N + 1];
        edges = new ArrayList[N + 1];
        for(int i = 0; i <= N; i++)
            edges[i] = new ArrayList<Integer>();

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            edges[u].add(v);
            edges[v].add(u);
        }
        // k 구하기
        for(int i = 1; i <= N; i++){
            if(!visited[i]){
//                bfs(i);
                dfs(i);
                k++;
            }
        }
        System.out.println(M - N + 2*k - 1);
    }
    public static boolean[] visited;
    public static void bfs(int n){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(n);
        while(!queue.isEmpty()){
            int i = queue.poll();
            visited[i] = true;
            for(int next : edges[i]){
                if(!visited[next])
                    queue.add(next);
            }
        }
    }
    public static void dfs(int n){
        visited[n] = true;
//        System.out.println(n);
        for(int i : edges[n]){
            if(!visited[i])
                dfs(i);
        }

    }
}
