package hangeunhyeong.boj;
import java.util.*;
import java.io.*;
/*
20437번 : 문자열게임 2
hashMap에 각 문자의 위치들을 나열(key : 문자, value : 문자열에서의 문자의 위치들을 나열한 arrayList)
어떤 문자를 정확히 K개를 포함하는 가장 짧은 연속 문자열의 길이를 구한다.
=> arrayList를 순환하며 K만큼 떨어져 있는 값을 구해 최솟값을 찾음
어떤 문자를 정확히 K개를 포함하고, 문자열의 첫 번째와 마지막 글자가 해당 문자로 같은 가장 긴 연속 문자열의 길이를 구한다.
=> arrayList를 순환하며 K만큼 떨어져 있는 값을 구해 최댓값을 찾음
 */
public class BOJ_20437 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int i = 0; i < T; i++){
            String W = br.readLine();
            int K = Integer.parseInt(br.readLine());
            HashMap<Character, ArrayList<Integer>> hashMap = new HashMap<>();

            // hashMap에 저장(key : 알파벳, value : 문자열 W에 있는 key 문자의 위치를 나열
            for(int j = 0; j < W.length(); j++){
                Character alphabet = W.charAt(j);
                if(hashMap.get(alphabet) == null)
                    hashMap.put(alphabet, new ArrayList<>());
                hashMap.get(alphabet).add(j);
            }
            // 어떤 문자를 정확히 K개를 포함하는 가장 짧은 연속 문자열의 길이를 구한다.
            int min = 10001;
            // 어떤 문자를 정확히 K개를 포함하고, 문자열의 첫 번째와 마지막 글자가 해당 문자로 같은 가장 긴 연속 문자열의 길이를 구한다.
            int max = -1;
            for(char c = 'a'; c <= 'z'; c++){
                ArrayList<Integer> arr = hashMap.get(c);
                for(int j = 0; arr != null && arr.size() >= K && j + K - 1 < arr.size(); j++){
                    int len = arr.get(j + K - 1) - arr.get(j) + 1;
                    if(min > len)
                        min = len;
                    if(max < len)
                        max = len;
                }
            }
            if(min == 10001 || max == -1)
                System.out.println(-1);
            else
                System.out.println(min + " " + max);
        }
    }
}
