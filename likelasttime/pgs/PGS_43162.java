import java.util.*;

/*
    A와 B가 직접적으로 연결되어있고, B와 C가 직접적으로 연결되어 있다면
        A와 C도 간접적으로 연결되어 있다.
    네트워크의 개수를 구하기
*/
class PGS_43162 {
    static int[] parent;        // 각 컴퓨터의 부모

    public static int union(int id) {
        if(parent[id] == id) {
            return id;
        }
        return parent[id] = union(parent[id]);
    }

    public static void find(int a, int b) {
        int aParent = union(a);
        int bParent = union(b);

        if(aParent != bParent) {
            parent[aParent] = bParent;
        }
    }

    /*
        n: 1 <= 컴퓨터의 개수 <= 200
        computers: 연결에 대한 정보가 담긴 2차원 배열

        네트워크 개수를 리턴
    */
    public int solution(int n, int[][] computers) {
        parent = new int[n];
        for(int i=0; i<n; i++) {
            // 자기 자신을 부모로 초기화
            parent[i] = i;
        }

        for(int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                if(computers[i][j] == 0) {		// 연결되어있지 않다면
                    continue;
                }
                find(i, j);
            }
        }

        for(int i=0; i<n; i++) {
            union(i);
        }

        Set<Integer> set = new HashSet();
        for(int i=0; i<n; i++) {
            set.add(parent[i]);
        }

        return set.size();
    }
}