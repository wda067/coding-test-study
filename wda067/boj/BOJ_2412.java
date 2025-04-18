import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
백준 / 암벽 등반 / 골드4
https://www.acmicpc.net/problem/2412
 */
public class BOJ_2412 {

    private static Node[] graph;
    private static int[] visited;
    private static int n, T;

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        graph = new Node[n + 1];
        visited = new int[n + 1];
        graph[0] = new Node(0, 0);

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[i] = new Node(x, y);
        }

        Arrays.sort(graph, (o1, o2) -> {
            if (o1.y > o2.y) {
                return 1;
            } else if (o1.y == o2.y) {
                if (o1.x > o2.x) {
                    return 1;
                }
            }
            return -1;
        });

        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        visited[0] = 0;

        while (!q.isEmpty()) {
            int nowIndex = q.poll();
            if (graph[nowIndex].y == T) {
                return visited[nowIndex];
            }
            int nowX = graph[nowIndex].x;
            int nowY = graph[nowIndex].y;

            for (int nextIndex = nowIndex + 1; nextIndex <= n; nextIndex++) {
                if (visited[nextIndex] != 0) {
                    continue;
                }
                int nextX = graph[nextIndex].x;
                int nextY = graph[nextIndex].y;
                if (nextY - nowY > 2) {
                    break;
                }
                if (Math.abs(nextX - nowX) > 2) {
                    continue;
                }
                visited[nextIndex] = visited[nowIndex] + 1;
                q.add(nextIndex);
            }
            for (int nextIndex = nowIndex - 1; nextIndex > 0; nextIndex--) {
                if (visited[nextIndex] != 0) {
                    continue;
                }
                int nextX = graph[nextIndex].x;
                int nextY = graph[nextIndex].y;
                if (nextY - nowY < -2) {
                    break;
                }
                if (Math.abs(nextX - nowX) > 2) {
                    continue;
                }
                visited[nextIndex] = visited[nowIndex] + 1;
                q.add(nextIndex);
            }
        }
        return -1;
    }

    private static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}