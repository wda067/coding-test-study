import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
백준 / 가희와 탑 / 골드3
https://www.acmicpc.net/problem/24337
 */
public class BOJ_24337 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());  // 100,000
        int a = Integer.parseInt(st.nextToken());  // 가희
        int b = Integer.parseInt(st.nextToken());  // 단비

        /*
        왼쪽에서 a개, 오른쪽에서 b개가 보이려면
        최소 a+b-1개의 건물이 필요
         */
        if (a + b - 1 > N) {
            System.out.println(-1);
            return;
        }

        StringBuilder sb = new StringBuilder();
        int max = Math.max(a, b);       // 가장 높은 빌딩
        int remain = N - (a + b - 1);   // a와 b를 제외한 나머지 1의 건물 수

        if (a == 1) {
            /*
            10 1 3
            3 1 1 1 1 1 1 1 2 1
             */

            // 첫 건물이 제일 높아야 왼쪽에서 1개만 보임
            sb.append(max).append(" ");

            // 중간에 1인 빌딩 설정
            for (int i = 0; i < remain; i++) {
                sb.append(1).append(" ");
            }

            // 가장 높은 빌딩을 제외한 -> b-1 만큼 내림차순으로 높이 설정
            for (int i = b - 1; i >= 1; i--) {
                sb.append(i);
                if (i != 1) {
                    sb.append(" ");
                }
            }
        } else {
            /*
            10 2 4
            1 1 1 1 1 1 4 3 2 1
             */

            // 사전 순이므로 1부터 채움
            for (int i = 0; i < remain; i++) {
                sb.append(1).append(" ");
            }

            // 1 ~ a-1 만틈 오름차순으로 높이 설정
            for (int i = 1; i <= a - 1; i++) {
                sb.append(i).append(" ");
            }

            // 피크
            sb.append(max).append(" ");

            // b-1 만큼 내림차순으로 높이 설정
            for (int i = b - 1; i >= 1; i--) {
                sb.append(i);
                if (i != 1) {
                    sb.append(" ");
                }
            }
        }

        System.out.println(sb);
    }
}
