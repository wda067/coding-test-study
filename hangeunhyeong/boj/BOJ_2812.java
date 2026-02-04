package hangeunhyeong.boj;

import java.io.*;
import java.util.*;
/*
BOJ 2812 - 크게만들기
[문제풀기전]
- 숫자를 정렬한뒤 최솟값 K개를 지우는 방식인가? X, 예제 입력3이 반례
- 완전탐색? X, 시간복잡도가 O(nCk) 이므로 시간초과
- 그리디? Yes! 자리수가 작을수록 앞쪽에 큰 수가 와야한다는 것을 이용
- N이 최대 50만이고 생성과 삽입이 빈번 -> LinkedList 이용
(단, 생성과 삽입에 걸리는 시간을 O(1)로 만들어주기 위해 직접 구현하거나 iterator 사용)

[과정]
1. 나보다 오른쪽에 큰 숫자가 더이상 없거나(내림차순으로 정렬될때까지) K개를 다 없앨때까지 나보다 오른쪽에 큰 숫자 있으면 지워나가기(왼쪽->오른쪽 방향으로 탐색)
2. 내림차순으로 정렬되었는데 K개를 다 없애지 못한 경우 K개를 다 없앨 때까지 가장 오른쪽부터 남아있는 수를 지워나가기
=> O(2*N)
 */
public class BOJ_2812 {
    static class Node{
        public int key;
        public Node next, prev;
        public Node(int key){
            this.key = key;
            this.next = null;
            this.prev = null;
        }
    }
    static class MyLinkedList {
        public Node head, tail;
        public int size;
        public MyLinkedList(){
            head = null;
            tail = null;
            size = 0;
        }
        // 연결리스트의 마지막에 노드 추가 O(1)
        public void add(int key){
            Node node = new Node(key);
            if(size == 0){
                head = node;
                tail = node;
                size++;
                return;
            }
            // 추가하려는 노드의 prev, next를 설정
            node.prev = tail;
            node.next = null;
            // 기존 tail노드의 next 설정 후 tail 변경
            tail.next = node;
            tail = node;
            size++;
        }
        // node를 연결리스트에서 제거 O(1)
        public void remove(Node node){
            // 이전노드의 다음노드, 다음 노드의 이전노드 설정(node가 head일 때는 이전이 없고 node가 tail일때는 이후가 없으니 예외처리)
            if(node != head)
                node.prev.next = node.next;
            if(node != tail)
                node.next.prev = node.prev;
            if(node == head)
                head = node.next;
            if(node == tail)
                tail = node.prev;
            size--;
        }
        public void print(){
            StringBuilder sb = new StringBuilder();
            Node node;
            for (node = this.head; node != null && node.next != null; node = node.next) {
                sb.append(node.key);
            }
            if(this.size != 0)
                sb.append(node.key);
            System.out.println(sb);
        }
    }

    public static void main(String[] args) throws IOException{
        int N, K;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        MyLinkedList list = new MyLinkedList();
        String input = br.readLine();

        for(int i = 0; i < N; i++)
            list.add(input.charAt(i) - '0');
//        list.print();
//        System.out.println(list.size);
        int cnt = 0;
        int cntPerTurn = 0;
        Node node = list.head;
        do{
            // 다음이 나보다 큰 숫자일 때
            if(node != list.tail && node.next.key > node.key){
                list.remove(node);
//                list.print();
                cnt++;
                cntPerTurn++;
                node = node.prev;
            }
            else
                node = node.next;
            // 끝까지 탐색했는데 이번 턴에 없앤 수가 없을 때(즉, 내림차순 정렬일때)
            if(node == null && cntPerTurn == 0){
                break;
            }
            // 끝까지 탐색했고 이번턴에 수를 없앤적이 있다면
            if(node == null){
                cntPerTurn = 0;
                node = list.head;
            }
        }while(cnt < K);

        // 끝까지 탐색했는데 그리디로 없애지 못한 경우 = 내림차순 정렬된 경우 이므로 끝에서부터 남은 수만큼 지워준다.
        if(cnt != K){
            for(int i = 0; i < K - cnt; i++){
                list.remove(list.tail);
            }
        }
        list.print();
    }
}
