package hangeunhyeong.boj;
import java.io.*;
import java.util.*;
/*
BOJ 1781 - 컵라면
[접근]
N개의 문제, N개의 시간 슬롯
각 문제는 deadline 이하의 슬롯에 들어갈 수 있다
ex) (3, 1) 인 경우 1, 2, 3 슬롯에 들어갈 수 있다
컵라면의 합이 최대가 되기 위해서는 문제수가 deadline을 초과하는 경우 최솟값을 빼주어야한다
[느낀점]
풀이 과정이 직관적으로 이해하기 어려웠다.
 */
public class BOJ_1781 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] tasks = new int[N][2];
        for(int i = 0; i < N; i++){
            String[] str = br.readLine().split(" ");
            int[] task = new int[2];
            task[0] = Integer.parseInt(str[0]);
            task[1] = Integer.parseInt(str[1]);
            tasks[i] = task;
        }
        Arrays.sort(tasks, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int answer = 0;
        for(int[] task : tasks){
            int deadline = task[0];
            int cupramyeon = task[1];
            // 최소힙에 컵라면 개수 추가
            minHeap.add(cupramyeon);
            // 최소힙의 크기가 deadline이하가 될때까지 최솟값을 제거
            while(minHeap.size() > deadline){
                minHeap.poll();
            }
        }
        // 최소힙에 들어있는 컵라면수의 총합 구하기
        while(!minHeap.isEmpty()){
            answer += minHeap.poll();
        }
        System.out.println(answer);
    }
}
