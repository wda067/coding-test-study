import java.util.*;
import java.lang.*;
import java.io.*;


/*
[input]
건물 N개 -> idx, val 저장 배열
가희가 볼 수 있는 건물 개수 a
단비가 볼 수 있는 건물 개수 b

[풀이]
 N < a + b - 1이면 -1
 - ex. 2, 2, 2인 경우 불가

1. for(1 ~ a-1)으로 a 순차적 삽입
2. a,b중 큰 수를 삽입
3. for(b-1 ~ 1)으로 b 순차적 삽입
4. 만약 list.size() < n 일 때
    - a 가 1인 경우와 > 1 인 경우 나눔
        - a == 1 일 때 :
            - 남은 개수만큼 1번째 인덱스에 1 삽입
        - a > 1 일 때 :
            - 남은 개수만큼 0번째 인덱스에 1 삽입

[output]
- 조건에 맞는 건물들의 높이 정보가 1개 이상
- 사전순으로 정렬 후 N개의 건물 높이 정보 출력

[느낀점]
시간복잡도 O(N)을 넘어서 왜 그리디로 해야되는지 생각을 떠올려야 했음
*/
public class BOJ_24337 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int a = sc.nextInt();
		int b = sc.nextInt();

		LinkedList<Integer> list = new LinkedList<>();

		if(n < a + b - 1) System.out.println(-1);
		else {
			for(int i = 1; i < a; i++){
				list.add(i);
			}
			list.add(Math.max(a, b));
			for(int i = b-1; i >= 1; i--){
				list.add(i);
			}

			if(a==1){
				while(list.size() < n){
					list.add(1, 1);
				}
			} else {
				while(list.size() < n){
					list.add(0, 1);
				}
			}
		}
		var sb = new StringBuilder();

		for(int num : list){
			sb.append(num).append(" ");
		}
		System.out.println(sb);
	}
}
