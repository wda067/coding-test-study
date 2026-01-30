import java.util.*;

/*
    자물쇠는 격자 한 칸의 크기가 1 * 1인 N * N 크기의 정사각 격자 형태다.
    특이한 모양의 열쇠는 M * M 크기의 정사각 격자 형태다.
    자물쇠는 홈이 파였다.
    열쇠는 홈과 돌기가 있다.
    열쇠는 회전과 이동이 가능하다.
    열쇠의 돌기 부분을 자물쇠의 홈 부분에 맞게 채우면 자물쇠가 열린다.
    자물쇠 영역 내에서는 열쇠의 돌기 부분과 자물쇠의 홈 부분이 정확히 일치해야 한다.
    열쇠의 돌기와 자물쇠의 돌기는 만날 수 없다.
    자물쇠의 모든 홈을 채워야 열 수 있다.
    
    열쇠로 자물쇠를 열 수 있으면 true를 반환하고 열 수 없으면 false를 반환
*/
class PGS_60059 {
    /* 
        90도 회전 함수
        시간 복잡도 = O(keySize^2)
    */
    public int[][] rotate(int[][] key, int keySize) {
        int[][] rotated = new int[keySize][keySize];
        for (int x = 0; x < keySize; x++) {
            for (int y = 0; y < keySize; y++) {
                rotated[x][y] = key[keySize - 1 - y][x];
            }
        }
        return rotated;
    }

    /*
        깊은 복사를 위한 함수
        2차원 배열 arr을 복사해서 반환
        시간 복잡도 = O(size^2)
    */
    private int[][] deepCopy(int[][] arr) {
        int size = arr.length;      // 행 크기
        int[][] copy = new int[size][size];     // 복사본 배열
        for (int i = 0; i < size; i++) {
            // arr의 i번째 행을 copy의 i번째 행으로 복사
            System.arraycopy(arr[i], 0, copy[i], 0, size);
        }
        return copy;
    }
    
    /*
        시간 복잡도 = O(lockSize^2 × (keySize + lockSize)^2 × keySize^2)
    */
    public boolean solution(int[][] key, int[][] lock) {
        int keySize = key.length;       // key 배열의 행 크기
        int lockSize = lock.length;     // lock 배열의 행 크기

        // 자물쇠에서 홈 부분(0) 갯수 세기
        int lockHomeCnt = 0;
        for (int x = 0; x < lockSize; x++) {
            for (int y = 0; y < lockSize; y++) {
                if (lock[x][y] == 0) {
                    lockHomeCnt++;
                }
            }
        }

        // 열쇠 회전 및 자물쇠 맞추기
        for (int i = 0; i < 4; i++) {  // 0도, 90도, 180도, 270도 회전
            if (tryLock(key, lock, keySize, lockSize)) {
                return true;
            }
            key = rotate(key, keySize);  // 키를 90도 회전시킴
        }

        return false;
    }

    /*
        자물쇠에 열쇠를 맞출 수 있는지 검사
        시간 복잡도 = O(lockSize^2 × (keySize + lockSize)^2 × keySize^2)
    */
    private boolean tryLock(int[][] key, int[][] lock, int keySize, int lockSize) {
        for (int lockX = 0; lockX < lockSize; lockX++) {  // 자물쇠 행
            for (int lockY = 0; lockY < lockSize; lockY++) {  // 자물쇠 열
                // 열쇠를 자물쇠에 놓기 위해서 시도
                for (int startX = -keySize + 1; startX < lockSize; startX++) {  // 키의 시작 행
                    for (int startY = -keySize + 1; startY < lockSize; startY++) {  // 키의 시작 열
                        if (canFit(key, lock, keySize, lockSize, startX, startY, lockX, lockY)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /* 
        키가 자물쇠에 맞을 수 있는지 확인하는 함수
        시간 복잡도 = O(keySize^2 + lockSize^2)
    */
    private boolean canFit(int[][] key, int[][] lock, int keySize, int lockSize, int startX, int startY, int lockX, int lockY) {
        int cnt = 0;

        // 자물쇠에 키가 맞는지 확인
        for (int x = 0; x < keySize; x++) {
            for (int y = 0; y < keySize; y++) {
                int lockPosX = lockX + x + startX;
                int lockPosY = lockY + y + startY;

                // 키가 자물쇠 안에 들어가는지 확인
                if (lockPosX >= 0 && lockPosX < lockSize && lockPosY >= 0 && lockPosY < lockSize) {
                    if (key[x][y] == 1 && lock[lockPosX][lockPosY] == 1) {
                        return false;  // 키의 돌기와 자물쇠의 돌기가 겹침
                    }
                    if (key[x][y] == 1 && lock[lockPosX][lockPosY] == 0) {
                        cnt++;  // 홈을 채움
                    }
                }
            }
        }

        // 자물쇠의 모든 홈을 채웠다면
        return cnt == countLockHoles(lock, lockSize);
    }

    /*
        자물쇠의 홈(0)의 개수를 세는 함수
        시간 복잡도 = O(lockSize^2)
    */
    private int countLockHoles(int[][] lock, int lockSize) {
        int cnt = 0;
        for (int x = 0; x < lockSize; x++) {
            for (int y = 0; y < lockSize; y++) {
                if (lock[x][y] == 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
