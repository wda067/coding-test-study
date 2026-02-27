package hangeunhyeong.boj;
import java.util.*;
import java.io.*;
/*
BOJ 20057 - 마법사 상어와 토네이도
대표적인 구현문제라서 문제에서 요구하는대로 구현하면 됨
 */
public class BOJ_20057 {
    public static int[][] tornado;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        tornado = new int[N][N];
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                tornado[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int x = N / 2, y = N / 2;
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        int outside = 0;
        // 나선형으로 토네이도 발생시키기
        for(int i = 0; !isOutside(x, y); i++){
            int dir = i % 4;
            for(int j = 0; j < i / 2 + 1 && !isOutside(x, y); j++){
                x = x + dx[dir];
                y = y + dy[dir];
                if(isOutside(x, y))
                    break;
                // 모래 이동
                outside += moveSand(x, y, dir);
            }
        }
        System.out.println(outside);
    }
    public static int moveSand(int x, int y, int dir){
        int sand = tornado[x][y];
        int[] dx = {-2, -1, -1, -1, 0, 1, 1, 1, 2, 0};
        int[] dy = {0, -1, 0, 1, -2, -1, 0, 1, 0, -1};
        int[] wind = new int[10];   // 날아가는 모래의 양
        wind[0] = (int)(sand * 0.02);
        wind[1] = (int)(sand * 0.1);
        wind[2] = (int)(sand * 0.07);
        wind[3] = (int)(sand * 0.01);
        wind[4] = (int)(sand * 0.05);
        wind[5] = (int)(sand * 0.1);
        wind[6] = (int)(sand * 0.07);
        wind[7] = (int)(sand * 0.01);
        wind[8] = (int)(sand * 0.02);
        wind[9] = sand;
        // wind[0...8]이 모두 정수형으로 변환되어 ⍺(wind[9])는 따로 계산
        for(int i = 0; i < 9; i++)
            wind[9] -= wind[i];

        int outside = 0;
        int movedX, movedY;
        tornado[x][y] = 0;
        for(int i = 0; i < 10; i++){
            switch(dir){
                case 0: // 왼쪽
                    movedX = x + dx[i];
                    movedY = y + dy[i];
                    break;
                case 1: // 아래쪽
                    movedX = x - dy[i];
                    movedY = y + dx[i];
                    break;
                case 2: // 오른쪽
                    movedX = x + dx[i];
                    movedY = y - dy[i];
                    break;
                default:    // 위쪽
                    movedX = x + dy[i];
                    movedY = y - dx[i];
            }
            // 모래가 격자내부로 날아간 경우
            if(!isOutside(movedX, movedY))
                tornado[movedX][movedY] += wind[i];
            // 모래가 격자바깥쪽으로 날아간 경우
            else
                outside += wind[i];
        }
        return outside;
    }
    public static boolean isOutside(int x, int y){
        int N = tornado.length;
        return x < 0 || x >= N || y < 0 || y >= N;
    }
}
