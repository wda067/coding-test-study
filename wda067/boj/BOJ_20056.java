import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
백준 / 마법사 상어와 파이어볼 / 골드4
https://www.acmicpc.net/problem/20056
 */
public class BOJ_20056 {

    private static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        List<FireBall> fireballs = new ArrayList<>();  // 현재 존재하는 파이어볼 저장

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());       // 질량
            int s = Integer.parseInt(st.nextToken());       // 속력
            int d = Integer.parseInt(st.nextToken());       // 방향

            fireballs.add(new FireBall(r, c, m, s, d));
        }

        // K번 명령 수행
        for (int i = 0; i < K; i++) {

            // 이동 결과 grid에 저장
            List<FireBall>[][] grid = new ArrayList[N][N];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    grid[r][c] = new ArrayList<>();
                }
            }

            for (FireBall fireball : fireballs) {
                fireball.move();
                grid[fireball.r][fireball.c].add(fireball);
            }

            // 파이어볼 합치기 진행
            List<FireBall> next = new ArrayList<>();

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    List<FireBall> cell = grid[r][c];
                    int size = cell.size();

                    // 해당 칸에 파이어볼이 없는 경우
                    if (size == 0) {
                        continue;
                    }

                    // 파이어볼이 하나인 경우
                    if (size == 1) {
                        next.add(cell.get(0));
                        continue;
                    }

                    // 파이어볼이 2개 이상인 경우 합치기
                    int massSum = 0;
                    int speedSum = 0;

                    for (FireBall fireball : cell) {
                        massSum += fireball.m;
                        speedSum += fireball.s;
                    }

                    int newMass = massSum / 5;
                    if (newMass == 0) {
                        continue; // 질량 0이면 소멸
                    }

                    int newSpeed = speedSum / size;
                    // 방향이 모두 짝수인지 홀수인지 판단
                    int[] dirs = decideDirection(cell)
                            ? new int[]{0, 2, 4, 6}
                            : new int[]{1, 3, 5, 7};

                    // 4개의 새로운 파이어볼 생성
                    for (int d : dirs) {
                        next.add(new FireBall(r, c, newMass, newSpeed, d));
                    }
                }
            }

            fireballs = next; // 갱신된 파이어볼 목록으로 교체
        }

        int result = 0;
        for (FireBall fireball : fireballs) {
            result += fireball.m;
        }
        System.out.println(result);
    }

    private static boolean decideDirection(List<FireBall> fireballs) {
        boolean hasEven = false;
        boolean hasOdd = false;

        for (FireBall fireball : fireballs) {
            if (fireball.d % 2 == 0) {
                hasEven = true;
            } else {
                hasOdd = true;
            }
        }

        return !hasEven || !hasOdd;
    }

    private static class FireBall {
        private int r, c, m, s, d;

        public FireBall(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }

        private void move() {
            r = ((r + (s * dr[d])) % N + N) % N;
            c = ((c + (s * dc[d])) % N + N) % N;
        }
    }
}
