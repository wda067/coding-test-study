import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();
        String bomb = br.readLine();

        int bombLen = bomb.length();
        char[] stack = new char[str.length()];
        int idx = 0;

        for (int i = 0; i < str.length(); i++) {
            stack[idx++] = str.charAt(i);

            // 폭발 문자열 길이 이상일 때만 검사
            if (idx >= bombLen) {
                boolean isBomb = true;
                for (int j = 0; j < bombLen; j++) {
                    if (stack[idx - bombLen + j] != bomb.charAt(j)) {
                        isBomb = false;
                        break;
                    }
                }

                // 폭발 문자열이면 제거
                if (isBomb) {
                    idx -= bombLen;
                }
            }
        }

        if (idx == 0) {
            System.out.println("FRULA");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < idx; i++) {
                sb.append(stack[i]);
            }
            System.out.println(sb);
        }
    }
}
