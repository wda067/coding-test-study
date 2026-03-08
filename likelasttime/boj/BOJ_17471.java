import java.util.*;
import java.io.*;
/* * N개의 구역(1번 ~ N번)
 * * 구역을 두 개의 선거구로 나눠야 한다.
 * * 각 구역은 두 선거구 중 하나에 포함되어야 한다.
 * * 선거구는 구역을 적어도 하나 포함한다.
 * * 한 선거구에 포함되어 있는 구역은 모두 연결되어 있어야 한다.
 * * 중간에 통하는 인접한 구역은 0개 이상이어야 한다.
 * * 모두 같은 선거구에 포함된 구역이어야 한다.
 * * 두 선거구에 포함된 인구의 차이를 최소로 한다. */
class BOJ_17471 {

    static int N;
    static int[] people;
    static List<Integer>[] graph;

    static boolean check(List<Integer> group) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visit = new boolean[N + 1];

        q.offer(group.get(0));
        visit[group.get(0)] = true;

        int cnt = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : graph[cur]) {
                if (!visit[next] && group.contains(next)) {
                    visit[next] = true;
                    q.offer(next);
                    cnt++;
                }
            }
        }

        return cnt == group.size();
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();

        people = new int[N + 1];
        graph = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            people[i] = sc.nextInt();
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            int m = sc.nextInt();
            for (int j = 0; j < m; j++) {
                graph[i].add(sc.nextInt());
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int i = 1; i < (1 << N) - 1; i++) {

            List<Integer> A = new ArrayList<>();
            List<Integer> B = new ArrayList<>();

            for (int j = 0; j < N; j++) {

                if ((i & (1 << j)) != 0) {
                    A.add(j + 1);
                } else {
                    B.add(j + 1);
                }
            }

            if (check(A) && check(B)) {

                int sumA = 0;
                int sumB = 0;

                for (int a : A) sumA += people[a];
                for (int b : B) sumB += people[b];

                answer = Math.min(answer, Math.abs(sumA - sumB));
            }
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}