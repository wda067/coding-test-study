/*
프로그래머스 / 자물쇠와 열쇠 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/60059
 */
public class PGS_60059 {

    public boolean solution(int[][] key, int[][] lock) {
        int M = key.length;
        int N = lock.length;
        int offset = M - 1;
        int expandedSize = N + 2 * offset;
        int[][] expandedLock = new int[expandedSize][expandedSize];

        // 확장된 자물쇠 배열에 기존 자물쇠 값 복사
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                expandedLock[offset + r][offset + c] = lock[r][c];
            }
        }

        // 열쇠를 4번 회전하며 검사
        for (int rotation = 0; rotation < 4; rotation++) {
            key = rotateKey(key);

            // 열쇠를 이동시키며 검사
            for (int x = 0; x < expandedSize - offset; x++) {
                for (int y = 0; y < expandedSize - offset; y++) {

                    // 열쇠를 자물쇠에 놓기
                    for (int r = 0; r < M; r++) {
                        for (int c = 0; c < M; c++) {
                            expandedLock[x + r][y + c] += key[r][c];
                        }
                    }

                    // 자물쇠가 열리는지 확인
                    if (check(expandedLock, offset, N)) {
                        return true;
                    }

                    // 열쇠를 다시 제거
                    for (int r = 0; r < M; r++) {
                        for (int c = 0; c < M; c++) {
                            expandedLock[x + r][y + c] -= key[r][c];
                        }
                    }
                }
            }
        }
        return false;
    }

    // 열쇠를 시계 방향으로 90도 회전시키는 함수
    private int[][] rotateKey(int[][] key) {
        int M = key.length;
        int[][] rotatedKey = new int[M][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < M; c++) {
                rotatedKey[c][M - 1 - r] = key[r][c];
            }
        }
        return rotatedKey;
    }

    // 자물쇠가 모두 채워졌는지 확인하는 함수
    private boolean check(int[][] expandedLock, int offset, int lockLength) {
        for (int r = 0; r < lockLength; r++) {
            for (int c = 0; c < lockLength; c++) {
                if (expandedLock[offset + r][offset + c] != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
