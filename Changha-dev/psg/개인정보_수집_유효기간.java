/*
1~n 번으로 분류된 개인정보 n

모든 달 : 28일까지
28*12일이 존재

1. todays -> days로 치환
2. terms 저장할 자료구조 필요 -> hashmap
3. privacies 순회(idx 필요하므로 for i)하면서 조건에 부합하면 answerList에 넣기

[output]
파기해야 될 개인정보 번호 오름차순으로
*/
import java.util.*;

class 개인정보_수집_유효기간 {
	static HashMap<String, Integer> termMap = new HashMap<>();
	static int bnd;
	public int[] solution(String today, String[] terms, String[] privacies) {

		int[] answer = new int[1];
		var answerList = new ArrayList<Integer>();
		bnd = toDays(today);
		for(String term : terms){
			String[] spl = term.split(" ");
			String a = spl[0];
			int b = Integer.parseInt(spl[1]);
			termMap.put(a, b);
		}

		// 개인정보 돌아가면서 확인해보기
		for(int i = 0; i < privacies.length; i++){
			String privacy = privacies[i];
			String[] spl = privacy.split(" ");
			int cur = toDays(spl[0]);
			int more = termMap.get(spl[1]) * 28;
			if(bnd >= cur+more){
				answerList.add(i+1);
			}
		}

		return answerList.stream().mapToInt(i->i).toArray();
	}

	static int toDays(String s){
		String[] spl = s.split("\\.");
		int y = (Integer.parseInt(spl[0]) - 2000) * 12 * 28;
		int m = Integer.parseInt(spl[1]) * 28;
		int d = Integer.parseInt(spl[2]);
		return y+m+d;
	}
}