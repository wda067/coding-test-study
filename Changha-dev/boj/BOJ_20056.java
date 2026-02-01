import java.util.*;
import java.lang.*;
import java.io.*;

/*
[input]
n : 길이
m : 파이어볼 개수
k : 이동 횟수
[풀이]
- 파이어볼 개수 카운트
    - 2개 이상일 때
        - 합쳐짐 -> 어떻게 기록할건지
            - 4개로 나뉘어짐
            - 질량 = (전체 / 5)
            - 속력 = (전체 / 개수)
            - 방향 = 모두 홀수 or 짝수
                - 0,2,4,6
                - else 1,3,5,7
        - 질량 0 -> 소멸

- ArrayList로 FireBall들을 기록할까하는데

*/
public class BOJ_20056 {

	static class FireBall {
		int r;
		int c;
		int m;
		int s;
		int d;
		public FireBall(int r, int c, int m, int s, int d){
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}

	static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
	static ArrayList<FireBall> balls = new ArrayList<>();
	static int n;
	static int[][] map;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		int M = sc.nextInt();
		int k = sc.nextInt();
		map = new int[n+1][n+1];
		for(int i = 0; i < M; i++){
			int r = sc.nextInt();
			int c = sc.nextInt();
			int m = sc.nextInt();
			int s = sc.nextInt();
			int d = sc.nextInt();

			balls.add(new FireBall(r, c, m, s, d));
			map[r][c] += 1;
		}
		for(int i = 0; i < k; i++){
			move();
			for(int r = 1; r <= n; r++){
				for(int c = 1; c <= n; c++){
					if(map[r][c] >= 2){
						divide(r,c);
					}
				}
			}
		}
		int res = 0;
		for(var cur : balls){
			res += cur.m;
		}
		System.out.println(res);
	}

	static void divide(int x, int y){
		ArrayList<FireBall> tmp = new ArrayList<>();
		ArrayList<FireBall> next = new ArrayList<>();
		for(var ball : balls){
			if(ball.r == x && ball.c == y){
				tmp.add(ball);
			} else {
				next.add(ball);
			}
		}
		int sumM = 0; int sumS = 0;
		int size = tmp.size();
		Boolean isOdd = null;
		boolean res = true;
		for(var t : tmp){
			sumM += t.m;
			sumS += t.s;
			if(isOdd == null){
				if(t.d % 2 == 0){
					isOdd = false;
				} else {
					isOdd = true;
				}
			} else {
				if(isOdd){
					if(t.d % 2 == 0){
						res = false;
					}
				} else {
					if(t.d % 2 != 0){
						res = false;
					}
				}
			}
		}
		sumM /= 5;
		if(sumM == 0){
			map[x][y] = 0;
			balls = next;
			return;
		}
		sumS /= size;
		if(res){
			for(int d = 0; d < 8; d+=2){
				next.add(new FireBall(x, y, sumM, sumS, d));
			}
		} else {
			for(int d = 1; d < 9; d+=2){
				next.add(new FireBall(x, y, sumM, sumS, d));
			}
		}
		map[x][y] = 4;
		balls = next;
	}

	static void move(){
		ArrayList<FireBall> next = new ArrayList<>(balls.size());
		for(var ball : balls){
			int nx = ball.r + (ball.s * dx[ball.d]);
			int ny = ball.c + (ball.s * dy[ball.d]);
			if(nx < 1 || nx > n){
				nx = ((nx - 1) % n + n) % n + 1;
			}
			if(ny < 1 || ny > n){
				ny = ((ny - 1) % n + n) % n + 1;
			}
			next.add(new FireBall(nx, ny, ball.m, ball.s, ball.d));
			map[ball.r][ball.c] -= 1;
			map[nx][ny] += 1;
		}
		balls = next;
	}
}
