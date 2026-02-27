package hhd1337.boj;
/*
[구현 아이디어2 - 이중 for문에서 j를 i+1부터 시작하지 말고, 
이전에 멈춘 j부터 시작하도록 함. 즉 j를 전역으로 관리, 절대 감소시키지 않음.
왜냐하면 배열을 정렬 해놓았기 때문에 이번 루프 j는 저번루프 j이상의 값일 수 밖에 없음.]
1. 정렬을 함
2. M 이상이면서 최솟값인 전역변수 선언 (minValidDiff)
2. 정렬된 배열 앞에서 부터 한 요소씩 선택하여
   - 오른쪽으로 한칸씩 가며 차이가 M 이상이 되는 수를 찾는다.(찾으면 멈춤, 찾은 값은 전역에서 관리.)
   ... 배열 끝에서 두번째요소까지 진행한다.
3. minValidDiff 출력한다.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class BOJ_20057 {
    private static int[][] map;
    private static int N;

    private static int totalSandOut = 0;

    private static int tornadoRow;
    private static int tornadoCol;

    private static List<Direction> tornadoDirectionOrder = new ArrayList<>();

    private static enum Direction {LEFT, DOWN, RIGHT, UP}

    // LEFT(0), DOWN(1), RIGHT(2), UP(3)
    private static final int[] dRow = {0, 1, 0, -1};
    private static final int[] dCol = {-1, 0, 1, 0};

    private static final int[][][] sandSpreadPattern = {
            // LEFT
            {
                    {0, 0, 2, 0, 0},
                    {0, 10, 7, 1, 0},
                    {5, 0, 0, 0, 0},
                    {0, 10, 7, 1, 0},
                    {0, 0, 2, 0, 0}
            },
            // DOWN
            {
                    {0, 0, 0, 0, 0},
                    {0, 1, 0, 1, 0},
                    {2, 7, 0, 7, 2},
                    {0, 10, 0, 10, 0},
                    {0, 0, 5, 0, 0}
            },
            // RIGHT
            {
                    {0, 0, 2, 0, 0},
                    {0, 1, 7, 10, 0},
                    {0, 0, 0, 0, 5},
                    {0, 1, 7, 10, 0},
                    {0, 0, 2, 0, 0}
            },
            // UP
            {
                    {0, 0, 5, 0, 0},
                    {0, 10, 0, 10, 0},
                    {2, 7, 0, 7, 2},
                    {0, 1, 0, 1, 0},
                    {0, 0, 0, 0, 0}
            }
    };

    public static void main(String[] args) throws Exception {
        initMap();
        setTornadoDirectionOrder();

        for (Direction di : tornadoDirectionOrder) {
            moveOneBlock(di);
        }

        System.out.println(totalSandOut);
    }

    private static void moveOneBlock(Direction di) {
        int dirIndex = di.ordinal();

        // 한 칸 이동 (도착 칸이 y)
        tornadoRow += dRow[dirIndex];
        tornadoCol += dCol[dirIndex];

        // y 칸 모래 분산
        spreadSand(tornadoRow, tornadoCol, dirIndex);
    }

    private static void spreadSand(int yRow, int yCol, int dirIndex) {
        int sand = map[yRow][yCol];
        if (sand == 0) {
            return;
        }
        map[yRow][yCol] = 0;

        int used = 0;

        // 5x5 패턴 적용 (중심이 (2,2))
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int p = sandSpreadPattern[dirIndex][i][j];
                if (p == 0) {
                    continue;
                }

                int targetRow = yRow + (i - 2);
                int targetCol = yCol + (j - 2);

                int moved = sand * p / 100;
                used += moved;

                addSand(targetRow, targetCol, moved);
            }
        }

        // alpha: 진행 방향 1칸 앞
        int alpha = sand - used;
        addSand(yRow + dRow[dirIndex], yCol + dCol[dirIndex], alpha);
    }

    private static void addSand(int row, int col, int amount) {
        if (amount == 0) {
            return;
        }

        if (row < 1 || row > N || col < 1 || col > N) {
            totalSandOut += amount;
        } else {
            map[row][col] += amount;
        }
    }

    private static void setTornadoDirectionOrder() {
        tornadoDirectionOrder.clear();

        int totalMoves = N * N - 1;
        int stepLen = 1;
        int dirIndex = 0; // LEFT부터

        while (tornadoDirectionOrder.size() < totalMoves) {
            for (int repeat = 0; repeat < 2; repeat++) {
                Direction direction = Direction.values()[dirIndex];

                for (int k = 0; k < stepLen && tornadoDirectionOrder.size() < totalMoves; k++) {
                    tornadoDirectionOrder.add(direction);
                }
                dirIndex = (dirIndex + 1) % 4;
            }
            stepLen++;
        }
    }

    private static void initMap() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N + 1][N + 1];

        tornadoRow = (N + 1) / 2;
        tornadoCol = (N + 1) / 2;

        for (int r = 1; r <= N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 1; c <= N; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
            }
        }
    }
}