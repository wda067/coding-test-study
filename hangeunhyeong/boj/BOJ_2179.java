package hangeunhyeong.boj;

import java.util.*;
public class BOJ_2179{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.nextLine();
        ArrayList<String> sorted = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(int i = 0; i < N; i++){
            String str = sc.nextLine();
            sorted.add(str);
            list.add(str);
            hashMap.put(str, i);
        }
        Collections.sort(sorted);   // O(Nlog N)

        int max = -1, cnt = 0;
        String S = "", T = "";
        TreeSet<Integer> treeSet = new TreeSet<>();
        // O(N * 100)
        for(int i = 0; i < sorted.size() - 1; i++){
            String str1 = sorted.get(i);
            String str2 = sorted.get(i + 1);
            int minLen = Math.min(str1.length(), str2.length());
            cnt = 0;
            String prefix = "";
            // str1, str2 접두사 길이 구하기
            while(cnt < minLen && str1.charAt(cnt) == str2.charAt(cnt)){
                prefix += str1.charAt(cnt++) + "";
            }
//            System.out.printf("%s(%d) %s(%d) : %s %d\n", str1, i, str2,i+1, prefix, cnt);
            if(max < cnt){
                treeSet.clear();
                treeSet.add(hashMap.get(str1));
                treeSet.add(hashMap.get(str2));
                max = cnt;
//                System.out.printf("%s %s 넣었음\n", str1, str2);
//                System.out.println(treeSet);
            }
            else if(max == cnt){
                treeSet.add(hashMap.get(str1));
                treeSet.add(hashMap.get(str2));
            }
        }

        S = list.get(treeSet.pollFirst());
        T = list.get(treeSet.first());
        for(int i : treeSet){
            String str = list.get(i);
            int cnt2 = 0;
            // S와 str의 접두사 길이 구하기(cnt2)
            while(cnt2 < Math.min(str.length(), S.length()) && S.charAt(cnt2) == str.charAt(cnt2)){
                cnt2++;
            }
            if(cnt2 == max){
                T = str;
                break;
            }
        }

//        System.out.println(list);
//        System.out.println(sorted);
        System.out.println(S);
        System.out.println(T);

    }
}

