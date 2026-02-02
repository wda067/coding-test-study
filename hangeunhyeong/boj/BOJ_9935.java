package hangeunhyeong.boj;
/*
BOJ 9935 - 문자열 폭발
str : 문자열
bomb : 폭발문자열
[오답]
while(str.contains(bomb))
    str = str.replaceAll(폭발문자열, "");
기본 풀이를 사용하면 O(N^2) 시간초과 => 다른방법? stack을 사용!

[정답풀이]
- 문자열을 한 글자씩 스택에 쌓는다
- 매번 쌓을 때마다 스택의 끝부분이 bomb와 같아졌는지 검사
- 같으면 폭발 문자열 길이만큼 pop

[개선점]
java.util.Stack을 이용하는 것보다 배열, 포인터로 stack을 구현하는것이 더 좋음
Stack을 이용하면 폭발 문자열을 검사하는 과정에서 stack을 배열처럼 쓰게 되기 때문
 */
import java.io.*;
import java.util.*;
public class BOJ_9935 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] str = br.readLine().toCharArray();
        int strLen = str.length;
        char[] bomb = br.readLine().toCharArray();
        int bombLen = bomb.length;
        char[] stk = new char[strLen];
        int top = 0;
        for(int i = 0; i < strLen; i++){
            stk[top] = str[i];  // 문자열을 한 글자씩 스택에 쌓음
            int cnt = 0;
            int j = 0, stkIdx, bombIdx;
            // 스택의 끝부분이 폭발 문자열과 같은지 검사
            do{
                stkIdx = top - j;
                bombIdx = bombLen - 1 - j;
                if(stkIdx >= 0 && bombIdx >= 0 && stk[stkIdx] == bomb[bombIdx])
                    cnt++;
                j++;
            }while(stkIdx >= 0 && bombIdx >= 0 && stk[stkIdx] == bomb[bombIdx] && cnt < bombLen);// 검사할 수 있고 검사결과가 같은동안

            // 스택의 끝부분이 폭발문자열과 같으면 pop
            if(cnt == bombLen){
                top -= bombLen;
            }
            top++;
        }
        if(top == 0)
            System.out.print("FRULA");
        else{
            System.out.print(new String(stk, 0, top));
        }
    }
}
// mirkovC4nizCC44 C4
// 12ab112ab2ab 12ab