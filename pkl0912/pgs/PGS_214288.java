package pkl0912.pgs;
import java.util.*;

public class PGS_214288 {

    public int solution(int k, int n, int[][] reqs) {
        int answer = 0;

        List<List<Time>> timeForEachType = new ArrayList<>();
        for (int i = 0; i < k + 1; i++) {
            timeForEachType.add(new ArrayList<>());
        }

        for (int[] req : reqs) {
            int startTime = req[0];
            int duration = req[1];
            int type = req[2];

            timeForEachType.get(type).add(new Time(startTime, startTime + duration));
        }

    
        int[][] waitTimeForEachTime = new int[k + 1][n + 1];
        for (int type = 1; type < k + 1; type++) { // 유형

            if (timeForEachType.get(type).size() == 0) continue;

            for (int counselors = 1; counselors <= (n-k)+1; counselors++) {

                int waitTime = calculationTime(timeForEachType.get(type), counselors);
                waitTimeForEachTime[type][counselors] = waitTime;
            }
        }

        int[] currentCounselors = new int[k + 1];
        for (int type = 1; type < k + 1; type++) {
            currentCounselors[type] = 1;
        }

        int remainCounselorNumber = n - k;

        while (remainCounselorNumber-- > 0) {
            int maxReduceTime = 0; 
            int correspondingType = 0;

            for (int type = 1; type < k + 1; type++) {
  
                int currentCounselorsByType = currentCounselors[type];


                int waitingTimeOfCurrentCounselors = waitTimeForEachTime[type][currentCounselorsByType];

     
                int next = waitTimeForEachTime[type][currentCounselorsByType + 1];


                int reduceWaitingTime = Math.abs(waitingTimeOfCurrentCounselors - next);

                if (reduceWaitingTime >= maxReduceTime) {
                    maxReduceTime = reduceWaitingTime;
                    correspondingType = type;
                }
            }

            if (maxReduceTime == 0) break;

            currentCounselors[correspondingType]++;
        }

        for (int type = 1; type < k + 1; type++) {

            if (timeForEachType.get(type).size() == 0) continue;

            int counselors = currentCounselors[type];

            answer += waitTimeForEachTime[type][counselors];
        }

        return answer;
    }

    private int calculationTime(List<Time> typeTime, int counselorNumber) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(); 
        int waitTime = 0;

        for (Time t : typeTime) { 
            if (pq.isEmpty() || pq.size() < counselorNumber) { 
                pq.add(t.end);
            } else {
  
                int earlyConsultEndTime = pq.poll();

                if (t.start < earlyConsultEndTime) { 
                    waitTime += (earlyConsultEndTime - t.start);
                    pq.add(earlyConsultEndTime + (t.end - t.start));
                } else {
                    pq.add(t.end); // 대기시간이 없는 경우 종료시간 저장
                }
            }
        }
        return waitTime;
    }

    private class Time {
        int start, end;

        public Time(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}