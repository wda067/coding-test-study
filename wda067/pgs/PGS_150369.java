/*
프로그래머스 / 택배 배달과 수거하기 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/150369
 */
class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;

        int d = n - 1;  // 배달해야 할 가장 먼 인덱스
        int p = n - 1;  // 수거해야 할 가장 먼 인덱스

        // 마지막 위치 탐색
        while (d >= 0 && deliveries[d] == 0) d--;
        while (p >= 0 && pickups[p] == 0) p--;

        // 배달 또는 수거해야 할 집이 남아있을 때
        while (d >= 0 || p >= 0) {
            int far = Math.max(d, p) + 1;   // 거리(집 번호)
            answer += (long) far * 2;       // 왕복 거리 누적

            // 배달 처리 (cap 만큼)
            int remain = cap;

            // 가장 먼 집부터 처리
            while (d >= 0 && remain > 0) {
                int curD = deliveries[d];

                // 현재 집 패스
                if (curD == 0) {
                    d--;
                    continue;
                }

                // 현재 집에서 배달이 끝날 때
                if (curD >= remain) {
                    deliveries[d] -= remain;
                    remain = 0;
                }

                // 택배가 남았으면 다음 집으로 이동
                else {
                    deliveries[d] = 0;
                    remain -= curD;
                    d--;
                }
            }

            // 수거 처리 (cap 만큼)
            remain = cap;

            // 가장 먼 집부터 처리
            while (p >= 0 && remain > 0) {
                int curP = pickups[p];

                // 현재 집 패스
                if (curP == 0) {
                    p--;
                    continue;
                }

                // 현재 집에서 수거가 끝날 때
                if (curP >= remain) {
                    pickups[p] -= remain;
                    remain = 0;
                }

                // 수거 용량이 남았을 경우 다음 집 이동
                else {
                    pickups[p] = 0;
                    remain -= curP;
                    p--;
                }
            }

            // 마지막 인덱스 갱신
            while (d >= 0 && deliveries[d] == 0) d--;
            while (p >= 0 && pickups[p] == 0) p--;
        }

        return answer;
    }
}
