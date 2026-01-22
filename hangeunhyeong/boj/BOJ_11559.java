package hangeunhyeong.boj;

import java.io.*;
import java.util.*;
public class BOJ_11559 {
    static char[][] field = new char[12][6];
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static boolean[][] visited = new boolean[12][6];
    static boolean isPopped; // 터진 뿌요뿌요가 있는지

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 필드 입력받기
        for(int i = 0; i < 12; i++){
            String str = br.readLine();
            for(int j = 0; j < 6; j++){
                field[i][j] = str.charAt(j);
            }
        }

        int answer = 0;
        do{
            //visited 초기화
            for(int i = 0; i < 12; i++){
                for(int j = 0; j < 6; j++){
                    visited[i][j] = false;
                }
            }
            // isPopped 초기화
            isPopped = false;
            // 중력의 영향을 받아 아래로 떨어지기
            gravity();
            printPuyoPuyo();

            // 터질수 있는 블록찾기
            for(int i = 0; i < 12; i++){
                for(int j = 0; j < 6; j++){
                    if(!visited[i][j] && field[i][j] != '.'){
                        bfs(i, j);
                    }
                }
            }

            if(isPopped)
                answer++;
        }while(isPopped);

        System.out.println(answer);
    }
    public static void printPuyoPuyo(){
        System.out.println("----------------------");
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 6; j++){
                System.out.print(field[i][j]);
            }
            System.out.println();
        }

    }
    public static void bfs(int a, int b) {
        Queue<int[]> queue = new LinkedList<>();
        ArrayList<int[]> blocks = new ArrayList<>();
        queue.add(new int[]{a, b});
        visited[a][b] = true;
        while(!queue.isEmpty()){
            int[] puyo = queue.poll();
            blocks.add(puyo);
            int x = puyo[0];
            int y = puyo[1];

            for(int i = 0; i < 4; i++){
                int nextX = x + dx[i];
                int nextY = y + dy[i];
                if(0 <= nextX && nextX < 12 && 0<= nextY && nextY < 6 &&
                        !visited[nextX][nextY] && field[nextX][nextY] != '.' && field[nextX][nextY] == field[x][y]){
                    visited[nextX][nextY] = true;
                    queue.add(new int[]{nextX, nextY});
                }
            }
        }
        for(int i = 0; blocks.size() >= 4 && i < blocks.size(); i++){
            int x = blocks.get(i)[0];
            int y = blocks.get(i)[1];
            field[x][y] = '.';
            isPopped = true;
            System.out.printf("[%d][%d] \'.\'으로 변경\n", x, y);
        }
    }
    // 터진 블록 위에 있는 블록들이 내려오는 로직
    public static void gravity(){
        for(int i = 0; i < 6; i++){
            for(int j = 10; j >= 0; j--){
                while(field[j][i] != '.' && j + 1 <= 11 && field[j + 1][i] == '.'){
                    field[j + 1][i] = field[j][i];
                    field[j][i] = '.';
                    j++;
                }
            }
        }
    }
}
