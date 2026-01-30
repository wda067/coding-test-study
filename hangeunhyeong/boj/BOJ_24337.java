package hangeunhyeong.boj;

import java.io.*;
import java.util.*;
/*
백준 24337 - 가희와 탑
[문제풀이]
    a + b - 1 < N
        -1 출력
    그 외
        i) a != 1
            (1 ... 1) (1 ~ a-1) (a, b 중 최댓값) (b-1 ~ 1)
        ii) a == 1
            (a, b 중 최댓값) (1 ... 1) (b-1 ~ 1)
[느낀점]
이 문제의 핵심은 왼쪽에서 보이는 건물 수(a)와 오른쪽에서 보이는 건물 수(b)를
동시에 만족시키는 높이 배열의 구조를 떠올리는 것이었다.

처음에는 단순히 증가/감소 수열로 접근하려 했지만,
두 조건이 하나의 ‘최대 높이 건물’을 기준으로 연결된다는 점을 캐치하지 못해
구현 방향을 잡기 어려웠다.

그리고 a == 1 인 경우에는 최대 높이 건물이 반드시 맨 앞에 와야 한다는
예외 케이스를 따로 처리해야 한다는 점도 어려웠었다.

 */
public class BOJ_24337 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int a = sc.nextInt();
        int b = sc.nextInt();
        ArrayList<Integer> answer = new ArrayList<>();
        if(a + b - 1 > N){
            System.out.println(-1);
            return;
        }
        if(a + b - 1 < N && a != 1){
            for(int i = 0; i < N - (a + b - 1); i++)
                answer.add(1);
        }
        int height = 1;
        while(height <= a - 1){
            answer.add(height++);
        }
        answer.add(Math.max(a, b));
        if(a == 1){
            for(int i = 0; i < N - (a + b - 1); i++)
                answer.add(1);
        }
        height = b - 1;
        while(height >= 1){
            answer.add(height--);
        }

        for(int i = 0; i < N; i++)
            System.out.print(answer.get(i) + " ");
    }
}
