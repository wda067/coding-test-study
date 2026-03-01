import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.StringTokenizer;

/*
백준 / 상어 중학교 / 골드1
https://www.acmicpc.net/problem/21609
 */
public class BOJ_21609 {

    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};
    private static int N;
    private static int[][] grid;
    private static List<Group> groups;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());   // 격자 한 변의 크기
        int M = Integer.parseInt(st.nextToken());   // 색상의 개수

        // 격자 세팅
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        /*
        초기 격자의 모든 칸에 블록 하나씩
        검은색 블록 -> -1, 무지개 블록 -> 0
        일반 블록 -> M가지 색상 (1~5)

        연결된 블록의 집합에
        1. 동일한 색의 일반 블록 포함
        2. 검은색 블록 포함 X
        기준 블록 -> 무지개 블록 X and 행의 번호 최소 and 열의 번호 최소

        1. 크기가 가장 큰 블록 그룹 찾기 -> 여러 개라면 무지개 블록 수 최대 그룹 and 기준 블록 행 최대 and 열 최대
        2. 블록 그룹의 모든 블록 제거, B^2점 획득
        3. 격자 중력
        4. 90도 반시계 회전
        5. 격자 중력
         */

        int score = 0;
        while (true) {
            groups = new ArrayList<>();  // 현재 턴의 블록 그룹 목록 저장

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] > 0) {  // 일반 블록일 때만 그룹 탐색
                        bfs(new int[]{i, j});
                    }
                }
            }

            // -을 붙혀서 내림차순
            Optional<Group> group = groups.stream()
                    .sorted(Comparator.comparingInt((Group o) -> -o.size)        // 크기가 가장 큰 그룹
                            .thenComparingInt((Group o) -> -o.rainbowCount)      // 무지개 블록 수
                            .thenComparingInt((Group o) -> -o.standardBlock[0])  // 기준 블록 행
                            .thenComparingInt((Group o) -> -o.standardBlock[1])  // 기준 블록 열
                    )
                    .findFirst();

            // 블록 그룹이 존재하지 않으면 스톱
            if (group.isEmpty()) {
                break;
            }

            // 블록 그룹 제거 (-2를 빈칸으로 설정)
            List<int[]> candidates = group.get().candidates;
            for (int[] candidate : candidates) {
                grid[candidate[0]][candidate[1]] = -2;
            }

            score += candidates.size() * candidates.size();

            // 중력 작용
            moveDown();

            // 반시계 90도 회전
            rotate();

            // 중력 작용
            moveDown();
        }

        System.out.println(score);
    }

    private static void bfs(int[] pos) {
        Queue<int[]> q = new LinkedList<>();
        q.add(pos);

        boolean[][] visited = new boolean[N][N];
        visited[pos[0]][pos[1]] = true;

        // 블록 그룹의 후보 저장
        List<int[]> candidates = new ArrayList<>();
        candidates.add(new int[]{pos[0], pos[1]});

        // 현재 일반 블록의 색깔
        int color = grid[pos[0]][pos[1]];
        int rainbowCount = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            // 레인보우 블록 카운트
            if (grid[cur[0]][cur[1]] == 0) {
                rainbowCount++;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                    continue;
                }

                // 동일한 일반 블록 or 무지개 블록
                if (!visited[nr][nc] && (grid[nr][nc] == color || grid[nr][nc] == 0)) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                    candidates.add(new int[]{nr, nc});
                }
            }
        }

        // 후보가 2개 이상일 경우 블록 그룹
        if (candidates.size() >= 2) {
            // 기준 블록 좌표 계산
            int[] standardBlock = candidates.stream()
                    .filter(o -> grid[o[0]][o[1]] != 0)             // 무지개 블록 제외
                    .sorted(Comparator.comparingInt((int[] o) -> o[0])  // 행 기준 오름차순
                            .thenComparingInt((int[] o) -> o[1]))       // 열 기준 오름차순
                    .findFirst()
                    .get();
            groups.add(new Group(candidates.size(), rainbowCount, standardBlock, candidates));
        }
    }

    // 반시계 90도 회전
    private static void rotate() {
        int[][] rotated = new int[N][N];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                rotated[N - 1 - c][r] = grid[r][c];
            }
        }

        grid = rotated;
    }

    private static void moveDown() {
        for (int c = 0; c < N; c++) {
            int writeRow = N - 1;  // 다음에 블록이 채워질 위치

            for (int r = N - 1; r >= 0; r--) {
                // 검은색 블록일 때
                if (grid[r][c] == -1) {
                    writeRow = r - 1;  // 다음 블록은 검은 블록 위쪽에 채움
                    continue;
                }

                // 빈칸이 아닐 때
                if (grid[r][c] != -2) {
                    int temp = grid[r][c];
                    grid[r][c] = -2;
                    grid[writeRow--][c] = temp;
                }
            }
        }
    }

    private static class Group {
        private int size, rainbowCount;     // 그룹의 크기, 무지개 블록의 수
        private int[] standardBlock;        // 기준 블록의 위치
        private List<int[]> candidates;     // 후보들의 좌표 목록

        public Group(int size, int rainbowCount, int[] standardBlock, List<int[]> candidates) {
            this.size = size;
            this.rainbowCount = rainbowCount;
            this.standardBlock = standardBlock;
            this.candidates = candidates;
        }
    }
}
