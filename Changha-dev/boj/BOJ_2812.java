import java.util.*;
import java.lang.*;
/*
N자리 숫자가 주어졌을 때,
둘째 줄에 N자리가 주어진다. 이 수는 0으로 시작하지 않는다.

[풀이]
- 프로그래머스 큰 수 만들기 처럼 StringBuilder에 그리디 방식 넣는걸 생각
- 안되네.... O(NK)
- Stack 방식
    - removed 변수로 k만큼 지웠는지 체크
    - 현재 숫자와 비교해서 q nums 작으면 빼기

*/
public class BOJ_2812 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		String num = sc.next();
		ArrayDeque<Character> q = new ArrayDeque<>();

		int removed = 0;
		for(int i = 0; i < n; i++){
			char cur = num.charAt(i);
			while(!q.isEmpty()&&removed < k && q.peekLast() < cur){
				q.pollLast();
				removed++;
			}
			q.addLast(cur);
		}

		while(removed < k){
			q.pollLast();
			removed++;
		}

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n-k; i++) sb.append(q.pollFirst());
		System.out.println(sb);

	}
}
