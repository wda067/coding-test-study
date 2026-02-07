import java.util.*;
import java.lang.*;

/*
블록
    - 검은색 -1
    - 무지개 0
    - 일반 M가지

[풀이]
그룹 조건
- 블록 개수 2개 이상
- 검은색 포함x
- 일반 블록 모두 색 동일
- 무지개 상관 x
- 기준 블록
    - 일반 블록 중 행의 번호가 가장 작은
    - 그중에서 열의 번호가 가장 작은

- 크기가 가장 큰 블록 그룹 찾기
    - 여러개 -> 무지개 가장 많은 그룹
    - 이것도 여러개 -> 기준 블록의 행이 가장 큰 것
    - 이것도 여러개 -> 열이 가장 큰 것
- 점수 획득
    - 블록의 수 : B -> B^2 획득
- 중력 작용
    - 검은색 제외, 모든 블록이 행의 번호가 큰 칸으로 이동
    - 끝 조건
        - 다른 블록을 만나기 or 격자의 경계 만나기
- 격자 90도 반시계 회전
- 다시 중력 작용

1. Group class, Block class
    - Group은 bndBlock저장, bndNum 저장
2. buildGroup 메서드
	- build 메서드로 그룹 만들기
3. remove 메서드
	- selectedGroup 제거
4. gravity 메서드
	- 배열 방식
5. rotate 메서드

[느낀점]
- 완전 하드한 구현 문제라고 느껴졌다...
- 클래스 설계는 그래도 무난하게 했던 것 같다
- gravity방식을 잘 익히기
*/
public class BOJ_21609 {

	static class Group {
		Block bndBlock;
		ArrayList<Block> blocks = new ArrayList<>();
		int bndNum;
		int rainbowCnt;
		Group (Block bndBlock, ArrayList<Block> blocks, int bndNum, int rainbowCnt){
			this.bndBlock = bndBlock;
			this.blocks = blocks;
			this.bndNum = bndNum;
			this.rainbowCnt = rainbowCnt;
		}
	}

	static class Block {
		int x;
		int y;
		int num;

		Block(int x, int y, int num){
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int[][] map;
	static Group selectedGroup;
	static int n, m;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();
		map = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				map[i][j] = sc.nextInt();
			}
		}
		int res = 0;

		while(true){
			if(!buildGroup()) break;
			int b = remove();
			res += b*b;
			gravity();
			rotate();
			gravity();
		}
		System.out.println(res);
	}

	static void gravity() {
		for (int col = 0; col < n; col++) {
			int write = n - 1; // 블록이 떨어져서 쌓일 위치

			for (int row = n - 1; row >= 0; row--) {
				if (map[row][col] == -1) {
					write = row - 1;
				} else if (map[row][col] >= 0) {    // 일반/무지개 블록만 이동
					int val = map[row][col];
					map[row][col] = -2;
					map[write][col] = val;
					write--;
				}
				// map[row][col] == -2(빈칸)이면 그냥 지나감
			}
		}
	}

	static int remove(){
		int size = selectedGroup.blocks.size();
		for(Block b : selectedGroup.blocks){
			map[b.x][b.y] = -2;
		}
		return size;
	}

	static Group build(int x, int y, int num){
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[]{x,y});

		boolean[][] visited = new boolean[n][n];
		visited[x][y] = true;

		ArrayList<Block> blocks = new ArrayList<>();
		Block bndBlock = new Block(x,y,num);
		int rainbowCnt = 0;

		while(!q.isEmpty()){
			int[] cur = q.poll();
			int cx = cur[0]; int cy = cur[1];

			blocks.add(new Block(cx, cy, map[cx][cy]));
			if(map[cx][cy] == 0) rainbowCnt++;

			if(map[cx][cy] > 0){
				if(cx < bndBlock.x || (cx == bndBlock.x && cy < bndBlock.y)){
					bndBlock = new Block(cx, cy, map[cx][cy]);
				}
			}
			for(int d = 0; d < 4; d++){
				int nx = cx + dx[d]; int ny = cy + dy[d];
				if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
				if(visited[nx][ny]) continue;
				if(map[nx][ny] != num && map[nx][ny] != 0) continue;

				visited[nx][ny] = true;
				q.add(new int[]{nx, ny});
			}
		}
		if(blocks.size() < 2) return null;
		return new Group(bndBlock, blocks, num, rainbowCnt);
	}

	static boolean buildGroup() {
		Group best = null;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int color = map[i][j];
				if (color <= 0) continue;

				Group cur = build(i, j, color);
				if (cur == null) continue;

				if (best == null) {
					best = cur;
					continue;
				}

				int bestSize = best.blocks.size();
				int curSize  = cur.blocks.size();

				if (curSize != bestSize) {
					if (curSize > bestSize) best = cur;
					continue;
				}

				if (cur.rainbowCnt != best.rainbowCnt) {
					if (cur.rainbowCnt > best.rainbowCnt) best = cur;
					continue;
				}

				// 기준블록 행이 큰 것
				if (cur.bndBlock.x != best.bndBlock.x) {
					if (cur.bndBlock.x > best.bndBlock.x) best = cur;
					continue;
				}

				// 기준블록 열이 큰 것
				if (cur.bndBlock.y > best.bndBlock.y) best = cur;
			}
		}

		if (best == null) return false;

		selectedGroup = best;
		return true;
	}

	static void rotate() {
		int[][] tmp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tmp[n - 1 - j][i] = map[i][j];
			}
		}
		map = tmp;
	}
}
