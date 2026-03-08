import java.util.*;
import java.io.*;
/*
	N개의 문제를 주고 각각의 문제를 풀었을 때 컵라면을 몇 개 줄 것인지를 제시
	받을 수 있는 최대 컵라면 수를 구한다.
	각 문제의 데드라인은 N 이하의 자연수
	각 문제를 풀 때 받을 수 있는 컵라면 수와 최대로 받을 수 있는 컵라면 수는 모두 2^31보다 작은 자연수
*/

import java.util.*;
import java.io.*;

class BOJ_1781 {

    static class Cupramen {
        int deadline;
        int cnt;

        Cupramen(int d, int c) {
            deadline = d;
            cnt = c;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Cupramen[] arr = new Cupramen[N];

        for(int i=0;i<N;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            arr[i] = new Cupramen(d,c);
        }

        Arrays.sort(arr, (a,b)-> a.deadline - b.deadline);

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(Cupramen cur : arr){
            pq.offer(cur.cnt);

            if(pq.size() > cur.deadline){
                pq.poll();
            }
        }

        long answer = 0;
        while(!pq.isEmpty()){
            answer += pq.poll();
        }

        System.out.println(answer);
    }
}