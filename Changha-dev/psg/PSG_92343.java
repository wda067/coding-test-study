/*
- info : 각 노드가 양 or 늑대 인지
- 0 : 양, 1 : 늑대
- 0번 노드 == 루트 노드

[풀이]
int 양의 갯수, int 늑대 갯수
트리 관계 형성 자료구조..
hashMap<Integer, List<Integer>> : 부모, 자식 노드
현재 리스트 복사 해서 다음 노드들 추가 하는 부분이 포인트

[output]
모을 수 있는 최대 양 몇마리

*/
import java.util.*;
class PSG_92343 {
	static HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
	static int[] info;
	static int answer = 0;
	public int solution(int[] info, int[][] edges) {
		this.info = info;
		for(int[] edge : edges){
			if(!map.containsKey(edge[0])){
				map.put(edge[0], new ArrayList<>());
			}
			map.get(edge[0]).add(edge[1]);
		}
		dfs(0, 0, 0, new ArrayList<>());

		return answer;
	}
	static void dfs(int sheepCnt, int wolfCnt, int curIdx, List<Integer> nexts){
		if(info[curIdx] == 0) sheepCnt+=1;
		else wolfCnt+=1;

		if(wolfCnt >= sheepCnt) return;
		answer = Math.max(answer, sheepCnt);

		List<Integer> tmp = new ArrayList<>(nexts);
		tmp.remove(Integer.valueOf(curIdx));

		if(map.containsKey(curIdx)){
			tmp.addAll(map.get(curIdx));
		}

		for(int nxtIdx : tmp){
			dfs(sheepCnt, wolfCnt, nxtIdx, tmp);
		}
	}
}
