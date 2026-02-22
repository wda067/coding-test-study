import java.util.*;
import java.lang.*;

/*
[input]
1. 알파벳 소문자 구성 W
2. 양수 K

[풀이]
(3.) 정확히 k개 포함하는 가장 짧은 연속 문자열
- O(N^2)은 어렵겠다 t 횟수도 고려해야되니까
- 알파벳 저장 자료구조, k 이하,동일,이상일 때 각각 로직
(4.) 초기 세팅은 3.과 동일
-
[output]
3.에 해당하는 문자열 길이
4.에 해당하는 문자열 길이
*/
public class BOJ_20437 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int t= sc.nextInt();
		for(int i = 0; i < t; i++){
			String w = sc.next();
			int k = sc.nextInt();
			int ans1 = solve1(w, k);
			int ans2 = solve2(w, k);
			if(ans1 == -1 || ans2 == -1){
				System.out.println(-1);
			}else {
				System.out.println(ans1 + " " + ans2);
			}
		}
	}

	static int solve1(String w, int k){
		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for(int i = 0; i < w.length(); i++){
			char c = w.charAt(i);
			if(!map.containsKey(c)){
				map.put(c, new ArrayList<>());
			}
			map.get(c).add(i);
		}
		int res = Integer.MAX_VALUE;
		// k개 개수만큼
		for(var li : map.values()){
			if(li.size() < k) continue;
			else if(li.size() == k){
				int sIdx = li.get(0);
				int eIdx = li.get(li.size()-1);
				res = Math.min(res, eIdx-sIdx+1);
			} else {
				int s = 0; int e = k-1;
				while(e < li.size()){
					int sIdx = li.get(s);
					int eIdx = li.get(e);
					res = Math.min(res, eIdx-sIdx+1);
					s++; e++;
				}
			}
		}
		return res != Integer.MAX_VALUE ? res : -1;
	}

	static int solve2(String w, int k){
		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for(int i = 0; i < w.length(); i++){
			char c = w.charAt(i);
			if(!map.containsKey(c)){
				map.put(c, new ArrayList<>());
			}
			map.get(c).add(i);
		}
		int res = -1;
		// k개 개수만큼
		for(var li : map.values()){
			if(li.size() < k) continue;
			else if(li.size() == k){
				int sIdx = li.get(0);
				int eIdx = li.get(li.size()-1);
				if(w.charAt(sIdx) != w.charAt(eIdx)) continue;
				res = Math.max(res, eIdx-sIdx+1);
			} else {
				int s = 0; int e = k-1;
				while(e < li.size()){
					int sIdx = li.get(s);
					int eIdx = li.get(e);
					if(w.charAt(sIdx) != w.charAt(eIdx)) continue;
					res = Math.max(res, eIdx-sIdx+1);
					s++; e++;
				}
			}
		}
		return res != -1 ? res : -1;
	}
}
