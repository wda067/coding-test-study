/*
[풀이]
- 그리디 방식
    - aIdx, bIdx 두개로 나누어서
    - 여기서 최댓값에 기준을 맞추기
*/
public class PSG_150369 {
	public long solution(int cap, int n, int[] deliveries, int[] pickups) {
		long answer = 0;

		int aIdx = n - 1;
		int bIdx = n - 1;

		while (aIdx >= 0 || bIdx >= 0) {
			if (aIdx < 0 && bIdx < 0) break;

			int aCap = cap;
			int bCap = cap;
			int aTmp = -1;
			int bTmp = -1;

			// 배달
			while (aIdx >= 0 && aCap > 0) {
				if (deliveries[aIdx] == 0) { aIdx--; continue; }
				if (aCap >= deliveries[aIdx]) {
					aCap -= deliveries[aIdx];
					deliveries[aIdx] = 0;
					aTmp = Math.max(aTmp, aIdx);
					aIdx--;
				} else {
					deliveries[aIdx] -= aCap;
					aTmp = Math.max(aTmp, aIdx);
					break;
				}
			}

			// 수거
			while (bIdx >= 0 && bCap > 0) {
				if (pickups[bIdx] == 0) { bIdx--; continue; }
				if (bCap >= pickups[bIdx]) {
					bCap -= pickups[bIdx];
					pickups[bIdx] = 0;
					bTmp = Math.max(bTmp, bIdx);
					bIdx--;
				} else {
					pickups[bIdx] -= bCap;
					bTmp = Math.max(bTmp, bIdx);
					break;
				}
			}

			answer += (Math.max(aTmp, bTmp) + 1L) * 2;
		}

		return answer;
	}
}