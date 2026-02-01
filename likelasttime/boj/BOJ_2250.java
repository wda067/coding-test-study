import java.io.*;
import java.util.*;

public class BOJ_2250 {
    static int N;                   // 노드의 개수
    static int[][] tree;             // tree[node][0] = 왼쪽 자식, tree[node][1] = 오른쪽 자식
    static ArrayList<Integer>[] levels; // 각 레벨별 column 번호 저장
    static int[] visit;              // 각 노드의 등장 횟수 (루트 판별용)
    static int column = 1;           // 중위 순회 시 열 번호

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        tree = new int[N + 1][2];
        visit = new int[N + 1];
        levels = new ArrayList[N + 1];

        // 레벨별 리스트 초기화
        for (int i = 0; i <= N; i++) {
            levels[i] = new ArrayList<>();
        }

        // 트리 정보 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            tree[node][0] = left;
            tree[node][1] = right;

            // 현재 노드 방문 표시
            visit[node]++;

            // 자식 노드 방문 표시
            if (left != -1) visit[left]++;
            if (right != -1) visit[right]++;
        }

        // 루트 노드 찾기
        int root = findRoot();

        // 중위 순회로 column 번호 부여
        dfs(root, 1);

        // 초기값 설정 (1번 레벨)
        int answerLevel = 1;
        int maxWidth = getWidth(1);

        // 각 레벨의 너비 계산
        for (int i = 2; i <= N; i++) {
            if (levels[i].isEmpty()) break;  // 더 이상 레벨이 없으면 종료

            int width = getWidth(i);

            // 더 넓은 레벨 발견 시 갱신
            if (width > maxWidth) {
                maxWidth = width;
                answerLevel = i;
            }
        }

        // 결과 출력
        System.out.println(answerLevel + " " + maxWidth);
    }

    /**
     * 루트 노드 찾기
     * visit 배열에서 값이 1인 노드가 루트
     */
    static int findRoot() {
        for (int i = 1; i <= N; i++) {
            if (visit[i] == 1) return i;
        }
        return -1;
    }

    /**
     * 중위 순회 (In-order Traversal)
     * 왼쪽 → 현재 → 오른쪽 순서로 방문
     * 방문 시 column 번호를 레벨에 저장
     */
    static void dfs(int node, int level) {
        if (node == -1) return;

        // 왼쪽 서브트리
        dfs(tree[node][0], level + 1);

        // 현재 노드 column 기록
        levels[level].add(column++);

        // 오른쪽 서브트리
        dfs(tree[node][1], level + 1);
    }

    /**
     * 특정 레벨의 너비 계산
     * 너비 = (가장 오른쪽 column - 가장 왼쪽 column + 1)
     */
    static int getWidth(int level) {
        int min = Collections.min(levels[level]);
        int max = Collections.max(levels[level]);
        return max - min + 1;
    }
}
