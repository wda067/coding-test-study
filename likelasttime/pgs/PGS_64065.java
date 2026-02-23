import java.util.*;
/*
    n개의 요소를 가진 튜플을 n-튜플
    중복된 원소가 있을 수 있다.
    정해진 순서가 있다. 순서가 다르면 서로 다른 튜플
    튜플의 원소 개수는 유한하다.
    중복되는 원소가 없는 튜플이 주어질 때 {, }를 이용해 표현할 수 있다.
        {{a1}, {a1, a2}, {a1, a2, a3}, {a1, a2, a3, a4}, ... {a1, a2, a3, a4, ..., an}}
        집합은 원소의 순서가 바뀌어도 상관없다.
    s가 표현하는 튜플을 배열에 담아 반환하기

    [풀이]
    s를 파싱해서 길이순으로 정렬
    오름차순으로 탐색하며 방문 안 한 애는 정답 배열에 추가
*/
class PGS_64065 {
    /*
        5 <= s의 길이 <= 1,000,000
    */
    public int[] solution(String s) {
        List<List<Integer>> lst = new ArrayList();  // 튜플들을 담는 리스트
        List<Integer> lstAnswer = new ArrayList();  // 정답을 담는 리스트
        s = s.substring(2, s.length() - 2);     // 가장 바깥의 {{ 와 }} 제거
        String[] arr = s.split("\\},\\{");  // 집합 사이의 '},{' 를 기준으로 분리

        for(int i=0; i<arr.length; i++) {
            String[] cur = arr[i].split(",");
            List<Integer> tmp = new ArrayList();
            for(String str : cur) {
                tmp.add(Integer.parseInt(str));
            }
            lst.add(tmp);
            //System.out.println(arr[i]);
        }

        // 길이를 기준으로 오름차순 정렬
        Collections.sort(lst, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> a, List<Integer> b) {
                return a.size() - b.size();     // 오름차순
            }
        });

        boolean[] visit = new boolean[100001];
        for(List<Integer> cur : lst) {
            for(int num : cur) {
                if(!visit[num]) {
                    visit[num] = true;
                    lstAnswer.add(num);
                    //System.out.println(num);
                }
            }
        }

        // 정답 배열 채우기
        int n = lstAnswer.size();
        int[] answer = new int[n];
        for(int i=0; i<n; i++) {
            answer[i] = lstAnswer.get(i);
        }

        return answer;
    }
}