package hangeunhyeong.pgs;

/*
프로그래머스 - 파괴되지 않은 건물
N : 게임 맵의 행
M : 게임 맵의 열
T : skill배열의 행(skill을 받는 횟수)
skill을 받을 때마다 매번 board에 그 값을 적용시키면 O(T*N*M)의 시간복잡도 => 시간초과

🔑 2차원 배열의 누적합을 이용해야한다
1. 맵의 (r1, c1)부터 (r2, c2)까지 skill을 받을 때마다 delta배열에 변화량을 더함
(r1, c1)     : +
(r1, c2+1)   : -
(r2 + 1, c1) : -
(r2+1, c2+1) : +
2. delta배열의 행과 열의 누적합을 모두 계산
3. delta + board = 건물들의 최종 내구도 > 0 ➡️ 파괴되지 않은 건물
=> 시간복잡도 O(T + N*M)
 */
import java.util.*;
class PGS_92344 {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int N = board.length, M = board[0].length;
        int[][] delta = new int[N][M];

        // skill을 받을 때마다 delta배열에 변화량을 더함
        for(int[] s : skill){
            int attackOrRecover = s[0] == 1 ? s[5] * -1 : s[5]; // 회복이면 +, 공격이면 -
            int r1 = s[1], r2= s[3], c1= s[2], c2= s[4];    // r1, r2, c1, c2 추출
            // delta배열에 변화량 더하기
            delta[r1][c1] += attackOrRecover;
            if(c2 + 1 < M)
                delta[r1][c2 + 1] -= attackOrRecover;
            if(r2 + 1 < N)
                delta[r2 + 1][c1] -= attackOrRecover;
            if(r2 + 1 < N && c2 + 1 < M)
                delta[r2 + 1][c2 + 1] += attackOrRecover;
        }
        // 열의 누적합
        for(int i = 0; i < N; i++){
            for(int j = 1; j < M; j++){
                delta[i][j] += delta[i][j - 1];
            }
        }
        // 행의 누적합
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                if(j != 0)
                    delta[j][i] += delta[j - 1][i];
                // 건물의 최종 내구도 > 0 ➡️ 파괴되지 않은 건물
                if(board[j][i] + delta[j][i] > 0){
                    answer++;
                }
            }
        }

        return answer;
    }
    public static void printDelta(int[][] delta){
        int N = delta.length;
        int M = delta[0].length;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                System.out.printf("%d ", delta[i][j]);
            }
            System.out.println();
        }
    }

}
