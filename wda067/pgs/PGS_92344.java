/*
프로그래머스 / 파괴되지 않은 건물 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/92344
 */
class PGS_92344 {

    public int solution(int[][] board, int[][] skill) {
        int N = board.length;      // 1,000
        int M = board[0].length;   // 1,000

        /*
        skill의 개수 250,000(K)
        board는 1,000(N) * 1,000(M)
        브루트포스 -> O(N * M * K)
        */
        int[][] change = new int[N + 1][M + 1];

        // skill[i] = [type, r1, c1, r2, c2, degree]
        // O(K)
        for (int[] s : skill) {
            int type = s[0];
            int r1 = s[1], c1 = s[2];
            int r2 = s[3], c2 = s[4];
            int degree = s[5];

            // 적의 공격일 때
            if (type == 1)
                degree *= -1;

            /*
            n 0 0 -n
            0 0 0 0
            0 0 0 0
            -n 0 0 n
             */
            change[r1][c1] += degree;
            change[r1][c2 + 1] -= degree;
            change[r2 + 1][c1] -= degree;
            change[r2 + 1][c2 + 1] += degree;
        }

        /*
        누적 합 계산 -> O(N * M)
        n n n 0
        n n n 0
        n n n 0
        0 0 0 0
         */
        // 가로 방향 누적 합
        for (int i = 0; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                change[i][j] += change[i][j - 1];
            }
        }

        // 세로 방향 누적 합
        for (int j = 0; j <= M; j++) {
            for (int i = 1; i <= N; i++) {
                change[i][j] += change[i - 1][j];
            }
        }

        // 누적합 반영 및 카운트 -> O(N * M)
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] += change[i][j];

                if (board[i][j] > 0) {
                    count++;  // 파괴되지 않은 건물 카운트
                }
            }
        }

        return count;
    }
}
