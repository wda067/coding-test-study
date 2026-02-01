import java.util.*;

/*
 * N * N 격자
 * 파이어볼 M개를 발사
 * 처음에 파이어볼은 각자 위치에서 이동을 대기하고 있다.
 * 격자의 행과 열은 1 ~ N번까지 번호가 매겨져 있다.
 * 1번 행은 N번행과 연결되어 있고, 1번 열은 N번 열과 연결되어 있다.
 * 파이어볼의 방향은 어떤 칸과 인접한 8개의 칸의 방향을 의미한다.
 * 
 * [마법사 상어가 모든 파이어볼들에게 이동을 명령했을 때]
 * 1. 모든 파이어볼이 자신의 방향 d로 속력 s만큼 이동(이동 중에 같은 칸에 여러 개의 파이어볼이 있을 수 있다.)
 * 2. 이동이 모두 끝난 뒤 2개 이상의 파이어볼이 있는 칸에는 다음과 같은 일이 일어난다.
 * 	2-1. 같은 칸에 있는 파이어볼은 모두 하나로 합치기
 * 	2-2. 파이어볼은 4개의 파이어볼로 나누어진다.
 * 	2-3. 나누어진 파이어볼의 질량, 속력, 방향은 
 * 		2-3-1. 질량 = 합쳐진 파이어볼 질량의 합L / 5
 * 		2-3-2. 속력 = 합쳐진 파이어볼 속력의 합 L / 합쳐진 파이어볼의 개수
 * 		2-3-3. 합쳐진 파이어볼의 방향이 모두 홀수이거나 모두 짝수면
 * 					방향은 0, 2, 4, 6
 * 					그렇지 않으면 1, 3, 5, 7
 * 		2-3-4. 질량이 0인 파이어볼은 소멸되어 사라진다.
 * 
 * 마법사 상어가 이동을 K번 명령한 후 남아있는 파이어볼 질량의 합을 구하자.
 */
class BOJ_20056 {
	static class FireBall {
		int m;		// 질량
		int s;		// 속력
		int d;		// 방향
		
		FireBall(int m, int s, int d) {
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}
	
	final static int[] DX = {-1, -1, 0, 1, 1, 1, 0, -1};
	final static int[] DY = {0, 1, 1, 1, 0, -1, -1, -1};
	
	static int N;		// 4 <= 격자 크기 <= 50
	static int M;		// 0 <= 파이어볼의 갯수 <= N^2
	static int K;		// 1 <= 이동 횟수 <= 1,000
	static List<FireBall>[][] grid;
	
	public static void init(List<FireBall>[][] a) {
		for(int x=1; x<=N; x++) {
			for(int y=1; y<=N; y++) {
				a[x][y] = new ArrayList();
			}
		}
	}
	
	public static void play() {
		List<FireBall>[][] tmp = new ArrayList[N + 1][N + 1];
		init(tmp);
		
		for(int x=1; x<=N; x++) {
			for(int y=1; y<=N; y++) {
				// 2개 이상의 파이어볼이 있는 칸이면
				if(grid[x][y].size() >= 2) {
					// 모두 하나로 합치기
					int l = 0;	// 합쳐진 파이어볼의 질량의 합
					int s = 0;		// 합쳐진 파이어볼의 속력의 합
					int evenCnt = 0;	// 짝수 방향 횟수
					int oddCnt = 0;		// 홀수 방향 횟수
					for(FireBall cur : grid[x][y]) {
						l += cur.m;
						s += cur.s;
						if(cur.d % 2 != 0) {		// 홀수
							oddCnt++;
						} else {		// 짝수
							evenCnt++;
						}
					}
					
					int totalL = l / 5;
					int totalS = s / grid[x][y].size();
					
					if(totalL == 0) {		// 질량이 0인 파이어볼은 사라진다
						continue;
					}
					
					// 합쳐진 파이어볼의 방향이 모두 홀수이거나 모두 짝수면
					if(evenCnt == 0 || oddCnt == 0) {
						// 방향이 0, 2, 4, 6
						for(int i=0; i<4; i++) {
							tmp[x][y].add(new FireBall(totalL, totalS, i * 2));
						}
					} else {
						// 방향이 1, 3, 5, 7
						for(int i=0; i<4; i++) {
							tmp[x][y].add(new FireBall(totalL, totalS, i * 2 + 1));
						}
					}
				} else if(grid[x][y].size() == 1) {		// 파이어볼이 1개 있을 때
					// 그대로
					tmp[x][y] = grid[x][y];
				}
			}
		}
		grid = tmp;
	}
	
	/*
	 * 1. 모든 파이어볼이 자신의 방향 d로 속력 s만큼 이동(이동 중에 같은 칸에 여러 개의 파이어볼이 있을 수 있다.)
	 */
	public static void moveAll() {
		List<FireBall>[][] tmp = new ArrayList[N + 1][N + 1];
		init(tmp);
		for(int x=1; x<=N; x++) {
			for(int y=1; y<=N; y++) {
				if(grid[x][y].isEmpty()) {		// 파이어볼이 없다면
					continue;
				}
				for(FireBall cur : grid[x][y]) {
					move(x, y, cur, tmp);
				}
			}
		}
		grid = tmp;
	}
	
	public static void move(int x, int y, FireBall cur, List<FireBall>[][] tmp) {
		int nx = x + DX[cur.d] * cur.s;
		int ny = y + DY[cur.d] * cur.s;

		nx = ((nx - 1) % N + N) % N + 1;
		ny = ((ny - 1) % N + N) % N + 1;

		tmp[nx][ny].add(cur);
	}
	
	public static void simulate() {
		for(int cmd=0; cmd<K; cmd++) {	// K번 이동
			moveAll();
			play();
		}
	}
	
	public static int getAnswer() {
		int answer = 0;
		for(int x=1; x<=N; x++) {
			for(int y=1; y<=N; y++) {
				if(grid[x][y].isEmpty()) {
					continue;
				}
				for(FireBall cur : grid[x][y]) {
					answer += cur.m;
				}
			}
		}
		return answer;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		grid = new ArrayList[N + 1][N + 1];
		init(grid);
		
		// 파이어볼의 정보 입력받기
		for(int i=0; i<M; i++) {
			int r = sc.nextInt();		// 1 <= 행 좌표 <= N
			int c = sc.nextInt();		// 1 <= 열 좌표 <= N
			int m = sc.nextInt();		// 1 <= 질량 <= 1,000
			int s = sc.nextInt();		// 1 <= 속력 <= 1,000
			int d = sc.nextInt();		// 0 <= 방향 <= 7
			grid[r][c].add(new FireBall(m, s, d));
		}
		
		simulate();
		System.out.print(getAnswer());
	}
}