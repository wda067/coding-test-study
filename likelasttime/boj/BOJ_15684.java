import java.util.*;

/* N개의 세로선과 M개의 가로선 인접한 세로선 사이에는 가로선을 놓을 수 있다.
 * 각각의 세로선마다 가로선을 놓을 수 있는 위치의 개수는 H이다.
 * 모든 세로선이 같은 위치를 갖는다. 두 가로선이 연속하거나 서로 접하면 안 된다.
 * i번을 선택하면 세로선의 결과가 i번이 나와야 한다.
 * 추가해야 하는 가로선 개수와 최솟값을 구한다.
 * */
class Main {
    static int N;   // 세로선 개수
    static int M;   // 기존 가로선 개수
    static int H;   // 가로선 놓을 수 있는 높이
    static boolean[][] arr;
    static int answer = -1;
    static boolean isFind = false;

    // 모든 세로선 검사
    public static boolean moveAll() {
        for(int c = 0; c < N; c++) {
            if(!move(c)) return false;
        }
        return true;
    }

    // 한 세로선 이동 시뮬레이션
    public static boolean move(int c) {
        int start = c;

        for(int r = 0; r < H; r++) {

            // 오른쪽 이동
            if(c < N - 1 && arr[r][c]) {
                c++;
            }
            // 왼쪽 이동
            else if(c > 0 && arr[r][c - 1]) {
                c--;
            }
        }

        return start == c;
    }

    // 백트래킹 (조합)
    public static void dfs(int cnt, int target, int start) {

        if(isFind) return;

        if(cnt == target) {
            if(moveAll()) {
                isFind = true;
            }
            return;
        }

        for(int i = start; i < H * (N - 1); i++) {

            int r = i / (N - 1);
            int c = i % (N - 1);

            // 이미 가로선 있음
            if(arr[r][c]) continue;

            // 인접 가로선 체크
            if(c - 1 >= 0 && arr[r][c - 1]) continue;
            if(c + 1 < N - 1 && arr[r][c + 1]) continue;

            arr[r][c] = true;
            dfs(cnt + 1, target, i + 1);
            arr[r][c] = false;
        }
    }

    public static void simulate() {

        // 0개 추가
        if(moveAll()) {
            answer = 0;
            return;
        }

        // 1~3개 추가
        for(int i = 1; i <= 3; i++) {
            dfs(0, i, 0);
            if(isFind) {
                answer = i;
                return;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        H = sc.nextInt();

        arr = new boolean[H][N - 1];

        for(int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            arr[a - 1][b - 1] = true;
        }

        simulate();
        System.out.println(answer);
    }
}
