import java.util.*;
import java.lang.*;

/*
[풀이]
- H * (N-1) = 30 * 9 =270칸 
    - 2^270 안됨
- 270C3 이정도는 됨
    - 10^6 x 10^2 = 10^8정도에 가지치기로 하면 가능

- 1, n : 각각 왼쪽, 오른쪽 이동 x
- 2, n-1 : 양쪽 이동 가능
- 기존 가로선 세팅해놓고 모든경우 탐색? -> backtracking
    - 제한 : 최대 3까지
[output]
i->i 가 나오도록, 가로선 개수 추가 최솟값 구해야한다.

어렵군.. check()메서드 구현을 정확히 어떻게 해야될지 안떠올랐다. (좌우비교는 알겠는데 현재 값 저장해놓고 갱신하는 부분)
*/
class BOJ_15684 {

	static boolean[][] isCheck;
	static int answer = Integer.MAX_VALUE;
	static int n, m, h;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();
		h = sc.nextInt();

		isCheck = new boolean[h+1][n+1];
		for(int i = 0; i < m; i++){
			int a = sc.nextInt();
			int b = sc.nextInt();
			isCheck[a][b] = true;
		}

		for(int i = 0; i <= 3; i++){
			dfs(0, 1, 1, i);
		}

		System.out.println(answer != Integer.MAX_VALUE ? answer : -1);
	}

	static void dfs(int dep, int sRow, int sCol, int maxDep){
		if(dep == maxDep){
			if(check()) answer = Math.min(answer, dep);
			return;
		}

		for(int r = sRow; r <= h; r++){
			int newCol = (r == sRow ? sCol : 1);
			for(int c = newCol; c <= n-1; c++){
				if(canPlace(r,c)){
					isCheck[r][c] = true;
					dfs(dep+1, r, c+2, maxDep);
					isCheck[r][c] = false;
				}
			}
		}
	}

	static boolean check(){
		for(int c = 1; c <= n; c++){
			int cur = c;
			for(int r = 1; r <= h; r++){
				if(isCheck[r][cur]) cur++;
				else if(cur-1 >= 1 && isCheck[r][cur-1]) cur--;
			}
			if(cur != c) return false;
		}
		return true;
	}

	static boolean canPlace(int r, int c){
		if(isCheck[r][c]) return false;
		if(c-1 >= 1 && isCheck[r][c-1]) return false;
		if(c+1 <= n-1 && isCheck[r][c+1]) return false;
		return true;
	}
}