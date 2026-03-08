import java.util.*;

class PSG_43162 {
	private static ArrayList<Integer>[] arr;
	private static boolean[] visited;
	private static int res = 0;
	public int solution(int n, int[][] computers) {
		arr = new ArrayList[n];
		for(int i = 0; i < n; i++){
			arr[i] = new ArrayList<>();
		}
		visited = new boolean[n];
		for(int i = 0; i < computers.length; i++){
			int[] cur = computers[i];
			for(int j = 0; j < cur.length; j++){
				if(i == j) continue;
				if(cur[j] == 1) arr[i].add(j);
			}
		}
		for(int i = 0; i < n; i++){
			if(visited[i]) continue;
			bfs(i);
			res++;
		}
		return res;
	}

	private static void bfs(int start){
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		visited[start] = true;
		while(!q.isEmpty()){
			int cur = q.poll();

			for(int n : arr[cur]){
				if(visited[n]) continue;
				visited[n] = true;
				q.add(n);
			}
		}
	}
}