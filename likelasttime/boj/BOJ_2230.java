import java.util.*;
import java.io.*;
/*
 * N개의 정수로 이루어진 수열이 있다.
 * 두 수를 골랐을 때 그 차이가 M 이상이면서 제일 작은 경우를 구하기
 * M이상이면서 가장 작은 차이를 출력한다
 */
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(arr);

        int left = 0;
        int right = 0;
        int answer = Integer.MAX_VALUE;

        while (left < N && right < N) {
            int diff = arr[right] - arr[left];

            if (diff < M) {
                right++;
            } else {
                answer = Math.min(answer, diff);
                left++;
            }

            if (left == right) {
                right++;
            }
        }

        System.out.println(answer);
    }
}