import java.io.*;
import java.util.*;

/*
	N * N 격자
	토네이도를 시전하면 격자의 가운데 칸부터 토네이도의 이동이 시작된다.
	토네이도는 한 번에 1칸 이동
	토네이도가 한 칸 이동할 때마다 모래는 일정한 비율로 흩날린다.
	토네이도가 x에서 y로 이동하면 y의 모든 모래가 비율과 a가 적혀있는 칸으로 이동
	비율이 적혀있는 칸으로 이동하는 모래의 양은 y에 있는 모래의 해당 비율만큼이다. (소수점 아래는 버린다.)
	a로 이동하는 모래의 양은 비율이 적혀있는 칸으로 이동하지 않은 남은 모래의 양과 같다.
	모래가 이미 있는 칸으로 모래가 이동하면:
		모래의 양은 더해진다.
	토네이도는 (1, 1)까지 이동한 뒤 소멸한다.
	모래가 격자 밖으로 이동할 수도 있다.
	토네이도가 소멸되었을 때 격자의 밖으로 나간 모래의 양을 구하자.
 */
public class BOJ_20057 {

    static int N;
    static int[][] A;
    static int answer = 0;

    // 방향: 좌, 하, 우, 상
    static final int[] DX = {0, 1, 0, -1};
    static final int[] DY = {-1, 0, 1, 0};

    // ratio[d][i][j] : d방향일 때 (i,j)로 퍼지는 비율(%)
    static int[][][] ratio = {
            // ←
            {
                    {0, 0, 2, 0, 0},
                    {0,10, 7, 1, 0},
                    {5, 0, 0, 0, 0},
                    {0,10, 7, 1, 0},
                    {0, 0, 2, 0, 0}
            },
            // ↓
            {
                    {0, 0, 0, 0, 0},
                    {0, 1, 0, 1, 0},
                    {2, 7, 0, 7, 2},
                    {0,10, 0,10, 0},
                    {0, 0, 5, 0, 0}
            },
            // →
            {
                    {0, 0, 2, 0, 0},
                    {0, 1, 7,10, 0},
                    {0, 0, 0, 0, 5},
                    {0, 1, 7,10, 0},
                    {0, 0, 2, 0, 0}
            },
            // ↑
            {
                    {0, 0, 5, 0, 0},
                    {0,10, 0,10, 0},
                    {2, 7, 0, 7, 2},
                    {0, 1, 0, 1, 0},
                    {0, 0, 0, 0, 0}
            }
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        A = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        simulate();
        System.out.println(answer);
    }

    // 달팽이 이동
    static void simulate() {
        int x = N / 2;
        int y = N / 2;
        int d = 0;
        int move = 1;

        while (true) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < move; j++) {
                    x += DX[d];
                    y += DY[d];

                    spreadSand(x, y, d);

                    if (x == 0 && y == 0) return;
                }
                d = (d + 1) % 4;
            }
            move++;
        }
    }

    // 모래 퍼뜨리기
    static void spreadSand(int x, int y, int d) {
        int sand = A[x][y];
        A[x][y] = 0;

        int used = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int p = ratio[d][i][j];
                if (p == 0) continue;

                int nx = x + i - 2;
                int ny = y + j - 2;

                int moved = sand * p / 100;
                used += moved;

                if (nx < 0 || ny < 0 || nx >= N || ny >= N)
                    answer += moved;
                else
                    A[nx][ny] += moved;
            }
        }

        // alpha
        int ax = x + DX[d];
        int ay = y + DY[d];
        int alpha = sand - used;

        if (ax < 0 || ay < 0 || ax >= N || ay >= N)
            answer += alpha;
        else
            A[ax][ay] += alpha;
    }
}