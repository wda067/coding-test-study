/*
    N * M 행렬 모양의 게임 맵
    내구도를 가진 건물이 각 칸마다 하나씩 있다.
    건물은 적의 공격을 받으면 내구도가 감소
    내구도 <= 0:
        파괴
    회복 스킬을 사용하면 건물들의 내구도를 높인다.
    적의 공격과 아군의 회복 스킬은 항상 직사각형 모양이다.
    내구도가 0이하가 된 이미 파괴된 건물도 공격을 받으면 계속해서 내구도가 하락한다.
    적의 공격 혹은 아군의 회복 스킬이 모두 끝난 뒤 파괴되지 않는 건물의 개수를 구하기

    [풀이]
    누적합 활용
    시간 복잡도: O(K + N * M)
    공간 복잡도: O(N * M)
*/
class PGS_92344 {
    static final int ATTACK = 1;
    static final int RECOVERY = 2;

    public int solution(int[][] board, int[][] skill) {
        int N = board.length;       // 행 크기
        int M = board[0].length;    // 열 크기

        // 차분 배열
        int[][] diff = new int[N + 1][M + 1];

        // 스킬 적용 (O(K))
        for (int[] s : skill) {
            int type = s[0];
            int r1 = s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];
            int degree = s[5];

            if (type == ATTACK) degree = -degree;   // 공격이면

            diff[r1][c1] += degree;     // 좌측 상단
            diff[r1][c2 + 1] -= degree;     // 우측 상단
            diff[r2 + 1][c1] -= degree;     // 좌측 하단
            diff[r2 + 1][c2 + 1] += degree;     // 우측 하단
        }

        // 가로 누적합
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < M; j++) {
                diff[i][j] += diff[i][j - 1];
            }
        }

        // 세로 누적합
        for (int j = 0; j < M; j++) {
            for (int i = 1; i < N; i++) {
                diff[i][j] += diff[i - 1][j];
            }
        }

        // board 반영, 정답 계산
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] + diff[i][j] > 0) {     // 파괴되지 않은 건물이라면
                    answer++;
                }
            }
        }

        return answer;      // 파괴되지 않는 건물의 개수
    }
}
