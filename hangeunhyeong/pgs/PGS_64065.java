package hangeunhyeong.pgs;
/*
PGS 64065 - 튜플
[문제풀이]
1. s에서 바깥중괄호를 제거하고 집합들을 분리하여 String배열에 값을 대입한다
2. String배열에서 모든 string마다 숫자를 분리하여 hashSet에 넣고 treeMap에 넣는다(key는 집합의 개수, value는 집합)
3. 이제 treeMap을 순서대로 순회하며 set에 새롭게 들어가는 원소들을 tuple배열에 저장
 */
import java.util.*;
class PGS_64065{
    public int[] solution(String s) {
        int[] tuple = {};
        // 바깥 중괄호 제거, 바깥 쉼표 제거
        String[] sets = seperate(trimStr(s));
        // printArray(sets);
        TreeMap<Integer, Set<Integer>> treeMap = new TreeMap<>();
        // 각 집합들마다 숫자를 분리하여 hashSet에 저장후 treeMap에 저장
        for(String set : sets){
            Set<Integer> hashSet = new HashSet<>();
            // 집합의 숫자들을 분리하여 hashSet에 저장
            for(String num : trimStr(set).split(",")){
                int n = Integer.parseInt(num);
                hashSet.add(n);
            }
            treeMap.put(hashSet.size(), hashSet);
        }
        tuple = new int[treeMap.size()];
        int tupleIdx = 0;
        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= treeMap.size(); i++){
            for(int j : treeMap.get(i)){
                // set에 새로 추가되었다면
                if(set.add(j)){
                    tuple[tupleIdx++] = j;
                    break;
                }
            }
        }
        return tuple;
    }
    public void printArray(String[] s){
        for(String str : s){
            System.out.println(str);
        }
        System.out.println("-------------------------------------");
    }
    // 바깥 중괄호를 없앰
    public String trimStr(String s){
        return s.substring(1, s.length() - 1);
    }
    // 집합들을 분류하여 String배열에 각각 담기(바깥 쉼표 제거)
    public String[] seperate(String s){
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            if(i - 1 >= 0 && s.charAt(i - 1) == '}' && s.charAt(i) == ','){
                list.add(sb.toString());
                sb = new StringBuilder();
            }
            else{
                sb.append(s.charAt(i));
            }
        }
        list.add(sb.toString());
        String[] result = new String[list.size()];
        int idx = 0;
        for(String str : list){
            result[idx++] = str;
        }
        return result;
    }
}