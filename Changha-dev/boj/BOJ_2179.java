import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
char a~z 26
자료구조 : 트라이 활용
s,t 는 어떻게 구할지 고민..
- 동일하면 안됨
- 입력 순서대로

root
- next[26]
- firstIdx(생각못함)

+) 이 노드(알파벳)를 가장 먼저 거쳐간 단어의 인덱스
*/
public class BOJ_2179 {
	static class Node {
		Node[] next = new Node[26];
		int firstIdx = -1;
	}

	static int maxLen = -1;
	static int bestS = -1;
	static int bestT = -1;

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		String[] sArr = new String[n];
		Node root = new Node();
		for(int i = 0; i < n; i++){
			st = new StringTokenizer(br.readLine());
			String str = st.nextToken();
			sArr[i] = str;

			insert(str, i, root);
		}

		if(bestS!=-1 && bestT!=-1){
			System.out.println(sArr[bestS]);
			System.out.println(sArr[bestT]);
		}
	}

	static void insert(String str, int curIdx, Node root){
		Node cur = root;

		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			int charIdx = c - 'a';

			if(cur.next[charIdx] == null){
				cur.next[charIdx] = new Node();
				cur.next[charIdx].firstIdx = curIdx;
			} else {
				checkAndUpdate(cur.next[charIdx].firstIdx, curIdx, i+1);
			}
			cur = cur.next[charIdx];
		}
	}

	static void checkAndUpdate(int idx1, int idx2, int len){
		if(len > maxLen){
			maxLen = len;
			bestS = idx1;
			bestT = idx2;
		} else if (len == maxLen){
			if(idx1 < bestS){
				bestS = idx1;
				bestT = idx2;
			}
		}
	}
}
