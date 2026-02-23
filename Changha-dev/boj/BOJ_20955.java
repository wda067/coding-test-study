import java.util.*;
import java.lang.*;
import java.io.*;

/*
[풀이]
- 일단 연결 정보를 저장 -> ArrayList[]
- 트리 조건
    - 순환 고리 x
    - bfs시 모든 노드 방문
- 그냥 chunk-1이 답이 아닌 이유가 뭐지
    - 내부 사이클도 없애야 하니까
    - 간선개수를 n-k개로 줄이는것 + chunk 연결 (이해가 쉽지 않았음..)
[output]
- 트리 형태로 하기 위한 최소 연산 횟수
*/
class BOJ_20955 {
	static ArrayList<Integer>[] list;
	static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		list = new ArrayList[n+1];
		for(int i = 1; i <= n; i++){
			list[i] = new ArrayList<Integer>();
		}
		for(int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			list[u].add(v);
			list[v].add(u);
		}
		visited = new boolean[n+1];
		int chunk = 0;
		for(int i = 1; i <=n; i++){
			if(visited[i])continue;
			bfs(i);
			chunk+=1;
		}
		int cycle = m - (n - chunk); // 여분 간선 = 끊어야 할 최소 횟수
		System.out.println(cycle + (chunk - 1)); // 끊기 + 연결하기
	}
	static void bfs(int start){
		var q = new LinkedList<Integer>();
		visited[start] = true;
		q.add(start);
		while(!q.isEmpty()){
			int cur = q.poll();
			for(int next : list[cur]){
				if(visited[next]) continue;
				visited[next] = true;
				q.add(next);
			}
		}
	}
}
