package hangeunhyeong.boj;
/*
BOJ 21609 - 상어 중학교
[느낀 점]
참.....문제가......복잡하다...
풀면서 코드기본기가 중요하다는 걸 느꼈고 중요한 로직들은 외워둬야겠다 ex) 중력 로직, 회전 로직 등
문제 이해도 한 번 잘못하니까 시간을 엄청 많이 버렸다 문제이해를 잘하자

[코드 설명]
grid : 블록들로 이루어진 2차원 배열, 문제에서 말하는 격자를 의미
blockGroups : grid에서 찾을 수 있는 모든 blockGroup들

1. grid에서 블록그룹을 찾고 그 중 조건에 만족하는 블록그룹 하나를 반환(블록그룹을 찾을 수 없다면 7번으로)
    - 시간복잡도 O(N^4) : 무지개 재방문 때문에 겪자 대부분을 훑을 수도 있음
2. 반환된 블록그룹에 해당하는 블록들을 grid에서 제거 후 점수기록
    - 시간복잡도 O(N^2)
3. 중력 작용
    - 시간복잡도 O(N^3) : 최대 (N - 1)칸 내려올 수 있음
4. 90도 반시계방향으로 회전
    - 시간복잡도 O(N^2)
5. 중력 작용
    - 시간복잡도 O(N^3)
6. 1번으로 이동
    - 전체 while문 시간복잡도 O(N^2/2) : 한 턴 돌 때 마다 최소 2개가 사라지고 블록그룹이 없어질때까지 반복하므로 (N^2/2)이다
7. 지금까지 얻은 점수를 출력
=> 한 턴당 O(N^4), 총 시간복잡도 O(N^6)
 */
import java.io.*;
import java.util.*;
public class BOJ_21609 {
    public static Block[][] grid;
    public static int N, M;
    public static List<BlockGroup> blockGroups = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        grid = new Block[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                grid[i][j] = new Block(i, j, Integer.parseInt(st.nextToken()));
            }
        }
        int score = 0;

        while(findBlockGroup() > 0){
            BlockGroup removedGroup = removeBlockGroup();
//            printGrid();
            score += (int)Math.pow(removedGroup.blocks.size(), 2);
//            System.out.printf("%d (+%d)\n", score, removedGroup.blocks.size());
            down();
//            printGrid();
            rotate90();
//            printGrid();
            down();
//            printGrid();
        }

        System.out.println(score);
    }

    public static void printGrid() {
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(grid[i][j] != null)
                    System.out.printf("%d ", grid[i][j].color);
                else
                    System.out.printf("  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int findBlockGroup(){
        blockGroups = new ArrayList<>();
        // 격자에 있는 모든 블록 방문여부 초기화
        for(int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                if(grid[i][j] != null)
                    grid[i][j].visited = false;
            }
        }
        // 격자에서 블록그룹 찾기
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                // 방문하지 않은 일반블록일 때 dfs탐색
                if(grid[i][j] != null && !grid[i][j].visited && grid[i][j].color > 0){
                    cnt = 0;
                    int before = blockGroups.size();
                    dfs(grid[i][j], null, grid[i][j].color);
                    //블록그룹이 생성되었고 무지개인 블럭은 방문 여부 초기화
                    if(blockGroups.size() > before){
                        for(Block block : blockGroups.getLast().blocks){
                            if(block.color == 0)
                                block.visited = false;
                        }
                    }

                }
            }
        }
        return blockGroups.size();


        // 디버깅
