import java.util.*;
import java.io.*;

/*
N개 스위치, N개 전구
0 : 켜져 있는 상태
1 : 꺼져 있는 상태

[조건]
- i 스위치를 누르면 i-1, i, i+1 전구 상태 변함
- (1 < i < N) 필터
- (1, N일때는 각각 오른쪽, 왼쪽까지만 포함)
- 0은 on, 1은 off

[풀이]
초기 상태 -> 최종 상태로 만들기 위해 최소 횟수

1. on,off 메서드 만들기 -> method changeMode
2. 구하는 과정을 어떻게 하지
    - 전체 다 하는 건 시간초과 될 듯
    - O(n) or O(nlogn) -> 그리디
    - 0번째 idx 2가지 case -> 누르거나 말거나
    - i번째는 i-1번째 바라보고 결정
3. 만약 초기 배열 값들이 최종 배열과 똑같은지 확인하고 갱신 -> int answer

[output]
최소 몇 번 누르면 최종 조건 완성되는 지
- 불가능 하면 -1

[느낀점]
- 너무 어렵게 생각한 듯
- 시간 복잡도로 대략적인 풀이 방법 잡은건 좋았음
- 경우의 수를 2가지로 나누는 패턴 기억하기
*/

public class BOJ_2138 {
	static int answer = Integer.MAX_VALUE;
	static int n;
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		int[] arr1 = new int[n];
		int[] arr2 = new int[n];

		String input = br.readLine();
		for(int i = 0; i < input.length(); i++){
			arr1[i] = input.charAt(i) - '0';
		}
		input = br.readLine();
		for(int i = 0; i < input.length(); i++){
			arr2[i] = input.charAt(i) - '0';
		}

		int[] tmp1 = arr1.clone();
		int[] tmp2 = arr1.clone();

		tmp1 = changeMode(tmp1, 0);
		int cnt1 = 1;
		for(int i = 1; i < n; i++){
			if(tmp1[i-1] != arr2[i-1]){
				tmp1 = changeMode(tmp1, i);
				cnt1+=1;
			}
		}
		int cnt2 = 0;
		for(int i = 1; i < n; i++){
			if(tmp2[i-1] != arr2[i-1]){
				tmp2 = changeMode(tmp2, i);
				cnt2+=1;
			}
		}
		if(Arrays.equals(tmp1, arr2)){
			answer = Math.min(cnt1, answer);
		}
		if(Arrays.equals(tmp2, arr2)){
			answer = Math.min(cnt2, answer);
		}
		System.out.println(answer != Integer.MAX_VALUE ? answer : -1);
	}

	static int[] changeMode(int[] arr, int i){
		if(i == 0) {
			arr[0] = arr[0] == 0 ? 1 : 0;
			arr[1] = arr[1] == 0 ? 1 : 0;
		} else if (i == n-1) {
			arr[n-2] = arr[n-2] == 0 ? 1 : 0;
			arr[n-1] = arr[n-1] == 0 ? 1 : 0;
		} else {
			arr[i-1] = arr[i-1] == 0 ? 1 : 0;
			arr[i] = arr[i] == 0 ? 1 : 0;
			arr[i+1] = arr[i+1] == 0 ? 1 : 0;
		}
		return arr;
	}
}
