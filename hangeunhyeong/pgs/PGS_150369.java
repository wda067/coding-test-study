package hangeunhyeong.pgs;

import java.util.*;
/*
PGS_150369
[접근]
- 물류창고에서 먼집부터 시작해서 배달해야하는 택배들을 최대로 챙긴 후 돌아오는 길에 최대한 많이 수거하는 것이 이득
- 단순히 배달하고 수거하는 것을 일일히 반복문으로 돌리면 O(n^2)의 시간복잡도 => X
- 누적합을 이용하여 반환점을 기록
 */
public class PGS_150369 {

    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        for(int i = n - 2; i >= 0; i--){
            deliveries[i] += deliveries[i + 1];
            pickups[i] += pickups[i + 1];
        }
        int dCnt = 0, pCnt = 0;
        ArrayList<Integer> dList = new ArrayList<>();
        ArrayList<Integer> pList = new ArrayList<>();
        for(int i = n - 1; i >= 0; i--){
            while(deliveries[i] != 0 && (deliveries[i] - 1) / cap >= dCnt){
                dList.add(i + 1);
                dCnt++;
            }
            while(pickups[i] != 0 && (pickups[i] - 1) / cap >= pCnt){
                pList.add(i + 1);
                pCnt++;
            }
        }

        long dis = 0;
        for(int i = 0; i < Math.max(dList.size(), pList.size()); i++){
            if(i < dList.size() && i < pList.size())
                dis += Math.max(dList.get(i), pList.get(i)) * 2;
            else if(i >= dList.size())
                dis += pList.get(i) * 2;
            else
                dis += dList.get(i) * 2;
        }

        return dis;
    }
    // gpt가 푼 것
    public long solution2(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        long dRem = 0; // i..n-1 구간 배달 남은 총량
        long pRem = 0; // i..n-1 구간 수거 남은 총량

        for (int i = n - 1; i >= 0; i--) {
            dRem += deliveries[i];
            pRem += pickups[i];

            while (dRem > 0 || pRem > 0) {
                dRem -= cap;
                pRem -= cap;
                answer += (long)(i + 1) * 2;
            }
        }
        return answer;
    }
}
