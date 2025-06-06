package pkl0912.pgs;

import java.util.*;
import java.lang.*;


public class PGS_92343 {
    static int max = 0;
    static int[] animal;
    static ArrayList<Integer>[] childs;
    
    public int solution(int[] info, int[][] edges) {
        animal = info;
        childs = new ArrayList[info.length]; // 자식 값 저장
        for(int[] edge : edges) {
            if(childs[edge[0]] == null) {
                childs[edge[0]] = new ArrayList<>();
            }    
            childs[edge[0]].add(edge[1]);
        }
        
        List<Integer> check = new ArrayList<>();
        check.add(0); // 루트부터 탐색
        dfs(0, 0, 0, check);
        
        return max;
        
    }
    
    public void dfs(int idx, int sheep, int wolf, List<Integer> check) {
        if(animal[idx] == 0) {
            sheep++;
        }else {
            wolf++;
        }
        
        if(sheep <= wolf) return; // 잡아먹힘
        
        max = Math.max(sheep, max);
        
        // 다음 갈 수 있는 곳 갱신해줌
        List<Integer> next = new ArrayList<>();
        next.addAll(check);
        next.remove(Integer.valueOf(idx)); // 현재는 탐색했으니까 빼줌
        if(childs[idx] != null) { // 리프노드가아니면
            for(int child: childs[idx]) {
                next.add(child);
            }
        }
        
        for(int node : next) {
            dfs(node, sheep, wolf, next);
        }
        
    }
    
}
