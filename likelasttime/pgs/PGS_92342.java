import java.util.*;
/*
    라이언이 화살 n발을 쏜다.
    점수를 계산한다.
        가장 작은 원의 과녁 점수는 10점
        가장 큰 원의 바깥쪽은 과녁 점수가 0점
        만약 k점을 어피치가 a발 맞혔고, 라이언이 b발을 맞혔다면
            더 많은 화살을 k점에 맞힌 선수가 k점을 가져간다.
        a = b라면
            어피치가 k점을 가진다.
        k점을 여러 발 맞혀도 k점만 가져간다.
        a = b = 0이라면
            어느 누구도 k점을 가져가지 않는다.
    최종 점수가 더 높은 선수가 우승자다.
    최종 점수가 같으면
        어피치를 우승자로 결정
    라이언이 무조건 지거나 비기는 경우 -1 반환
    라이언이 가장 큰 점수 차이로 우승하기 위해 n발의 화살을 어떤 과녁 점수에 맞혀야하는지 10 ~ 0을 정수 배열에 담기
    여러가지 방법이 있다면
        가장 낮은 점수를 더 많이 맞힌 경우를 반환
*/
class PGS_92342 {
    static int N;
    static int[] apeach;
    static int[] answer;
    static int maxDiff = -1;

    public int[] solution(int n, int[] info) {
        N = n;
        apeach = info;
        answer = new int[11];

        dfs(0, n, new int[11]);

        if (maxDiff == -1) return new int[]{-1};
        return answer;
    }

    // idx: 현재 점수 인덱스 (0 = 10점, 10 = 0점)
    // arrowsLeft: 남은 화살
    // rion: 라이언 화살 분배 상태
    private void dfs(int idx, int arrowsLeft, int[] rion) {
        // 종료 조건
        if (idx == 11 || arrowsLeft == 0) {
            // 남은 화살은 전부 0점에 몰아주기
            rion[10] += arrowsLeft;
            calculate(rion);
            rion[10] -= arrowsLeft;
            return;
        }

        // 이 점수 포기
        dfs(idx + 1, arrowsLeft, rion);

        // 이 점수 가져오기 (어피치보다 1발 더 필요)
        int need = apeach[idx] + 1;
        if (arrowsLeft >= need) {
            rion[idx] = need;
            dfs(idx + 1, arrowsLeft - need, rion);
            rion[idx] = 0; // 백트래킹
        }
    }

    // 점수 계산 + 최적해 갱신
    private void calculate(int[] rion) {
        int rionScore = 0;
        int apeachScore = 0;

        for (int i = 0; i < 11; i++) {
            if (rion[i] == 0 && apeach[i] == 0) continue;

            int score = 10 - i;
            if (rion[i] > apeach[i]) rionScore += score;
            else apeachScore += score;
        }

        int diff = rionScore - apeachScore;
        if (diff <= 0) return;

        if (diff > maxDiff || (diff == maxDiff && isBetter(rion, answer))) {
            maxDiff = diff;
            answer = rion.clone();
        }
    }

    // 낮은 점수를 더 많이 맞힌 경우 우선
    private boolean isBetter(int[] rion, int[] best) {
        for (int i = 10; i >= 0; i--) {
            if (rion[i] > best[i]) return true;
            if (rion[i] < best[i]) return false;
        }
        return false;
    }
}
