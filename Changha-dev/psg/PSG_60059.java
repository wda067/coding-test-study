
/*
[풀이]
- 열쇠 회전과 이동이 가능
- 자물쇠 범위 벗어나는건 상관 x
    - map 확장
    - 둘이 합쳤을 때 모두 1이어야 함
- 그러면 모든 case를 돌려봐야될텐데 어떻게 하지..?
    - lock의 1,1부터 n,n까지 key를 맞대면서 돌려본다..?
    - mark() 메서드
        - key를 맞대었을때 틀리면 바로 continue;
[output]
- 자물쇠를 열수 있으면 true / false
*/
import java.util.*;
public class PSG_60059 {

	static int n, m;
	public boolean solution(int[][] key, int[][] lock) {
		boolean answer = true;
		n = lock.length;
		m = key.length;
		int sx = n; int sy = n;
		int[][] tmp = new int[3*n][3*n];
		for(int i = n; i < 2*n; i++){
			for(int j = n; j < 2*n; j++){
				tmp[i][j] = lock[i-n][j-n];
			}
		}

		for(int r = 0; r < 4; r++){
			key = rotate(key);

			for(int i = 0; i <= 2*n; i++){
				for(int j = 0; j <= 2*n; j++){
					int[][] newMap = deepCopy(tmp);
					mark(newMap, key, i, j);
					boolean res = check(newMap);
					if(res) return true;
				}
			}
		}

		return false;
	}

	static boolean check(int[][] map){
		for(int i = n; i < 2*n; i++){
			for(int j = n; j < 2*n; j++){
				if(map[i][j] == 0) return false;
				if(map[i][j] == 2) return false;
			}
		}
		return true;
	}

	static void mark(int[][] tmp, int[][] key, int x, int y){
		for(int i = 0; i < m; i++){
			for(int j = 0; j < m; j++){
				tmp[i+x][j+y] += key[i][j];
			}
		}
	}

	static int[][] deepCopy(int[][] src){
		int[][] dst = new int[src.length][];
		for (int i = 0; i < src.length; i++) {
			dst[i] = Arrays.copyOf(src[i], src[i].length);
		}
		return dst;
	}

	static int[][] rotate(int[][] key){
		int[][] rot = new int[m][m];
		for(int i = 0; i < m; i++){
			for(int j = 0; j < m; j++){
				rot[j][m-1-i] = key[i][j];
			}
		}
		return rot;
	}
}
