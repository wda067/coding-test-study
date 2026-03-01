/*
    n개의 집
    i번째 집은 물류 창고에서 거리 i만큼 떨어져 있다.
    i번째 집은 j번째 집과 거리 j - i만큼 떨어져 있다. (1 <= i <= j <= n)
    트럭에는 재활용 택배 상자를 최대 cap개 실을 수 있다.
    트럭 하나로 모든 배달과 수거를 마치고 물류창고까지 돌아올 수 있는 최소 이동 거리를 구하자.
    각 집에 배달 및 수거할 때 원하는 개수만큼 택배를 배달 및 수거한다.
*/
class PGS_150369 {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;

        int dIdx = n - 1; // 배달 끝
        int pIdx = n - 1; // 수거 끝

        while (dIdx >= 0 || pIdx >= 0) {

            // 아직 남아있는 가장 먼 집 찾기
            while (dIdx >= 0 && deliveries[dIdx] == 0) dIdx--;
            while (pIdx >= 0 && pickups[pIdx] == 0) pIdx--;

            if (dIdx < 0 && pIdx < 0) break;

            int farthest = Math.max(dIdx, pIdx);
            answer += (long)(farthest + 1) * 2;

            // 배달 처리
            int capLeft = cap;
            for (int i = dIdx; i >= 0 && capLeft > 0; i--) {
                int used = Math.min(deliveries[i], capLeft);
                deliveries[i] -= used;
                capLeft -= used;
                if (deliveries[i] == 0) dIdx = i - 1;
            }

            // 수거 처리
            capLeft = cap;
            for (int i = pIdx; i >= 0 && capLeft > 0; i--) {
                int used = Math.min(pickups[i], capLeft);
                pickups[i] -= used;
                capLeft -= used;
                if (pickups[i] == 0) pIdx = i - 1;
            }
        }

        return answer;
    }
}