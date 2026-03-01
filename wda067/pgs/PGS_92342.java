/*
프로그래머스 / 양궁대회 / Level 2
https://school.programmers.co.kr/learn/courses/30/lessons/92342
 */
class PGS_92342 {

    private int[] info;
    private int max = Integer.MIN_VALUE;
    private int[] answer;

    public int[] solution(int n, int[] info) {
        this.info = info;

        recur(0, n, new int[11]);

        return answer;
    }

    /*
    화살 n개를 11칸에 하나씩 배치하는 경우의 수 -> 11^n (시간 초과)

    각 점수칸마다
      1) 이김 (info[i] + 1개)
      2) 포기 (0개)
    둘 중 하나만 선택 -> 2^11
     */
    private void recur(int depth, int remain, int[] scores) {
        if (depth == 10) {
            scores[10] = remain;  // 남은 화살을 0점에 배치

            int diff = compare(scores);

            // 라이언이 진 경우
            if (diff == -1) {
                if (max == Integer.MIN_VALUE)  // 한 번도 이긴 적이 없을 때
                    answer = new int[]{-1};
                return;
            }

            // 점수차가 더 크거나,
            // 점수차가 같고 낮은 점수를 더 많이 맞힌 경우
            if (max < diff || (max == diff && better(scores))) {
                max = diff;
                answer = scores.clone();  // 현재 상태 스냅샷
            }

            scores[10] = 0;  // 원복
            return;
        }

        int need = info[depth] + 1;  // 현재 점수에서 이기기 위해 필요한 개수

        // 남은 화살 개수가 need 이상일 경우 -> 이김
        if (remain >= need) {
            scores[depth] = need;
            recur(depth + 1, remain - need, scores);
            scores[depth] = 0;
        }

        // 이 점수칸은 포기
        recur(depth + 1, remain, scores);
    }

    private int compare(int[] scores) {
        int a = 0;
        int b = 0;
        int score = 10;
        for (int i = 0; i <= 10; i++) {
            int aCnt = info[i];
            int bCnt = scores[i];

            // 현재 점수에 둘다 0개일 때는 패스
            if (aCnt == 0 && bCnt == 0) {
                score--;
                continue;
            }


            if (aCnt >= bCnt) {  // 어피치가 점수 획득
                a += score;
            } else if (aCnt < bCnt) {  // 라이언이 점수 획득
                b += score;
            }
            score--;
        }

        if (a < b) return b - a;  // 라이언이 획득했을 때 점수차 반환
        return -1;
    }

    private boolean better(int[] cur) {
        if (answer == null) return true;

        // 기존보다 낮은 점수에 더 많이 맞힌 경우 true
        for (int i = 10; i >= 0; i--) {
            if (cur[i] != answer[i])
                return cur[i] > answer[i];
        }
        return false;
    }
}