//        System.out.printf("최종블록그룹 : 개수(%d) 색(%d) 기준블록[%d][%d]\n", finalBlockGroup.blocks.size(), finalBlockGroup.color, finalBlockGroup.standard / N, finalBlockGroup.standard % N);

    }

    public static BlockGroup removeBlockGroup() {
        // 블록 그룹들 중 한 개 찾기
        int maxBlockCnt = 0;
        int maxRainbow = -1;
        int maxStandard = -1;
        BlockGroup finalBlockGroup = null;

        // order by 블록 수 desc, 무지개블록 수 desc, 기준블록번호 desc
        for(BlockGroup blockGroup : blockGroups){
//            System.out.printf("블록 그룹 : 개수(%d) 색(%d) 기준블록[%d][%d]\n", blockGroup.blocks.size(), blockGroup.color, blockGroup.standard / N,blockGroup.standard % N);
            int blockCnt, rainbow, standard;
            blockCnt = blockGroup.blocks.size();
            rainbow = blockGroup.rainbow;
            standard = blockGroup.standard;
            if(blockCnt > maxBlockCnt){
                maxBlockCnt = blockCnt;
                maxRainbow = rainbow;
                maxStandard = standard;
                finalBlockGroup = blockGroup;
            }
            else if(blockCnt == maxBlockCnt){
                if(rainbow > maxRainbow){
                    maxBlockCnt = blockCnt;
                    maxRainbow = rainbow;
                    maxStandard = standard;
                    finalBlockGroup = blockGroup;
                } else if (rainbow == maxRainbow) {
                    if(standard > maxStandard){
                        maxBlockCnt = blockCnt;
                        maxRainbow = rainbow;
                        maxStandard = standard;
                        finalBlockGroup = blockGroup;
                    }
                }
            }
        }

        // 격자에서 블록제거
        for(Block block : finalBlockGroup.blocks){
            grid[block.x][block.y] = null;
        }
        return finalBlockGroup;
    }

    public static void down(){
        for(int j = 0; j < N; j++){
            for(int i = N - 1; i >= 0; i--){
                // 일반블록/무지개블록 밑에 아무 블록도 없으면 내려감
                int idx = i;
                while(idx + 1 < N && grid[idx][j] != null && grid[idx][j].color != -1 && grid[idx + 1][j] == null){
                    Block tmp = grid[idx][j];
                    grid[idx][j] = grid[idx + 1][j];
                    grid[idx + 1][j] = tmp;
                    tmp.x = idx + 1;
                    tmp.y = j;
                    idx++;
                }
            }
        }
    }
    public static void rotate90(){
        Block[][] temp = new Block[N][N];
        int r = 0, c = 0;
        for(int i = 0; i < N; i++){
            r = 0;
            for(int j = N - 1; j >= 0; j--){
                temp[r][c] = grid[i][j];
                if(temp[r][c] != null){
                    temp[r][c].x = r;
                    temp[r][c].y = c;
                }
                r++;
            }
            c++;
        }
        grid = temp;
    }
    public static int cnt;
    public static int[] dx = {-1, 0, 0, 1};
    public static int[] dy = {0, -1, 1, 0};

    public static void dfs(Block block, BlockGroup blockGroup, int color){
        block.visited = true;
        cnt++;  // 블록의 수 count

        // 블록그룹에 속하는 블록이면 해당 그룹에 추가
        if(blockGroup != null){
            blockGroup.add(block);
        }

        for(int i = 0; i < 4; i++){
            int nextX = block.x + dx[i];
            int nextY = block.y + dy[i];
            // 범위 밖이면 pass
            if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N)
                continue;
            // 빈 공간이면
            if(grid[nextX][nextY] == null)
                continue;
            Block nextBlock = grid[nextX][nextY];
            // 방문하지 않았고 무지개 블록이거나 블록의 색이 같은 경우
            if (!nextBlock.visited && (nextBlock.color == 0 || nextBlock.color == color)) {
                // 블록그룹 생성가능 시 블록그룹 초기화
                if(blockGroup == null){
                    blockGroup = new BlockGroup(new ArrayList<Block>(), block.color, block.x * N + block.y);
                    blockGroup.add(block);
                    blockGroups.add(blockGroup);
                }
                dfs(nextBlock, blockGroup, color);
            }
        }
    }
    public static class BlockGroup{
        List<Block> blocks;
        int color;
        int standard;
        int rainbow;
        public BlockGroup(List<Block> blocks, int color, int standard){
            this.blocks = blocks;
            this.color = color;
            this.standard = standard;
            this.rainbow = 0;
        }
        public void add(Block block){
            this.blocks.add(block);
            if(block.color == 0){
                rainbow++;
            }
        }
    }
    public static class Block{
        int x, y;
        int color;
        boolean visited;
        public Block(int x, int y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.visited = false;
        }
    }
}
