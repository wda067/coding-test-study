/*

[풀이]
- 길이가 짧은 것부터 순차적으로 넣어야됨 -> {} 규칙 활용해서
    - 이미 포함되어 있는 건 제외 -> HashSet

*/
import java.util.*;

public class PSG_64065 {

	static ArrayList<ArrayList<Integer>> list = new ArrayList<>();

	public int[] solution(String s) {
		int[] answer = {};
		StringBuilder sb = new StringBuilder(s);
		HashSet<Integer> set = new HashSet<>();

		sb.deleteCharAt(0);
		sb.deleteCharAt(sb.length()-1);

		ArrayList<Integer> numList = new ArrayList<>();
		int cur = 0;
		boolean inNumber = false;
		for(char c : sb.toString().toCharArray()){
			if(c >= '0' && c <= '9'){
				cur = cur * 10 + (c-'0');
				inNumber = true;
				continue;
			}

			if(inNumber){
				numList.add(cur);
				cur = 0;
				inNumber = false;
			}
			if(c == '}'){
				list.add(numList);
				numList = new ArrayList<>();
			}
		}
		Collections.sort(list, (a,b)->{
			return a.size() - b.size();
		});

		ArrayList<Integer> resList = new ArrayList<>();
		for(var curList : list){
			for(int curNum : curList){
				if(set.contains(curNum)) continue;
				set.add(curNum);
				resList.add(curNum);
			}
		}


		return resList.stream().mapToInt(i->i).toArray();
	}
}
