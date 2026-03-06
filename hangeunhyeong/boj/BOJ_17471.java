package hangeunhyeong.boj;

import java.io.*;
import java.util.*;
/*
BOJ 17471 - 게리맨더링
[접근]
N이 10이하이므로 완전탐색으로 답을 구할 수 있다는 엄청난 힌트가 주어진것
A, B 선거구가 있다고 가정
각 구역이 A 또는 B 선거구로 선정될 것임 => 총 경우의 수는 N^10
비트마스크 : 1 ~ (2^N - 2)   단, 0과 2^N - 1 은 선거구가 한 개이므로 제외
2가지 경우의 수는 보통 비트마스크로 표현
비트마스크에 의해 선거구가 정해진 구역들을 탐색하면서 조건에 맞는지 검사
=> 선거구 A인 구역 하나, 선거구 B인 구역 하나를 시작지점으로 bfs/dfs 탐색하여 모든 구역들을 다 방문할 수 있어야함
[느낀점]
배운게 참 많은 문제인듯..
 */
public class BOJ_17471 {
    public static int sum, N;
    public static int[] population;
    public static ArrayList<Integer>[] adjList;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        sum = 0;
        population = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 구역별 인구수, 총 인구수
        for(int i = 1; i <= N; i++){
            population[i] = Integer.parseInt(st.nextToken());
            sum += population[i];
        }
        // 인접리스트 초기화
        adjList = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 그래프 구축
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            int adjCnt = Integer.parseInt(st.nextToken());
            for(int j = 0; j < adjCnt; j++){
                adjList[i].add(Integer.parseInt(st.nextToken()));
            }
        }
        int min = sum;
        // 0000... 1111...(선거구가 1개인 경우) 제외
        for(int mask = 1; mask < (int)(Math.pow(2, N) - 2); mask++){
            // 중복제거 : 비트반전시 A, B 가 바뀔뿐 결과에는 영향이 없음
            if((0b1111111111 ^ mask) < mask)
                continue;
            int diff = bfs(mask);
            if(diff != -1){
                min = Math.min(min, diff);
            }
        }
        if(min == sum)
            min = -1;
        System.out.println(min);

    }
    public static int bfs(int mask){
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N + 1];

        queue.add(1);
        visited[1] = true;
        int peopleCnt = 0;
        int n = 0;
        while(!queue.isEmpty()){
            int node = queue.poll();
            peopleCnt += population[node];
            n++;
            for(int adj : adjList[node]){
                if(visited[adj])
                    continue;
                // 현재 구역과 인접 구역이 같은 선거구인 경우
                if(getNth(mask, node) == getNth(mask, adj)){
                    queue.add(adj);
                    visited[adj] = true;
                }
            }
        }
        int i = 2;
        // 다른 구역 시작번호 찾기
        while(getNth(mask, 1) == getNth(mask, i)){
            i++;
        }

        queue.add(i);
        visited[i] = true;
        while(!queue.isEmpty()){
            int node = queue.poll();
            n++;
            for(int adj : adjList[node]){
                if(visited[adj])
                    continue;
                if(getNth(mask, node) == getNth(mask, adj)){
                    queue.add(adj);
                    visited[adj] = true;
                }
            }
        }
        if(n != N)
            return -1;

        return Math.abs(peopleCnt - (sum - peopleCnt));
    }
    // mask 끝에서부터 n번째 비트를 반환
    public static int getNth(int mask, int n){
        return (mask >> (n - 1)) & 1;
    }
}
