import java.util.*;

/*
 * 크기가 N * N 격자
 * 초기에 격자의 모든 칸에 블록이 하나씩 있다.
 * 블록은 검은색 블록 / 무지개 블록 / 일반 블록이다.
 * 일반 블록은 M가지 색상이 있다.
 * 색은 M이하의 자연수(검은색 블록 = -1, 무지개 블록 = 0)
 * |r1 - r2| + |c1 - c2| = 1을 만족하는 (r1, c1)과 (r2, c2)를 인접한 칸이라고 한다.
 * 블록 그룹은 연결된 블록의 집합이다.
 * 그룹에는 일반 블록이 최소 하나가 있어야 한다.
 * 	일반 블록의 색은 모두 같아야 한다.
 * 	검은색 블록은 포함 X
 * 	무지개 블록은 갯수 제한이 없다.
 * 	그룹에 속한 블록의 개수는 2보다 크거나 같아야 한다.
 * 	임의의 한 블록에서 그룹에 속한 인접한 칸으로 이동해서 그룹에 속한 다른 모든 칸으로 이동할 수 있어야 한다.
 * 	기준 블록은 무지개 블록이 아닌 블록 중에서 행의 번호가 가장 작은 블록 > 열의 번호가 가장 작은 블록순이다.
 *
 * [오토 플레이 기능]
 * 블록 그룹이 존재하는 동안 계속해서 반복
 * 1. 크기가 가장 큰 블록 그룹 찾기 > 포함된 무지개 블록의 수가 가장 많은 블록 그룹 > 기준 블록의 행이 가장 큰거 > 열이 가장 큰거
 * 2. 찾은 블록 그룹의 모든 블록을 제거(B개를 제거하면 B^2만큼 점수 획득)
 * 3. 격자에 중력이 작용한다.
 * 4. 격자가 90도 반시계 방향으로 회전
 * 5. 격자에 중력이 작용한다.
 *
 * 격자에 중력이 작용하면
 * 	검은색 블록을 제외한 모든 블록이 행의 번호가 큰 칸으로 이동(다른 블록이나 격자의 경계를 만나기 전까지 계속됨)
 *
 * 오토 플레이가 모두 끝났을 때 획득한 점수의 합을 구하기
 */
class BOJ_21609 {
    static final int BLACK = -1;
    static final int RAINBOW = 0;
    static final int EMPTY = -2;

    static int N, M;
    static int[][] grid;
    static boolean[][] visited;
    static int score = 0;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static class Group implements Comparable<Group> {
        List<int[]> blocks = new ArrayList<>();
        int rainbowCnt;
        int stdX, stdY; // 기준 블록 (가장 작은 행, 열)

        @Override
        public int compareTo(Group o) {
            if (this.blocks.size() != o.blocks.size())
                return o.blocks.size() - this.blocks.size();
            if (this.rainbowCnt != o.rainbowCnt)
                return o.rainbowCnt - this.rainbowCnt;
            if (this.stdX != o.stdX)
                return o.stdX - this.stdX;
            return o.stdY - this.stdY;
        }
    }

    static Group bfs(int sx, int sy) {
        Queue<int[]> q = new LinkedList<>();
        List<int[]> rainbowList = new ArrayList<>();

        visited[sx][sy] = true;
        q.add(new int[]{sx, sy});

        Group g = new Group();
        g.blocks.add(new int[]{sx, sy});
        g.stdX = sx;
        g.stdY = sy;

        int color = grid[sx][sy];

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int d = 0; d < 4; d++) {
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visited[nx][ny]) continue;
                if (grid[nx][ny] == BLACK) continue;

                if (grid[nx][ny] == color || grid[nx][ny] == RAINBOW) {
                    visited[nx][ny] = true;
                    q.add(new int[]{nx, ny});
                    g.blocks.add(new int[]{nx, ny});

                    if (grid[nx][ny] == RAINBOW) {
                        g.rainbowCnt++;
                        rainbowList.add(new int[]{nx, ny});
                    } else {
                        // 기준 블록: 가장 작은 행 → 가장 작은 열
                        if (nx < g.stdX || (nx == g.stdX && ny < g.stdY)) {
                            g.stdX = nx;
                            g.stdY = ny;
                        }
                    }
                }
            }
        }

        // 무지개 블록 방문 초기화 (다른 그룹에서 재사용 가능)
        for (int[] r : rainbowList) {
            visited[r[0]][r[1]] = false;
        }

        if (g.blocks.size() < 2) return null;
        return g;
    }

    static void gravity() {
        for (int y = 0; y < N; y++) {
            int bottom = N - 1;

            for (int x = N - 1; x >= 0; x--) {
                if (grid[x][y] == BLACK) {
                    bottom = x - 1;
                }
                else if (grid[x][y] >= 0) {
                    int temp = grid[x][y];
                    grid[x][y] = EMPTY;
                    grid[bottom][y] = temp;
                    bottom--;
                }
            }
        }
    }

    static void rotate() {
        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                tmp[N - 1 - j][i] = grid[i][j];
        grid = tmp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        grid = new int[N][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = sc.nextInt();

        while (true) {
            visited = new boolean[N][N];
            List<Group> groups = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!visited[i][j] && grid[i][j] > 0) {
                        Group g = bfs(i, j);
                        if (g != null) groups.add(g);
                    }
                }
            }

            if (groups.isEmpty()) break;

            Collections.sort(groups);
            Group best = groups.get(0);

            int size = best.blocks.size();
            score += size * size;

            for (int[] b : best.blocks) {
                grid[b[0]][b[1]] = EMPTY;
            }

            gravity();
            rotate();
            gravity();
        }

        System.out.println(score);
    }
}
