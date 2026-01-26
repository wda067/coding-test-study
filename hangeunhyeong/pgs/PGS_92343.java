package hangeunhyeong.pgs;

import java.util.*;
/*
프로그래머스 - 양과 늑대
dfs를 이용하여 풀이
canGo 배열 : 방문할 수 있는 노드 번호
cnt 배열 : cnt[0]은 지금까지 방문한 양의 수, cnt[1]은 늑대의 수를 의미

 */
public class PGS_92343 {
    static int[] tree;
    static int max = 0;
    static int[][] Edges;
    public int solution(int[] info, int[][] edges) {
        tree = info.clone();
        Edges = edges.clone();
        boolean[] canGo = new boolean[info.length];
        Arrays.fill(canGo, false);
        int[] cnt = {1, 0}; // 양의 수, 늑대의 수

        dfs(0, canGo, cnt);
        return max;
    }
    public void dfs(int node, boolean[] canGo, int[] cnt){
        canGo[node] = false;
        // 갈 수 있는 노드에 자식 노드추가
        for(int[] edge : Edges){
            if(edge[0] == node){
                canGo[edge[1]] = true;
            }
        }
        // 디버깅
        // for(int i = 0; i < canGo.length; i++){
        //     if(canGo[i])
        //         System.out.print(i + " ");
        // }
        // System.out.println();
        // System.out.println("양 : " + cnt[0] + ", 늑대 : " + cnt[1]);
        // 디버깅

        if(max < cnt[0])
            max = cnt[0];
        for(int i = 0; i < canGo.length; i++){
            if(!canGo[i])
                continue;
            // 양과 늑대의 수 업데이트
            int[] next = cnt.clone();
            next[tree[i]]++;
            // 양 > 늑대
            if(next[0] > next[1]){
                dfs(i, canGo.clone(), next);
            }
        }

    }
}
