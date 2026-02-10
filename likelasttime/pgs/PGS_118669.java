import java.util.*;
import java.util.stream.Collectors;

/*
    n개의 지점으로 이루어짐
    각 지점은 1 ~ n의 번호를 가진다
    각 지점은 양방향 통행이 가능한 등산로로 연결되어 있다.
    휴식 없이 이동해야 하는 시간 중 가장 긴 시간은 intensity
    출입구는 처음과 끝에 한 번씩, 산봉우리는 한 번만 포함된다.
    itensity를 최소로 하자.(여러 개라면 산봉우리의 번호가 가장 낮은 등산코스를 선택)
*/
class Solution {
    static List<List<Node>> nodeLst = new ArrayList();      // 인접 리스트

    class Node {
        int num;        // 지점 번호
        int weight;     // 비용

        Node(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }
    }

    /*
        2 <= 지점 수 n <= 50,000
        paths = [[i, j, w], ...]
        1 <= 출입구 gates <= n
        1 <= 산봉우리 summits <= n
    */
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        int answerNum = n;      // 산봉우리의 번호
        int answerWeight = 10000001;        // intensity의 최소값
        for(int i=0; i<n+1; i++) {        // 지점이 1부터 시작이라서 n+1개를 만들었다.
            nodeLst.add(new ArrayList());
        }

        // O(1)로 원소를 찾기 위해 배열을 리스트 타입으로 변환
        List<Integer> gatesLst = Arrays.stream(gates).boxed().collect(Collectors.toList());
        List<Integer> summitsLst = Arrays.stream(summits).boxed().collect(Collectors.toList());

        for(int[] path : paths) {
            int i = path[0];        // i 지점
            int j = path[1];        // j 지점
            int w = path[2];        // 두 지점 사이를 이동하는 데 걸리는 시간
            if(gatesLst.contains(i) || summitsLst.contains(j)) {       // 단방향 연결
                nodeLst.get(i).add(new Node(j, w));           // (i -> j)
            } else if(gatesLst.contains(j) || summitsLst.contains(i)) {     // 단방향 연결
                nodeLst.get(j).add(new Node(i, w));            // (j -> i)
            } else {    // 양방향 연결
                nodeLst.get(i).add(new Node(j, w));
                nodeLst.get(j).add(new Node(i, w));
            }
        }

        int[] weights = dijkstra(gates, n);     // 다익스트라 알고리즘 호출

        // intensity가 최소가 되는 등산코스가 여러 개라면 그중 산봉우리의 번호가 가장 낮은 등산코스를 선택
        for(int i=1; i<=n; i++) {
            if(summitsLst.contains(i)) {     // 이 지점이 출입구라면
                if(weights[i] < answerWeight) {     // intensity가 최소인 산봉우리를 찾으면
                    answerWeight = weights[i];      // intensity의 최솟값
                    answerNum = i;      // 산봉우리의 번호
                }
            }
        }

        return new int[]{answerNum, answerWeight};      // [산봉우리의 번호, intensity의 최솟값]
    }

    /*
        gates: 출입구 배열
        n: 지점 수
    */
    public int[] dijkstra(int[] gates, int n) {
        PriorityQueue<Node> que = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight)); // 이동할 때 소요되는 시간이 낮은 순으로 정렬한 우선순위 큐 생성
        boolean[] visited = new boolean[n + 1];     // 방문 배열
        int[] weights = new int[n + 1];             // 지점별 intensity를 저장하는 배열
        Arrays.fill(weights, Integer.MAX_VALUE);    // 비용 배열 초기화

        for(int gateNum : gates) {     // 출입구를 큐에 모두 넣기
            que.add(new Node(gateNum, 0));      // 출입구의 비용(소요시간)은 0이다.
            weights[gateNum] = 0;       // 시작 지점의 비용 배열의 값을 0으로 할당
        }

        while(!que.isEmpty()) {     // 우선 순위 큐에 원소가 있을 동안에
            Node node = que.poll();         // 비용이 가장 낮은 객체를 꺼내기
            int num = node.num;     // 현재 지점 번호
            int weight = node.weight;       // 현재 지점의 비용(소요시간)

            if(visited[num]) {      // 방문한 지점은 건너띄기
                continue;
            } else if(weight > weights[num]) {      // 현재 지점의 intensity를 초과하는 지점은 볼 필요가 없다.
                continue;
            }

            visited[num] = true;        // 현재 지점 방문 처리

            for(Node newNode : nodeLst.get(num)) {     // num과 연결된 지점들을 탐색
                int newNum = newNode.num;       // 새로운 지점 번호
                int newWeight = newNode.weight;     // 새로운 지점의 비용(소요시간)
                int maxWeight = Math.max(newWeight, weights[num]);   // 여기까지 오는 데 휴식 없이 이동해야 하는 시간 중 가장 긴 시간
                if(weights[newNum] > maxWeight) {       // 더 작은 비용을 찾음
                    weights[newNum] = maxWeight;        // intensity 값 갱신
                    que.add(new Node(newNum, weights[newNum]));     // 우선순위 큐에 추가
                }
            }
        }

        return weights;
    }
}