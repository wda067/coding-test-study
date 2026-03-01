package hangeunhyeong.boj;
import java.util.*;
import java.io.*;
/*
BOJ 21277 - 짠돌이 호석
완전탐색으로 풀 경우 O(N*M)의 시간복잡도 => 가능!
[풀이과정]
- puzzle1을 기준으로 행방향[-N2,...,N1+N2], 열방향[-M2,...,M1+M2] 으로 puzzle2를 움직인다
- 각 방향으로 움직이며 액자에 담을 수 있는지 검사한다
- 위 과정을 90도씩 회전해가며 반복
 */
public class BOJ_21277 {
    public static int[][] puzzle1, puzzle2;
    public static int N1, N2, M1, M2;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N1 = Integer.parseInt(str[0]);
        M1 = Integer.parseInt(str[1]);
        puzzle1 = new int[N1][M1];
        // puzzle1 입력받기
        for(int i = 0; i < N1; i++){
            String s = br.readLine();
            for(int j = 0; j < M1; j++){
                puzzle1[i][j] = s.charAt(j) - '0';
            }
        }
        str = br.readLine().split(" ");
        N2 = Integer.parseInt(str[0]);
        M2 = Integer.parseInt(str[1]);
        puzzle2 = new int[N2][M2];
        // puzzle2 입력받기
        for(int i = 0; i < N2; i++){
            String s = br.readLine();
            for(int j = 0; j < M2; j++){
                puzzle2[i][j] = (s.charAt(j) - '0') * 2;
            }
        }
        // 움직여가면서 액자의 최소넓이 찾기
        int min = 5000;
        for(int k = 0; k < 4; k++){
            for(int dr = -N2; dr <= N1 + N2; dr++){
                for(int dc = -M2; dc <= M1 + M2; dc++){
                    int s = combine(dr, dc);
                    // 결합 실패시
                    if(s == -1)
                        continue;
                    min = Math.min(min, s);
                }
            }
            // puzzle2 회전하기
            rotate();
        }
        System.out.println(min);
    }

    public static int combine(int dr, int dc){
       int dir = getDir(dr, dc);
        //puzzle1
       int[] r1 = {-dr, -dr, 0, 0};
       int[] c1 = {-dc, 0, -dc, 0};
       //puzzle2
       int[] r2 = {0, 0, dr, dr};
       int[] c2 = {0, dc, 0, dc};
       // 액자의 크기
        int N, M;
        N = Math.max(r1[dir] + N1, r2[dir] + N2) - Math.min(r1[dir], r2[dir]);
        M = Math.max(c1[dir] + M1, c2[dir] + M2) - Math.min(c1[dir], c2[dir]);
       int[][] frame = new int[N][M];

       for(int i = 0; i < N1; i++){
           for(int j = 0; j < M1; j++){
               frame[r1[dir] + i][c1[dir] + j] = puzzle1[i][j];
           }
       }
        for(int i = 0; i < N2; i++){
            for(int j = 0; j < M2; j++){
                frame[r2[dir] + i][c2[dir] + j] += puzzle2[i][j];
                if(frame[r2[dir] + i][c2[dir] + j] == 3)
                    return -1;
            }
        }
       return N * M;
    }
    public static int getDir(int dr, int dc){
        // puzzle1을 기준으로 puzzle2의 위치가
        // 왼쪽 위 또는 왼쪽
        if(dr <= 0 && dc < 0)
            return 0;
        // 오른쪽 위 또는 위쪽
        else if(dr < 0 && dc >= 0)
            return 1;
        // 왼쪽 아래 또는 아래쪽
        else if(dr > 0 && dc <= 0)
            return 2;
        // 오른쪽 아래 또는 오른쪽
        else
            return 3;
    }
    public static void rotate(){
        int[][] tmp = new int[M2][N2];
        int r = 0, c = N2 - 1;
        // 90도 회전
        for(int i = 0; i < N2; i++){
            r = 0;
            for(int j = 0; j < M2; j++){
                tmp[r++][c] = puzzle2[i][j];
            }
            c--;
        }
        N2 = tmp.length;
        M2 = tmp[0].length;
        puzzle2 = tmp;
    }
}
