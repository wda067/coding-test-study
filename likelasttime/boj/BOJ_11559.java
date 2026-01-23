import java.util.*;
/*
    뿌요는 아래에 바닥이나 다른 뿌요가 나올 때까지 아래로 떨어진다.
    같은 색 뿌요가 4개 이상 상하좌우로 연결되어 있으면
    	연결된 같은 색 뿌요들이 한꺼번에 없어진다.
    중력의 영향을 받아 차례대로 아래로 떨어진다.
    터질 수 있는 뿌요가 여러 그룹이 있다면 동시에 터져야 하고 여러 그룹이 터지더라도 한번의 연쇄가 추가된다.
    연쇄가 몇 번 연속으로 일어날지 계산하기
*/
class Main {
    final static int[] DX = {-1, 1, 0, 0};
    final static int[] DY = {0, 0, -1, 1};
    final static int ROW_SIZE = 12;		// 세로 크기
    final static int COL_SIZE = 6;		// 가로 크기
    final static char R = 'R';
    final static char G = 'G';
    final static char B = 'B';
    final static char P = 'P';
    final static char Y = 'Y';
    final static char EMPTY = '.';

    static char[][] arr;		// ROW_SIZE * COL_SIZE 크기의 배열
    static int answer = 0;		// 연쇄 횟수

    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /*
     * (x, y)가 격자 내에 있다면 true 반환
     */
    public static boolean inRange(int x, int y) {
        return 0 <= x && x < ROW_SIZE && 0 <= y && y < COL_SIZE;
    }

    /*
     * (x, y) 뿌요에서 시작해서 상하좌우로 인접한 같은 뿌요를 찾는 BFS 탐색
     * 4개 이상 찾으면 해당 뿌요들은 사라지고, true 반환
     */
    public static boolean bfs(int x, int y, boolean[][] visit) {
        Queue<Position> que = new LinkedList();
        List<Position> lst = new ArrayList();		// arr[x][y]와 같은 뿌요의 위치를 담는 리스트
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

        if(cnt < 4) {	// 같은 색 뿌요가 4개 미만이라면
            return false;
        }

        // 같은 색 뿌요가 4개 이상 상하좌우로 연결되어 있으면
        for(Position cur : lst) {
            // 연결된 같은 색 뿌요들이 한꺼번에 없어진다.
            arr[cur.x][cur.y] = EMPTY;
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
                if (arr[x][y] != EMPTY) {		// 빈 공간이 아니면(뿌요)
                    char tmp = arr[x][y];
                    arr[x][y] = EMPTY;
                    arr[writeX--][y] = tmp;
                }
            }
        }
    }

    public static void simulate() {
        while(true) {	// 뿌요가 연쇄적으로 터질동안
            boolean flag = false;		// 배열 전체를 탐색하며 연쇄적으로 터진 뿌요가 있는지
            boolean[][] visit = new boolean[ROW_SIZE][COL_SIZE];
            for(int x=0; x<ROW_SIZE; x++) {
                for(int y=0; y<COL_SIZE; y++) {
                    // 빈 공간(.)이거나 이미 방문했거나
                    if(arr[x][y] == EMPTY || visit[x][y]) {
                        continue;
                    }
                    boolean result = bfs(x, y, visit);
                    if(result) {	// 연쇄적으로 터진 뿌요가 있다면
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