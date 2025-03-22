package pkl0912.boj;
import java.util.*;
import java.io.*;

public class BOJ_14002 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int[] dp = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i<n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        dp[0] = 1;
        int lis = 1;
        for(int i = 1; i<n; i++){
            dp[i] = 1;
            for(int j = 0; j<i; j++){
                if(arr[j]<arr[i]){
                    dp[i] = Math.max(dp[i], dp[j]+1);
                    lis = Math.max(lis, dp[i]);
                }
            }
        }
        Stack<Integer> stack = new Stack<>();
        for(int i = n-1; i>=0; i--){
            if(dp[i]==lis){
                stack.push(arr[i]);
                lis--;
            }
        }
        System.out.println(stack.size());
        while(!stack.isEmpty()){
            System.out.print(stack.pop()+" ");
        }
        
    }
}
