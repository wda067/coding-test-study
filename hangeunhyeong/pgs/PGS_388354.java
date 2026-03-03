package hangeunhyeong.pgs;
import java.util.*;
class PGS_388354 {
    static class DSU {
        int[] p, r;
        DSU(int n) {
            p = new int[n];
            r = new int[n];
            for (int i = 0; i < n; i++) p[i] = i;
        }
        int find(int x) {
            while (p[x] != x) {
                p[x] = p[p[x]];
                x = p[x];
            }
            return x;
        }
        void union(int a, int b) {
            a = find(a); b = find(b);
            if (a == b) return;
            if (r[a] < r[b]) { int t = a; a = b; b = t; }
            p[b] = a;
            if (r[a] == r[b]) r[a]++;
        }
    }

    public int[] solution(int[] nodes, int[][] edges) {
        int n = nodes.length;

        // 1) 좌표압축: sortedNodes에서 index를 찾는다
        int[] sorted = nodes.clone();
        Arrays.sort(sorted);

        // 2) degree + DSU
        int[] deg = new int[n];
        DSU dsu = new DSU(n);

        for (int[] e : edges) {
            int a = idxOf(sorted, e[0]);
            int b = idxOf(sorted, e[1]);
            deg[a]++;
            deg[b]++;
            dsu.union(a, b);
        }

        // 3) 컴포넌트별 size, match 집계
        int[] size = new int[n];
        int[] match = new int[n];

        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            size[root]++;

            int nodeVal = sorted[i];      // 원래 노드 번호
            int p = nodeVal & 1;          // 번호 홀짝
            int d = deg[i] & 1;           // 차수 홀짝
            if (p == d) match[root]++;
        }

        // 4) 조건 체크
        int oddEvenTrees = 0;
        int revOddEvenTrees = 0;

        for (int i = 0; i < n; i++) {
            if (size[i] == 0) continue;

            if (match[i] == 1) oddEvenTrees++;
            if (size[i] - match[i] == 1) revOddEvenTrees++;
        }

        return new int[]{oddEvenTrees, revOddEvenTrees};
    }

    // sorted 배열에서 value의 인덱스
    static int idxOf(int[] sorted, int value) {
        int idx = Arrays.binarySearch(sorted, value);
        // 문제에서 edges의 a,b는 nodes에 존재한다고 보장 -> idx >= 0
        return idx;
    }
}
