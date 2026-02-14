import java.util.*;


/*
[풀이]
- 매 초마다 A[r][c] == k인지 확인, 아니면 R(행>=열) / C(행<열) 연산 수행 (최대 100초)
- 한 행(또는 열)에서 0은 제외하고 숫자별 등장횟수 카운트
- (등장횟수, 숫자 오름차순)로 정렬 후 [숫자, 횟수] 형태로 다시 나열 (길이 100까지만)
- 모든 행/열은 가장 긴 길이에 맞춰 0으로 패딩
- C연산은 전치(transpose) -> R연산 -> 전치로 처리
*/
public class BOJ_17140 {

	static int r;
	static int c;
	static int k;
	static ArrayList<ArrayList<Integer>> map;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		r = sc.nextInt()-1;
		c = sc.nextInt()-1;
		k = sc.nextInt();
		map = new ArrayList<>();
		for(int i = 0; i < 3; i++){
			ArrayList<Integer> tmp = new ArrayList<>();
			for(int j = 0;j < 3; j++){
				tmp.add(sc.nextInt());
			}
			map.add(tmp);
		}
		int time = 0;
		while(time <= 100){
			if(isCheck()){
				System.out.println(time);
				return;
			}
			int R = map.size();
			int C = map.get(0).size();
			if(R >= C){
				rowSort();
			} else {
				colSort();
			}
			time++;
		}
		System.out.println(-1);
	}

	private static void rowSort(){
		int maxLen = 0;
		ArrayList<ArrayList<Integer>> newMap = new ArrayList<>();

		for(int i = 0; i < map.size(); i++){
			Map<Integer, Integer> countMap = new TreeMap<>();

			for(int num : map.get(i)){
				if(num != 0){
					countMap.put(num, countMap.getOrDefault(num, 0) + 1);
				}
			}

			List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(countMap.entrySet());
			sortedList.sort((a,b) -> {
				if(a.getValue() - b.getValue() != 0) return a.getValue() - b.getValue();
				return a.getKey() - b.getKey();
			});

			ArrayList<Integer> newRow = new ArrayList<>();
			for(Map.Entry<Integer, Integer> entry : sortedList){
				newRow.add(entry.getKey());
				newRow.add(entry.getValue());
				if(newRow.size() >= 100) break;
			}

			maxLen = Math.max(maxLen, newRow.size());
			newMap.add(newRow);
		}

		for(List<Integer> row : newMap){
			while(row.size() < maxLen){
				row.add(0);
			}
		}
		map = newMap;
	};

	private static void colSort(){
		transpose();
		rowSort();
		transpose();
	}

	private static boolean isCheck(){
		if(r >= map.size() || c >= map.get(0).size()) return false;
		return map.get(r).get(c) == k;
	}

	private static void transpose() {
		int cols = map.get(0).size();
		int rows = map.size();
		ArrayList<ArrayList<Integer>> newMap = new ArrayList<>();

		for(int i = 0; i < cols; i++){
			ArrayList<Integer> newCols = new ArrayList<>();
			for(int j = 0; j < rows; j++){
				int newCol = map.get(j).get(i);
				newCols.add(newCol);
			}
			newMap.add(newCols);
		}
		map = newMap;
	}
}

