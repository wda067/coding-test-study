import java.util.*;
import java.lang.*;

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
[문제점]
- move() 후에 매 턴마다 NxN 전체를 훑음
- divide()가 전체 balls를 매번 스캔

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
	static ArrayList<FireBall>[][] map;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		int M = sc.nextInt();
		int k = sc.nextInt();
		map = new ArrayList[n][n];
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) map[i][j] = new ArrayList<>();
		for(int i = 0; i < M; i++){
			int r = sc.nextInt()-1;
			int c = sc.nextInt()-1;
			int m = sc.nextInt();
			int s = sc.nextInt();
			int d = sc.nextInt();

			balls.add(new FireBall(r, c, m, s, d));
		}

		for(int t = 0; t < k; t++){
			moveAll();
			mergeSplitAll();
		}

		long ans = 0;
		for(FireBall b : balls) ans += b.m;
		System.out.println(ans);
	}

	static void mergeSplitAll(){
		ArrayList<FireBall> next = new ArrayList<>();

		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				ArrayList<FireBall> cell = map[i][j];

				int cnt = cell.size();
				if(cnt == 0) continue;

				if(cnt == 1){
					next.add(cell.get(0));
					continue;
				}

				int sumM = 0, sumS = 0;
				boolean allEven = true, allOdd = true;

				for(FireBall b: cell){
					sumM += b.m;
					sumS += b.s;
					if(b.d % 2 == 0) allOdd = false;
					else allEven = false;
				}

				int nm = sumM / 5;
				if(nm == 0) continue;

				int ns = sumS / cnt;
				int startDir = (allEven || allOdd) ? 0 : 1;

				for(int d = startDir; d < 8; d += 2){
					next.add(new FireBall(i, j, nm, ns, d));
				}
			}
		}
		balls = next;
	}

	static void moveAll(){
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) map[i][j].clear();

		for(FireBall b : balls){
			int step = b.s % n;
			int nr = (b.r + dx[b.d] * step) % n;
			int nc = (b.c + dy[b.d] * step) % n;
			if(nr < 0) nr += n;
			if(nc < 0) nc += n;

			b.r = nr; b.c = nc;
			map[nr][nc].add(b);
		}
	}
}