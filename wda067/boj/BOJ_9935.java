import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
백준 / 문자열 폭발 / 골드4
https://www.acmicpc.net/problem/9935
 */
public class BOJ_9935 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();       // 1,000,000
        String bomb = br.readLine();    // 36

        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            sb.append(c);  // 왼쪽부터 한 글자씩 삽입

            // 폭발문자열이 존재할 수 있을 경우
            if (sb.length() >= bomb.length()) {
                boolean flag = true;

                // sb 끝부분이 폭발문자열과 일치하는지 확인
                for (int i = 0; i < bomb.length(); i++) {
                    if (sb.charAt(sb.length() - bomb.length() + i) != bomb.charAt(i)) {
                        flag = false;
                        break;
                    }
                }

                // sb 끝의 폭발문자열 삭제
                if (flag) {
                    sb.delete(sb.length() - bomb.length(), sb.length());
                }
            }
        }

        if (sb.length() == 0) {
            System.out.println("FRULA");
        } else {
            System.out.println(sb);
        }
    }
}
