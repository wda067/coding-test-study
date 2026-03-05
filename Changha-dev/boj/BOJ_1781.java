import java.util.*;
import java.lang.*;
import java.io.*;

/*
N : 숙제의 개수

정렬
- 데드라인 ASC
- 컵라면 수 DESC

잘못한 것
- PQ는 POLL시 정렬한다.
- 현재 방식은 최적이 안되는 경우가 존재함
3
1 1
2 100
2 99
인 경우 (1,1)+(2,100)이 아니라 (2,100)+(2,99)여야 함
[결과]
동호가 받을 수 있는 최대 컵라면 수
*/
import java.util.*;
import java.lang.*;
import java.io.*;

/*
N : 숙제의 개수

정렬
- 데드라인 ASC
- 컵라면 수 DESC

잘못한 것
- PQ는 POLL시 정렬한다.
- 현재 방식은 최적이 안되는 경우가 존재함
3
1 1
2 100
2 99
인 경우 (1,1)+(2,100)이 아니라 (2,100)+(2,99)여야 함

[다시 푼 방식]
deadline 오름차순
데드라인 내에서 개수만큼 최댓값 고르면됨

[결과]
동호가 받을 수 있는 최대 컵라면 수
*/
public class BOJ_1781 {
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		List<int[]> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			list.add(new int[]{d, r});
		}

		list.sort(Comparator.comparingInt(a -> a[0]));
		var minHeap = new PriorityQueue<Integer>();
		for(int[] cur : list){
			int d = cur[0], r = cur[1];
			minHeap.add(r);
			if(minHeap.size() > d) minHeap.poll();
		}

		long ans = 0;
		while(!minHeap.isEmpty()) ans += minHeap.poll();

		System.out.println(ans);
	}
}

// public class BOJ_1781 {
// 	public static void main(String[] args) throws IOException {
// 		var br = new BufferedReader(new InputStreamReader(System.in));
// 		var st = new StringTokenizer(br.readLine());
// 		int n = Integer.parseInt(st.nextToken());
// 		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
// 			int comp1 = a[0] - b[0];
// 			if(comp1 == 0){
// 				return b[1] - a[1];
// 			}
// 			return comp1;
// 		});
//
// 		for(int i = 1; i <= n; i++){
// 			st = new StringTokenizer(br.readLine());
// 			int a = Integer.parseInt(st.nextToken());
// 			int b = Integer.parseInt(st.nextToken());
// 			pq.add(new int[]{a,b,i});
// 		}
// 		int curDeadLine = 1;
// 		int ans = 0;
// 		while(!pq.isEmpty()){
// 			int[] cur = pq.poll();
// 			if(curDeadLine <= cur[0]){
// 				ans += cur[1];
// 				curDeadLine++;
// 			}
// 		}
//
// 		System.out.println(ans);
// 	}
// }
