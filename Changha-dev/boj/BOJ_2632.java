import java.util.*;
import java.lang.*;
import java.io.*;

/*
- a에서 일부 빼면 b에서 나머지로 만들 수 있는지
- 연속된 수 -> prefixSum으로 세팅하면 되겠다 생각
- 원형 -> len 2배 증가로 풀기 (이 부분 복습 필요)
*/
public class BOJ_2632 {
	static int size;
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());
		size = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		int[] a = new int[m];
		int[] b = new int[n];
		for(int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			a[i] = Integer.parseInt(st.nextToken());
		}
		for(int i = 0; i < n; i++){
			st = new StringTokenizer(br.readLine());
			b[i] = Integer.parseInt(st.nextToken());
		}
		long ans = 0;
		int[] cntA = buildCnt(a, m);
		int[] cntB = buildCnt(b, n);
		for(int i = 0; i <= size; i++){
			ans += (long) cntA[i] * cntB[size - i];
		}
		System.out.println(ans);
	}

	static int[] buildCnt(int[] arr, int len){
		int[] prefix = new int[2 * len + 1];
		for(int i = 0; i < 2 * len; i++){
			prefix[i+1] = prefix[i] + arr[i % len];
		}
		int[] cnt = new int[size + 1];
		cnt[0] = 1;

		int total = prefix[len];
		if(total <= size) cnt[total]+=1;

		for(int l = 1; l <= len - 1; l++){
			for(int start = 0; start < len; start++){
				int sum = prefix[start + l] - prefix[start];
				if(sum <= size) cnt[sum] += 1;
			}
		}
		return cnt;
	}
}