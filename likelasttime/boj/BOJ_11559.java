import java.util.*;
/*
    뿌요는 아래에 바닥이나 다른 뿌요가 나올 때까지 아래로 떨어진다.
    같은 색 뿌요가 4개 이상 상하좌우로 연결되어 있으면
    	연결된 같은 색 뿌요들이 한꺼번에 없어진다.
    중력의 영향을 받아 차례대로 아래로 떨어진다.
    터질 수 있는 뿌요가 여러 그룹이 있다면 동시에 터져야 하고 여러 그룹이 터지더라도 한번의 연쇄가 추가된다.
    연쇄가 몇 번 연속으로 일어날지 계산하기
*/
class BOJ_11559 {
    final static int[] DX = {-1, 1, 0, 0};
    final static int[] DY = {0, 0, -1, 1};
    final static int ROW_SIZE = 12;
    final static int COL_SIZE = 6;
    final static char R = 'R';
    final static char G = 'G';
    final static char B = 'B';
    final static char P = 'P';
    final static char Y = 'Y';
    final static char EMPTY = '.';

    static char[][] arr;
    static char[][] copied;
    static int answer = 0;

    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static boolean inRange(int x, int y) {
        return 0 <= x && x < ROW_SIZE && 0 <= y && y < COL_SIZE;
    }

    /*
     * origin을 target에 복사
     */
    public static void copy(char[][] origin, char[][] target) {
        for(int x=0; x<ROW_SIZE; x++) {
            for(int y=0; y<COL_SIZE; y++) {
                target[x][y] = origin[x][y];
            }
        }
    }

    public static boolean bfs(int x, int y, boolean[][] visit) {
        Queue<Position> que = new LinkedList();
        List<Position> lst = new ArrayList();
        int cnt = 0;		// 같은 색깔의 뿌요 갯수
        lst.add(new Position(x, y));
        que.offer(new Position(x, y));
        visit[x][y] = true;
        while(!que.isEmpty()) {
            cnt++;
            Position cur = que.poll();
            for(int d=0; d<4; d++) {
                int nx = DX[d] + cur.x;
                int ny = DY[d] + cur.y;
                // 격자를 벗어나거나 방문했거나 종류가 다르거나
                if(!inRange(nx, ny) || visit[nx][ny] || arr[x][y] != arr[nx][ny]) {
                    continue;
                }
                visit[nx][ny] = true;
                que.offer(new Position(nx, ny));
                lst.add(new Position(nx, ny));
            }
        }

        if(cnt < 4) {
            return false;
        }

        // 같은 색 뿌요가 4개 이상 상하좌우로 연결되어 있으면
        for(Position cur : lst) {
            // 연결된 같은 색 뿌요들이 한꺼번에 없어진다.
            copied[cur.x][cur.y] = EMPTY;
        }

        return true;
    }

    /*
     * 뿌요가 아래로 떨어진다
     * 아래부터 뿌요를 채운다.
     */
    static void down() {
        for (int y = 0; y < COL_SIZE; y++) {
            int writeX = ROW_SIZE - 1;  // 아래부터 채울 위치

            // 아래 → 위로 뿌요 수집
            for (int x = ROW_SIZE - 1; x >= 0; x--) {
                if (copied[x][y] != EMPTY) {		// 빈 공간이 아니면(뿌요)
                    arr[writeX][y] = copied[x][y];
                    writeX--;
                }
            }

            // 남은 칸은 빈칸으로
            for (int x = writeX; x >= 0; x--) {
                arr[x][y] = '.';
            }
        }
    }

    public static void simulate() {
        while(true) {
            boolean flag = false;
            boolean[][] visit = new boolean[ROW_SIZE][COL_SIZE];
            copy(arr, copied);		// arr을 copied로 복사
            for(int x=0; x<ROW_SIZE; x++) {
                for(int y=0; y<COL_SIZE; y++) {
                    // 빈 공간(.)이거나 이미 방문했거나
                    if(arr[x][y] == EMPTY || visit[x][y]) {
                        continue;
                    }
                    boolean result = bfs(x, y, visit);
                    if(result) {
                        flag = true;
                    }
                }
            }

            if(!flag) {
                break;
            }

            answer++;
            // 뿌요가 아래로 떨어진다.
            down();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        arr = new char[ROW_SIZE][COL_SIZE];
        copied = new char[ROW_SIZE][COL_SIZE];
        for(int x=0; x<ROW_SIZE; x++) {
            String str = sc.next();
            for(int y=0; y<COL_SIZE; y++) {
                arr[x][y] = str.charAt(y);
            }
        }
        simulate();
        System.out.print(answer);
    }
}