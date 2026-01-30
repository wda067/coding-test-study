package hangeunhyeong.pgs;
import java.util.*;
public class PGS_60059 {

    public boolean solution(int[][] key, int[][] lock) {
        int M = key.length;
        int N = lock.length;

        // lock을 가운데에 두기 위한 확장 보드 (3N x 3N)
        int size = N * 3;
        int[][] board = new int[size][size];

        // 가운데 N x N 위치에 lock 복사
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i + N][j + N] = lock[i][j];
            }
        }

        int[][] curKey = key;

        // 4번 회전하면서 모든 위치 시도
        for (int rot = 0; rot < 4; rot++) {
            for (int x = 0; x <= size - M; x++) {
                for (int y = 0; y <= size - M; y++) {
                    if (canUnlock(curKey, board, x, y, N)) return true;
                }
            }
            curKey = rotate90(curKey);
        }

        return false;
    }

    // (x,y) 위치에 key를 더해보고, lock 중앙 영역이 모두 1이면 성공
    private boolean canUnlock(int[][] key, int[][] board, int x, int y, int N) {
        int M = key.length;

        // key 올리기 (더하기)
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                board[x + i][y + j] += key[i][j];
            }
        }

        // 중앙 lock 영역 체크: 전부 1이어야 함
        boolean ok = true;
        for (int i = N; i < 2 * N && ok; i++) {
            for (int j = N; j < 2 * N; j++) {
                if (board[i][j] != 1) { // 0이면 홈 못채움, 2면 돌기 충돌
                    ok = false;
                    break;
                }
            }
        }

        // key 내리기 (원복)
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                board[x + i][y + j] -= key[i][j];
            }
        }

        return ok;
    }

    // 시계방향 90도 회전
    public int[][] rotate90(int[][] key) {
        int M = key.length;
        int[][] rotated = new int[M][M];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                rotated[j][M - 1 - i] = key[i][j];
            }
        }
        return rotated;
    }
}