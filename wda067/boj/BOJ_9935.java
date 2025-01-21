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

        String s = br.readLine();
        String bomb = br.readLine();
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            sb.append(c);
            if (sb.length() >= bomb.length()) {
                boolean isBomb = true;

                //sb에 폭탄 문자열이 존재하는지 검사
                for (int i = 0; i < bomb.length(); i++) {
                    if (sb.charAt(sb.length() - bomb.length() + i) != bomb.charAt(i)) {
                        isBomb = false;
                        break;
                    }
                }

                //폭탄 문자열이 존재하면 sb에서 폭탄 문자열만 제거
                if (isBomb) {
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
