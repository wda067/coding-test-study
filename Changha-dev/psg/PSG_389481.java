/*
[풀이]
- 소문자 1~11글자
- 정렬: 길이 짧은 것부터, 길이 같으면 사전순

- 각 문자열을 전체 순번(num)으로 변환해서 bans를 숫자로 처리(26진수처럼 a=0~z=25)
  num = (더 짧은 길이 개수) + (같은 길이 내 인덱스) + 1
- bans는 중복 제거(Set) 후 num으로 바꿔 정렬
- target = n 에서 시작해서, banNum <= target 이면 그 금지어만큼 순번이 한 칸 밀리므로 target++
- 최종 target(num)을 다시 문자열로 변환해서 답 반환
*/
import java.util.*;
public class PSG_389481 {

	static long[] pow26 = new long[12];
	static long[] prefixSum = new long[12];
	public String solution(long n, String[] bans) {
		// 초기화
		pow26[0] = 1L;
		for(int i = 1; i <= 11; i++){
			pow26[i] = pow26[i-1] * 26;
		}
		prefixSum[0] = 0L;
		for(int i = 1; i <= 11; i++){
			prefixSum[i] = prefixSum[i-1] + pow26[i];
		}

		HashSet<String> unique = new HashSet<>(Arrays.asList(bans));
		long[] banNums = new long[unique.size()];
		int idx = 0;
		for(String b : unique){
			banNums[idx++] = covertToNum(b);
		}
		Arrays.sort(banNums);

		long target = n;
		for (long bn : banNums) {
			if (bn <= target) target++;
			else break;
		}

		return covertToString(target);
	}

	static long covertToNum(String s){
		int len = s.length();
		long prev = prefixSum[len-1];

		long tmp = 0L;
		for(int i = 0; i < len; i++){
			int d = s.charAt(i) - 'a';
			tmp = tmp * 26L + d;
		}
		prev += tmp + 1L;
		return prev;
	}

	static String covertToString(long num){
		int len = 1;
		while(len <= 11 && num > prefixSum[len]) len++;

		long tmp = num - prefixSum[len-1] - 1L;

		char[] s = new char[len];
		for(int i = len - 1; i >= 0; i--){
			int d = (int)(tmp % 26L);
			s[i] = (char)('a'+d);
			tmp /= 26L;
		}
		return new String(s);

	}
}
