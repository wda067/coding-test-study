/*
- 내구도 0이하 -> 파괴
- 회복으로 다시 살릴 수 있음

tmp[][] 배열을 따로 만듦
    - skill 적용 val을 tmp배열에 저장
    - skill for(n)
        - 가로x세로 돌면서 기입하면 시간초과 (10^6 x 10^3 x 10^3)
        - 사전 세팅 후 단 한번의 for문으로 채우도록 -> 누적합
            - 총 4군데에 기입

[output]
- 파괴되지 않은 건물의 개수 return

*/
public class PSG_92344 {
	static int[][] tmp;
	static int n, m;
	public int solution(int[][] board, int[][] skill) {
		int answer = 0;
		n = board.length;
		m = board[0].length;
		tmp = new int[n+1][m+1];
		for(int[] s : skill){
			mark(s);
		}
		simulate();
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				int cur = board[i][j] + tmp[i][j];
				if(cur <= 0) continue;
				answer+=1;
			}
		}

		return answer;
	}

	static void mark(int[] skill){
		int type = skill[0]; int val = skill[5];
		int sx = skill[1]; int sy = skill[2];
		int ex = skill[3]; int ey = skill[4];
		val = type == 1 ? val * -1 : val;
		tmp[sx][sy] += val;
		tmp[sx][ey+1] -= val;
		tmp[ex+1][sy] -= val;
		tmp[ex+1][ey+1] += val;
	}

	static void simulate(){
		// l -> r
		for(int r = 0; r < n; r++){
			for(int c = 1; c < m; c++){
				tmp[r][c] += tmp[r][c-1];
			}
		}

		// u -> d
		for(int c = 0; c < m; c++){
			for(int r = 1; r < n; r++){
				tmp[r][c] += tmp[r-1][c];
			}
		}
	}
}
