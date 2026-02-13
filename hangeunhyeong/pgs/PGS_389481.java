package hangeunhyeong.pgs;

import java.util.*;
class PGS_389481 {
    public String solution(long n, String[] bans) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for(String ban : bans){
            // System.out.println(ban + " " + getN(ban));
            pq.add(getN(ban));
        }
        while(!pq.isEmpty() && pq.peek() <= n){
            pq.poll();
            n++;
        }
//        System.out.println(getSpell(26));
        return getSpell(n);
    }
    public long getN(String spell){
        int len = spell.length();
        long n = 0;
        for(int i = 0; i < len; i++){
            long idx = spell.charAt(i) - 'a' + 1;
            n = n * 26 + idx;
        }
        return n;
    }
    public String getSpell(long n) {
        n--;
        if(n / 26 == 0)
            return (char)('a' + (n % 26)) + "";
        else
            return getSpell(n / 26) + (char)('a' + (n % 26));
    }
}