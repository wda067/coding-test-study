import java.util.*;
import java.lang.*;

/*
[input]
- 노드의 개수 N
- 노드 번호, 왼쪽 자식, 오른쪽 자식
    - 자식이 없으면 -1

[풀이]
이진 트리
각 레벨 별 너비를 구해야함
    - 레벨 별 가장 왼쪽, 가장 오른쪽을 찾아야 함
    - left List, right List 따로 배열을 만듦
    - max_depth를 알아야 함
    - x좌표 관리
먼저 루트 노드를 찾기
    - 소거법으로 boolean[] false인 것을 찾는 방식

[output]
너비가 가장 넓은 레벨, 그 레벨의 너비 출력

[느낀점]
- 열번호(x좌표) 구하는 방법을 놓침 (중위순회)
- 구해야 할 것은 잘 파악했지만
    - 루트노드 파악
    - 중위순회로 업데이트
위 과정을 작성하는데에 어려움 겪음
*/
public class BOJ_2250 {

	static class Node {
		int num;
		int left;
		int right;

		public Node(int num, int left, int right){
			this.num = num;
			this.left = left;
			this.right = right;
		}
	}

	static int[] minLeft, maxRight;
	static Node[] tree;
	static int nodeIdx = 1;
	static int width = 1;
	static int dep = 1;
	static int n;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();

		tree = new Node[n+1];
		minLeft = new int[n+1];
		maxRight = new int[n+1];

		Arrays.fill(minLeft, Integer.MAX_VALUE);
		Arrays.fill(maxRight, Integer.MIN_VALUE);

		boolean[] hasParent = new boolean[n+1];

		for(int i = 0; i < n; i++){
			int num = sc.nextInt();
			int left = sc.nextInt();
			int right = sc.nextInt();

			tree[num] = new Node(num, left, right);
			if(left != -1) hasParent[left] = true;
			if(right != -1) hasParent[right] = true;
		}

		int rootIdx = -1;
		for(int i = 1; i <= n; i++){
			if(!hasParent[i]) rootIdx = i;
		}

		inOrder(1, rootIdx);

		// 계산
		cal();
		System.out.print(dep + " " + width);
	}

	static void cal(){
		for(int i = 1; i <= n; i++){
			int tmpWidth = maxRight[i] - minLeft[i] + 1;
			if(tmpWidth > width){
				width = tmpWidth;
				dep = i;
			}
		}
	}

	static void inOrder(int dep, int idx){
		if(idx == -1) return;

		inOrder(dep+1, tree[idx].left);

		minLeft[dep] = Math.min(minLeft[dep], nodeIdx);
		maxRight[dep] = Math.max(maxRight[dep], nodeIdx);
		nodeIdx+=1;

		inOrder(dep+1, tree[idx].right);
	}
}
