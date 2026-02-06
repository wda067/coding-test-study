package hangeunhyeong.pgs;
/*
그리디 : 최적해 != 탐욕해 -> X
완전탐색 : 시간복잡도 O(11Hn) -> O
*/
class PGS_92342 {
    public int[] apeach;
    public int[] solution(int n, int[] info) {
        apeach = info.clone();
        dfs(0, n, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0, 0);
        if(diff <= 0)
            return new int[]{-1};
        return result;
    }
    public int diff = 0;
    public int[] result;
    // n : idx, remain : 남아있는 화살 수, lion : 라이언이 맞힌 과녁, apeachScore : 어피치의 점수, lionScore : 라이언의 점수
    public void dfs(int n, int remain, int[] lion, int apeachScore, int lionScore){

        if(n == 11){
            // 화살이 남았으면 점수가 낮은 과녁에 추가
            if(remain > 0)
                lion[lion.length - 1] += remain;
            // 점수차 갱신
            if(diff < lionScore - apeachScore){
                diff = lionScore - apeachScore;
                result = lion;
                // System.out.print(diff + ": [");
                // printLion(lion);
            }
            // 점수차가 같은 경우 점수가 가장 낮은쪽에 더 많이 맞힌 경우를 채택
            else if(diff != 0 && diff == lionScore - apeachScore && better(lion.clone(), result.clone())){
                result = lion;
            }

        }
        if(n > 10)
            return;
        // i가 0일 때는 어피치가 점수를 획득한 경우, i가 1일때는 라이언이 점수를 획득한 경우
        for(int i = 0; i < 2; i++){
            int arrow = i == 0 ? 0 : apeach[n] + 1;  // (10 - n)점에 쏜 화살 개수
            // 화살을 쏠 수 있고 라이언이 승리한 경우
            if(remain - arrow >= 0 && i == 1){
                int[] after = lion.clone();
                after[n] = apeach[n] + 1;
                dfs(n + 1, remain - (apeach[n] + 1), after, apeachScore, lionScore + 10 - n);
            }
            //  화살을 쏠 수 없는 경우 어피치의 승리가 되므로 pass
            else if(remain - arrow < 0 && i == 1)
                continue;
                // 어피치가 승리한 경우(i==0)
            else{
                if(apeach[n] == 0)
                    dfs(n + 1, remain - 0, lion.clone(), apeachScore, lionScore);
                else
                    dfs(n + 1, remain - 0, lion.clone(), apeachScore + 10 - n, lionScore);
            }

        }
    }
    public void printLion(int[] lion){
        for(int i : lion){
            System.out.print(i + " ");
        }
        System.out.print("]\n");
    }
    public boolean better(int[] lion, int[] result){
        for(int i = lion.length - 1; i >= 0; i--){
            if(lion[i] != result[i]){
                return lion[i] > result[i] ? true : false;
            }
        }
        return false;
    }
}

