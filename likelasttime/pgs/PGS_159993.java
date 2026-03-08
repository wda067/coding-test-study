import java.util.*;
/*
    1 * 1 크기의 칸들로 이루어진 직사각형 격자 형태의 미로
    각 칸은 통로 또는 벽이다.
    벽으로 된 칸은 지나갈 수 없고 통로로 된 칸으로만 이동할 수 있다.
    통로들 중 한 칸에는 미로를 빠져나가는 문이 있고, 레버를 당겨서만 열 수 있다.
    아직 레버를 당기지 않았더라도 출구가 있는 칸을 지나갈 수 있다.
    미로에서 한 칸을 이동하는데 1초가 걸린다고 할 때 최대한 빠르게 미로를 나가는데 걸리는 시간을 구하기
*/
class PGS_159993 {
    static class Position {
        int x;
        int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    final static Character WALL = 'X';
    final static Character EXIT = 'E';
    final static Character LEVER = 'L';
    final static int[] DX = {-1, 1, 0, 0};
    final static int[] DY = {0, 0, -1, 1};

    static String[] MAPS;
    static int N;       // 세로 크기
    static int M;       // 가로 크기
    static Queue<Position> que;
    static boolean[][] visit;
    static Position lever;      // 레버 위치

    public static boolean inRange(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < M;
    }

    public static void findStart() {
        // 시작점을 큐에 넣기
        for(int x=0; x<N; x++) {
            for(int y=0; y<M; y++) {
                if(MAPS[x].charAt(y) == 'S') {
                    que.offer(new Position(x, y));
                    visit[x][y] = true;
                    return;
                }
            }
        }
    }

    public static int bfs(char destination) {
        int distance = 0;
        while(!que.isEmpty()) {
            int cnt = que.size();
            distance++;
            for(int i=0; i<cnt; i++) {
                Position cur = que.poll();
                if(MAPS[cur.x].charAt(cur.y) == destination) {
                    if(destination == LEVER) {
                        lever = cur;
                    }
                    return distance - 1;
                }
                for(int d=0; d<4; d++) {
                    int nx = DX[d] + cur.x;
                    int ny = DY[d] + cur.y;
                    // 격자를 벗어나거나 이미 방문했거나 벽이거나
                    if(!inRange(nx, ny) || visit[nx][ny] || MAPS[nx].charAt(ny) == WALL) {
                        continue;
                    }
                    //System.out.println(nx + " " + ny + " 거리 " + distance);
                    visit[nx][ny] = true;
                    que.offer(new Position(nx, ny));
                }
            }
        }
        return -1;
    }

    public static void init() {
        que = new LinkedList();
        visit = new boolean[N][M];
    }

    public int solution(String[] maps) {
        MAPS = maps;
        N = maps.length;
        M = maps[0].length();
        init();
        findStart();
        // 시작 지점 -> 레버가 있는 위치까지의 거리
        int toLever = bfs(LEVER);
        if(toLever == -1) {
            return -1;
        }
        init();
        // 레버가 있는 위치를 큐에 추가
        que.offer(lever);
        visit[lever.x][lever.y] = true;
        int toExit = bfs(EXIT);
        if(toExit == -1) {
            return -1;
        }
        return toLever + toExit;      // 미로를 탈출하는데 걸리는 시간(탈출 못 하면 -1)
    }
}