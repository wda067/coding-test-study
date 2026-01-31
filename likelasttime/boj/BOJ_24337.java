import java.util.*;
/*
    다양한 높이의 건물들이 N개 존재
    가희는 건물들의 왼쪽에 위치
    단비는 건물들의 오른쪽에 위치
    가희의 오른쪽에 1번 건물이 있다.
    1 <= x <= N-1일때
    	x번 건물의 오른쪽에는 x+1번 건물이 있다.
    N번 건물의 오른쪽에는 단비가 있다.
    가희는 1번 건물을 볼 수 있다.
    k번 건물보다 왼쪽에 있는 건물들이 모두 k번 건물보다 높이가 낮다면
    	가희는 k번 건물을 볼 수 있다.
    단비는 N번 건물을 볼 수 있다.
    k번 건물보다 오른쪽에 있는 건물들이 모두 k번 건물보다 높이가 낮다면
    	단비는 k번 건물을 볼 수 있다.

    [풀이]
    시간복잡도: O(N)
    공간복잡도: O(N)
*/
class Main {
    static int N;	// 1 <= 건물의 개수 <= 10^5
    static int A;	// 1 <= 가희가 볼 수 있는 건물의 개수 <= N
    static int B;	// 1 <= 단비가 볼 수 있는 건물의 개수 <= N
    static List<Integer> tower;

    public static void simulate() {
        // 오른쪽을 바라보기(1 ~ A - 1까지 세우기)
        for(int i=1; i<A; i++) {
            tower.add(i);
        }

        // A와 B 모두 볼 수 있는 가장 높은 건물 추가
        tower.add(Math.max(A, B));

        // 왼쪽을 바라보기(B - 1 ~ 1까지 세우기)
        for(int i=B-1; i>=1; i--) {
            tower.add(i);
        }

        if(A == 1) {    // 왼쪽에서 바라봤을 때 건물을 1개만 보는 상황
            while(tower.size() < N) {
                // 사전순으로 빠른 게 정답이니까 두 번째 위치에 1 채우기
                tower.add(1, 1);
            }
        } else {
            // 맨 앞에 1을 리스트 tower의 길이가 N이 될때까지 채우기
            while(tower.size() < N) {
                tower.add(0, 1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        tower = new ArrayList();

        // 가장 높은 건물은 단비, 가희 둘다 볼 수 있어서 N + 1
        if(A + B > N + 1) {
            System.out.print(-1);
            return;
        }

        simulate();

        for(int i=0; i<N; i++) {
            System.out.print(tower.get(i) + " ");
        }
    }
}