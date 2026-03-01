import java.util.*;
import java.lang.*;
import java.io.*;

/*
[풀이]
1. 두 번씩 진행 후 이동길이 + 1 증가
2. spread를 위한 퍼센트 위치 baseX,baseY 기억 후 해당 percent 값 저장
	- 회전 하므로 미리 sx,sy로 이차배열로 사전 세팅
	- a 관련 내용도 동일하게

주의.. (percent[i] / 100)으로 하니까 0으로만 출력돼서 알아보니 소수점이라 0으로 되어버림
*/
public class BOJ_20057 {
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};
	static int[] baseX = {-1, 1, -1, 1, -2, 2, -1, 1, 0};
	static int[] baseY = {1, 1, 0, 0, 0, 0, -1, -1, -2};
	static int[] percent = {1, 1, 7, 7, 2, 2, 10, 10, 5};
	static int baseAx = 0;
	static int baseAy = -1;

	static int[][] sx = new int[4][9];
	static int[][] sy = new int[4][9];
	static int[] ax = new int[4];
	static int[] ay = new int[4];

	static int n;
	static int out = 0;
	static int[][] map;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		map = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				map[i][j] = sc.nextInt();
			}
		}
		buildPattern();

		int x = n / 2;
		int y = n / 2;

		int dir = 0;
		int step = 1;

		int turn = 0;
		while (true) {
			for (int i = 0; i < step; i++) {
				x += dx[dir];
				y += dy[dir];
				spread(x, y, dir);
				if (x == 0 && y == 0){
					System.out.println(out);
					return;
				}
			}
			dir = (dir + 1) % 4;

			turn++;
			if (turn == 2) { // 두 번 끝났으면
				step++;
				turn = 0;
			}
		}


	}

	static boolean in(int x, int y){
		return 0 <= x && x < n && 0 <= y && y < n;
	}

	static void spread(int x, int y, int dir){
		int sand = map[x][y];
		if(sand == 0) return;
		map[x][y] = 0;

		int remain = sand;

		for(int i = 0; i < 9; i++){
			int nx = x + sx[dir][i];
			int ny = y + sy[dir][i];
			int next = sand * percent[i] / 100;
			remain -= next;

			if(in(nx, ny)) map[nx][ny] += next;
			else out += next;
		}

		int nx = x + ax[dir];
		int ny = y + ay[dir];
		if(in(nx, ny)) map[nx][ny] += remain;
		else out += remain;
	}

	static void buildPattern(){
		for(int i = 0; i < 9; i++){
			sx[0][i] = baseX[i];
			sy[0][i] = baseY[i];
		}
		ax[0] = baseAx; ay[0] = baseAy;
		for(int d = 1; d < 4; d++){
			for(int i = 0; i < 9; i++){
				int px = sx[d-1][i];
				int py = sy[d-1][i];

				sx[d][i] = -py;
				sy[d][i] = px;
			}
			int pax = ax[d-1];
			int pay = ay[d-1];
			ax[d] = -pay;
			ay[d] = pax;
		}

	}
}