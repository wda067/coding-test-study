import java.util.*;
import java.lang.*;

/*

- 폭발 문자열 폭발하면 그 문자는 문자열에서 사라짐
    - 이것도 계속해서 while문 방식일 듯
        - 남아있는 문자 x -> "FRULA"
[풀이]
- Stack
    - 순차적으로 진행하면서
        - 만약 일치하는 문자열이 들어온다면 제거
            - 이거 판별 로직을 어떻게 할 지가 고민..
                - stk 길이가 폭발 문자열보다 작으면 continue
                - idx로 비교


[조건]
- 알파벳 소문자, 대문자, 숫자 0~9
- 최대 10^6

[output]
- 끝나고 남은 문자열 출력

[느낀점]
- for문 remove는 중간에서 시작하면 idx가 꼬이므로 뒤에서 삭제해야 된다.

*/
public class BOJ_9935 {
	static String base, target;
	static ArrayList<Character> stk;
	static int n;
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		base = sc.next();
		target = sc.next();
		n = target.length();
		stk = new ArrayList<>();
		for(char c : base.toCharArray()){
			stk.add(c);
			check();
		}
		if(stk.size() == 0) System.out.println("FRULA");
		else {
			var sb = new StringBuilder();
			for(int i = 0; i < stk.size(); i++){
				sb.append(stk.get(i));
			}
			System.out.println(sb.toString());
		}
	}

	static void check(){
		boolean isCheck = true;
		if(stk.size() < n) return;
		for(int i = 0; i < n; i++){
			if(stk.get(i+(stk.size()-n)) == target.charAt(i)) continue;
			else{
				isCheck = false;
				break;
			}
		}

		if(isCheck){
			remove();
			check();
		} else return;
	}
	static void remove(){
		int m = stk.size();
		for(int i = m-1; i >= m-n; i--){
			stk.remove(i);
		}
	}

}
