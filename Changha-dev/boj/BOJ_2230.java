import java.util.*;
import java.lang.*;
import java.io.*;

/*
[풀이]
- 두 수 차이가 M이상 and 최소
-> BinarySearch 떠올림
    -> BinarySearch보다는 투포인터로 이동하는 방식으로 함
    -> BinarySearch는 고정된 target을 찾아야 하는데 그러면 s, e 중에 s를 for문으로 하나씩 탐색하면서
    -> 고정시키면서 Binary_search를 해봐야함

[output]
- 조건에 맞는 차이를 출력
*/
public class BOJ_2230 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		ArrayList<Integer> li = new ArrayList<>();
		for(int i = 0; i < n; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			li.add(a);
		}
		Collections.sort(li);
		int s = 0; int e = 0;
		int res = Integer.MAX_VALUE;
		while(e < n){
			int cur = li.get(e) - li.get(s);
			if(cur > m){
				res = Math.min(res, cur);
				s++;
				if(s == e) e++; // 같을경우 무한루프에 빠지므로 벌리기
			} else if(cur == m){
				res = cur;
				break;
			} else {
				e++;
			}
		}
		System.out.println(res);
	}
}
