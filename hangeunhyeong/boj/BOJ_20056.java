package hangeunhyeong.boj;

import java.io.*;
import java.util.*;

public class BOJ_20056 {
    public static ArrayList<Fireball> fireballs = new ArrayList<>();
    public static int N;
    public static void main(String[] args) throws IOException {
        int M, K;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        for(int i = 0; i < M; i++){
            String[] info = br.readLine().split(" ");
            int r, c, m, s, d;
            r = Integer.parseInt(info[0]) - 1;
            c = Integer.parseInt(info[1]) - 1;
            m = Integer.parseInt(info[2]);
            s = Integer.parseInt(info[3]);
            d = Integer.parseInt(info[4]);
            Fireball fireball = new Fireball();
            fireball.setFireball(r, c, m, s, d);
            fireballs.add(fireball);
        }
        for(int i = 0; i < K; i++){
            // 이동
            for(Fireball fireball : fireballs){
                move(fireball);
            }
            // 파이어볼 정렬
            fireballs.sort(Comparator.comparingInt((Fireball f) -> (f.x * N + f.y)));

            for(int j = 0; j < fireballs.size(); j++){
                int m = fireballs.get(j).m, s = fireballs.get(j).s;
                boolean allOdd = fireballs.get(j).d % 2 == 0 ? false : true;
                boolean allEven = fireballs.get(j).d % 2 == 0 ? true : false;
                int cnt = 0;
                // 다음 fireball과 현재 fireball이 같은동안 질량의 합, 속력의합, 방향이 모두 홀수/짝수 인지를 알아냄
                while(j + 1 < fireballs.size() && fireballs.get(j).x == fireballs.get(j + 1).x
                        && fireballs.get(j).y == fireballs.get(j + 1).y){
                     Fireball nextFireball = fireballs.get(j + 1);
                     m += nextFireball.m;
                     s += nextFireball.s;
                     allOdd &= nextFireball.d % 2 == 1;
                     allEven &= nextFireball.d % 2 == 0;
                     cnt++;
                     j++;
                }
                // 같은위치에 fireball이 여러개인경우
                if(cnt > 0){
                    // fireball 하나로 합치기
                    for(int k = 0; k < cnt; k++){
                        fireballs.remove(j - 1);
                        j--;
                    }
                    int x = fireballs.get(j).x;
                    int y = fireballs.get(j).y;
                    int dividedM = m / 5;
                    int dividedS = s / (cnt + 1);
                    int startDir = allOdd || allEven ? 0 : 1;
                    fireballs.remove(j);
                    if(dividedM == 0){
                        j--;
                        continue;
                    }
                    // fireball 4개로 나누기
                    for(int d = startDir; d <= 7; d += 2){
                        Fireball fireball = new Fireball();
                        fireball.setFireball(x, y, dividedM, dividedS, d);
                        fireballs.add(j, fireball);
                    }
                    j += 3;
                }

            }
//            for (Fireball fireball : fireballs) {
//                System.out.printf("fireball[%d][%d] : %d %d %d\n", fireball.x, fireball.y, fireball.m, fireball.s, fireball.d);
//            }
//            System.out.println();
        }
        int sum = 0;
        for (Fireball fireball : fireballs) {
            sum += fireball.m;
        }
        System.out.println(sum);
    }

    public static void move(Fireball fireball) {
        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
        int s = fireball.s;
        int d = fireball.d;
        fireball.x = (fireball.x + s * dx[d] + s * N) % N;
        fireball.y = (fireball.y + s * dy[d] + s * N) % N;

    }
    static class Fireball{
        public int x, y;    // 위치
        public int m;       // 질량
        public int s;       // 속력
        public int d;       // 방향

        public Fireball(){
            this.x = -1;
            this.y = -1;
            this.m = 0;
            this.s = 0;
            this.d = -1;
        }
        public void setFireball(int x, int y, int m, int s, int d){
            this.x = x;
            this.y = y;
            this.m = m;
            this.s = s;
            this.d = d;
        }

    }
}
