import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
백준 / 사냥꾼 / 골드4
https://www.acmicpc.net/problem/8983
 */
public class BOJ_8983 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());  //사대의 수
        int N = Integer.parseInt(st.nextToken());  //동물의 수
        int L = Integer.parseInt(st.nextToken());  //사정거리

        int[] sadae = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            sadae[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(sadae);  //이진 탐색을 위해 정렬

        int result = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            //(x, y)에서 가장 가까운 사대를 탐색
            int s = 0;
            int e = M - 1;
            boolean isCaught = false;

            while (s <= e) {
                int m = (s + e) / 2;
                int d = Math.abs(sadae[m] - x) + y;
                if (d <= L) {
                    isCaught = true;
                    break;
                }

                if (sadae[m] < x) {  //사대가 왼쪽에 있을 때
                    s = m + 1;
                } else if (sadae[m] >= x) {  //사대가 오른쪽에 있을 때
                    e = m - 1;
                }
            }

            if (isCaught) {
                result++;
            }
        }

        System.out.println(result);
    }
}
