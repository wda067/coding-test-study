package hangeunhyeong.boj;

import java.io.*;
import java.util.*;
/*
BOJ 2230 - 수고르기
 */
public class BOJ_2230 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, M;
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        int[] A = new int[N];
        for(int i = 0; i < N; i++){
            A[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(A);
        int left, right, min;
        left = 0;   right = 0;  min = A[N - 1] - A[0];
        while(left <= right){
            while(right != N && A[right] - A[left] < M){
                right++;
            }
            if(right == N)
                break;
            min = Math.min(min, A[right] - A[left]);
            if(min == M)
                break;
            left++;
        }
        System.out.println(min);
    }
}
