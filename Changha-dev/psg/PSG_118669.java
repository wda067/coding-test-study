/*
출입구 -> 산봉우리 1 -> 출입구

휴식없이 이동해야 하는 시간 중 가장 긴 시간 intensity

- 원래의 출입구로 돌아와야 됨
    - 탐색 -> 조건 만족 -> 원래 출입구이면 return;
- 생각못함 => 다익스트라
    - dist[next] = dist[cur] + cost;

[output]
intensity 최소
*/
import java.util.*;
public class PSG_118669 {
	class Node {
		int num;
		int cost;
		public Node(int num, int cost){
			this.num = num;
			this.cost = cost;
		}
	}
	static Queue<int[]> answer;
	static ArrayList<int[]>[] list;
	static HashSet<Integer> summitSet = new HashSet<>();
	static HashSet<Integer> gateSet = new HashSet<>();
	public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
		list = new ArrayList[n+1];
		answer = new PriorityQueue<>((a,b) -> {
			int comp1 = a[1] - b[1];
			if(comp1 == 0){
				int comp2 = a[0] - b[0];
				return comp2;
			}
			return comp1;
		});
		for(int gate : gates){
			gateSet.add(gate);
		}
		for(int summit : summits){
			summitSet.add(summit);
		}
		for(int i = 0; i <= n; i++){
			list[i] = new ArrayList<>();
		}
		for(int[] path : paths){
			list[path[0]].add(new int[]{path[1], path[2]});
			list[path[1]].add(new int[]{path[0], path[2]});
		}
		int[] intensity = new int[n+1];
		Arrays.fill(intensity, Integer.MAX_VALUE);
		var q = new PriorityQueue<Node>((a,b) -> a.cost - b.cost);
		for(int gate : gates){
			intensity[gate] = 0;
			q.add(new Node(gate, 0));
		}
		while(!q.isEmpty()){
			Node cur = q.poll();
			if(cur.cost > intensity[cur.num]) continue;
			if(summitSet.contains(cur.num)) continue;

			for(int[] nxt : list[cur.num]){
				int next = nxt[0];
				int weight = nxt[1];
				int nxtIntensity = Math.max(weight, cur.cost);
				if(intensity[next] > nxtIntensity){
					intensity[next] = nxtIntensity;
					q.add(new Node(next, nxtIntensity));
				}
			}
		}
		int minSummit = 0;
		int minIntensity = Integer.MAX_VALUE;
		Arrays.sort(summits);
		for(int summit : summits){
			int curIntensity = intensity[summit];
			if(minIntensity > curIntensity){
				minSummit = summit;
				minIntensity = curIntensity;
			}
		}
		return new int[]{minSummit, minIntensity};
	}
}