import java.util.*;
import java.io.*;

/*
12x6 배열 공간
R, G, B, P, Y

이중 for로 순회
상하좌우 탐색 -> bfs

터졌을 때 : 위에 뿌요가 있으면 아래로 하강함 (이 부분 구현이 어려울 듯) -> rebuild 메서드로 분리
- 몇 칸을 아래로 하강해야 되는 지 정보 필요 -> 리스트에 담아서 아래에서부터 채우기
- 터진 위치에 val : 'X'로 설정

rebuild 메서드는 'X'를 보고 아래로 이동

[느낀점]
- 조건 만족때까지 while문 돌리는 패턴 익혀야되겠다..
- 몇 칸을 내려야 되는 지 버블 정렬은 비효율적이라는 것을 생각
*/
public class BOJ_11559 {
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static char[][] arr = new char[12][6];
	static boolean[][] visited;
	static int answer = 0;
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 0; i < 12; i++){
			String line = br.readLine();
			for(int j = 0; j < 6; j++){
				arr[i][j] = line.charAt(j);
			}
		}
		while(true){
			boolean isPopped = false;
			visited = new boolean[12][6];

			for(int i = 0; i < 12; i++){
				for(int j = 0; j < 6; j++){
					if(arr[i][j] == '.') continue;
					if(arr[i][j] == 'X') continue;
					if(visited[i][j]) continue;

					if(bfs(i, j, arr[i][j])){
						isPopped = true;
					}
				}
			}

			if(isPopped){
				answer++;
				rebuild();
			} else {
				break;
			}
		}

		System.out.println(answer);
	}

	static boolean bfs(int x, int y, char base){
		var q = new LinkedList<int[]>();
		var tmp = new ArrayList<int[]>();
		q.add(new int[]{x, y});
		tmp.add(new int[]{x, y});
		visited[x][y] = true;
		int cnt = 1;

		while(!q.isEmpty()){
			int[] now = q.poll();
			int curX = now[0];
			int curY = now[1];
			for(int d = 0; d < 4; d++){
				int nx = curX + dx[d];
				int ny = curY + dy[d];
				if(nx < 0 || ny < 0 || nx > 11 || ny > 5) continue;
				if(visited[nx][ny]) continue;
				if(arr[nx][ny] != base) continue;
				visited[nx][ny] = true;
				cnt += 1;
				q.add(new int[]{nx, ny});
				tmp.add(new int[]{nx, ny});
			}
		}
		if(cnt >= 4){
			remark(tmp);
			return true;
		}
		return false;
	}

	static void remark(ArrayList<int[]> tmp){
		for(int[] cur : tmp){
			int x = cur[0];
			int y = cur[1];
			arr[x][y] = 'X';
		}
	}

	static void rebuild(){
		for(int i = 0; i < 6; i++){
			List<Character> li = new ArrayList<>();
			for(int j = 11; j >= 0; j--){
				if(arr[j][i] == '.') continue;
				if(arr[j][i] == 'X') continue;
				li.add(arr[j][i]);
			}

			for(int r = 0; r < 12; r++){
				arr[r][i] = '.';
			}

			int idx = 11;
			for(var cur : li){
				arr[idx][i] = cur;
				idx--;
			}
		}
	}
}
