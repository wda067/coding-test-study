package hangeunhyeong.boj;
/*
BOJ 2250 - 트리의 높이와 너비
중위 순위의 특성을 이용 => 가로 방향으로 노드를 방문
처음에는 배열의 idx를 이용해서 트리를 구현하려고 했지만 공간복잡도 이슈 때문에 Node 클래스를 이용하여 트리를 구현하였다.
1. 트리 생성
2. 중위순회해서 각 레벨의 최솟값과 최댓값을 찾음
3. 최대너비와 최대너비를 가진 레벨을 출력
 */
import java.io.*;
public class BOJ_2250 {
    static class Node{
        int left, right, parent;
        int key;
        int level;
        public Node(int key){
            this.key = key;
        }
    }
    public static Node[] nodes;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        nodes = new Node[N + 1];
        for(int i = 0; i < N; i++){
            String[] input = br.readLine().split(" ");
            int cur = Integer.parseInt(input[0]);
            int left = Integer.parseInt(input[1]);
            int right = Integer.parseInt(input[2]);
            // cur, left, right를 key로 가지는 노드들 생성
            if(nodes[cur] == null)
                nodes[cur] = new Node(cur);
            if(left != -1 && nodes[left] == null)
                nodes[left] = new Node(left);
            if(right != -1 && nodes[right] == null)
                nodes[right] = new Node(right);
            // 현재 노드의 자식들을 등록
            nodes[cur].left = left;
            nodes[cur].right = right;
            // 자식노드들의 부모(=현재노드)를 등록
            if(left != -1)
                nodes[left].parent = cur;
            if(right != -1)
                nodes[right].parent = cur;
        }
        // root노드 찾기
        int root = 0;
        for(Node node : nodes){
            if(node != null && node.parent == 0) {
                root = node.key;
                break;
            }
        }
        width = new int[N + 1][2]; // 레벨별 너비
        // width 초기화
        for(int i = 1; i < width.length; i++)
            width[i][0] = N;
        // 중위 순회
        inOrderSearch(root, 1);
        // 디버깅
        //  printWidth();

        // 최대너비와 최대너비를 가지는 레벨 구하기
        int maxLv = 0, max = 0;
        for(int i = 1; i <= N; i++){
            int w =  width[i][1] - width[i][0] + 1;
            if(max < w){
                max = w;
                maxLv = i;
            }
        }
        System.out.printf("%d %d", maxLv, max);

    }
    public static void printWidth(){
        for(int i = 1; i < width.length; i++){
            System.out.printf("Lv%d : %d %d\n", i, width[i][0], width[i][1]);
        }
    }
    public static int idx = 0;
    public static int[][] width;    // 레벨별 너비
    public static void inOrderSearch(int n, int level){
        if(n == -1)
            return;
        Node node = nodes[n];
        node.level = level;
        // 왼쪽 자식 노드 방문
        inOrderSearch(nodes[n].left, level + 1);
        // 기록
        width[level][0] = Math.min(idx, width[level][0]);
        width[level][1] = Math.max(idx, width[level][1]);
        idx++;
        // 오른쪽 자식노드 방문
        inOrderSearch(nodes[n].right, level + 1);

    }
}
