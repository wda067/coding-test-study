import java.util.*;
import java.lang.*;
import java.io.*;


/*
[풀이]
길이가 최대 50x50이므로
- 100 x 100 으로 시간복잡도 계산하면 브루트포스 가능 
- 둘다 1이면 안됨
*/
public class BOJ_21277 {

	static int[][] arr1; static int[][] arr2;
	static int n1, m1, n2, m2;
	static int res = Integer.MAX_VALUE;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n1 = sc.nextInt();
		m1 = sc.nextInt();
		arr1 = new int[n1][m1];
		for(int i = 0; i < n1; i++){
			String s = sc.next();
			for (int j = 0; j < m1; j++) {
				arr1[i][j] = s.charAt(j) - '0';
			}
		}
		n2 = sc.nextInt();
		m2 = sc.nextInt();
		arr2 = new int[n2][m2];
		for(int i = 0; i < n2; i++){
			String s = sc.next();
			for (int j = 0; j < m2; j++) {
				arr2[i][j] = s.charAt(j) - '0';
			}
		}
		for (int rot = 0; rot < 4; rot++) {
			solve();
			rotate90();
		}
		System.out.println(res);
	}

	static void solve() {
		int bn = arr2.length;
		int bm = arr2[0].length;

		for (int dx = -bn; dx <= n1; dx++) {
			for (int dy = -bm; dy <= m1; dy++) {

				if (!canPlace(dx, dy)) continue;

				// 범위 체크
				int top = Math.min(0, dx);
				int left = Math.min(0, dy);
				int bottom = Math.max(n1, dx + bn);
				int right = Math.max(m1, dy + bm);

				int area = (bottom - top) * (right - left);
				res = Math.min(res, area);
			}
		}
	}

	static boolean canPlace(int dx, int dy) {
		int bn = arr2.length;
		int bm = arr2[0].length;

		for (int i = 0; i < bn; i++) {
			for (int j = 0; j < bm; j++) {
				if (arr2[i][j] == 0) continue;

				int x = dx + i;
				int y = dy + j;

				if (0 <= x && x < n1 && 0 <= y && y < m1) {
					if (arr1[x][y] == 1) return false;
				}
			}
		}
		return true;
	}

	static void rotate90() {
		int n = arr2.length;
		int m = arr2[0].length;
		int[][] dst = new int[m][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dst[j][n - 1 - i] = arr2[i][j];
			}
		}
		arr2 = dst;
	}
}