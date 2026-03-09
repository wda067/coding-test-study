/*
가능한 경로가 2개 이상일 경우
- 알파벳 순서가 앞서는 경로로 return -> PriorityQueue

*/
import java.util.*;

public class PSG_43164 {
	static LinkedList<String> answer = new LinkedList<>();
	static HashMap<String, PriorityQueue<String>> map = new HashMap<>();
	public String[] solution(String[][] tickets) {

		int n = tickets.length;
		for(String[] ticket : tickets){
			String a = ticket[0];
			String b = ticket[1];
			if(!map.containsKey(a)){
				map.put(a, new PriorityQueue<>());
			}
			var cur = map.get(a);
			cur.add(b);
			map.put(a, cur);
		}

		dfs("ICN");


		return answer.toArray(new String[0]);
	}

	static void dfs(String str){
		var cur = map.get(str);

		while(cur != null && !cur.isEmpty()){
			String next = cur.poll();
			dfs(next);
		}
		answer.addFirst(str);
	}
}