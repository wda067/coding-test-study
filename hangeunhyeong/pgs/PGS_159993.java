package hangeunhyeong.pgs;

import java.util.*;
class PGS_159993 {
    public static int[][] time;
    public static int targetX, targetY;
    public static int n, m;
    public static char[][] map;
    public int solution(String[] maps) {
        n = maps.length;
        m = maps[0].length();
        map = new char[n][m];
        time = new int[n][m];
        int startX = 0, startY = 0, entranceX = 0, entranceY = 0, leverX = 0, leverY = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                char c = maps[i].charAt(j);
                map[i][j] = c;
                switch(c){
                    case 'S':
                        startX = i; startY = j;
                        break;
                    case 'E':
                        entranceX = i;  entranceY = j;
                        break;
                    case 'L':
                        leverX = i; leverY = j;
                        break;
                }
            }
        }
        // System.out.println(startX + " " + startY + " " + entranceX + " " + entranceY + " " + leverX + " " + leverY);

        int answer = 0;
        targetX = leverX;   targetY = leverY;
        answer = bfs(startX, startY);
        if(answer == -1)
            return -1;

        targetX = entranceX;    targetY = entranceY;
        answer = bfs(leverX, leverY);
        return answer;
    }
    public int bfs(int x, int y){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(x);
        queue.add(y);
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        boolean[][] visited = new boolean[n][m];
        int nextX, nextY;
        while(!queue.isEmpty()){
            x = queue.poll();
            y = queue.poll();
            if(x == targetX && y == targetY)
                return time[x][y];
            for(int i = 0; i < 4; i++){
                nextX = x + dx[i];
                nextY = y + dy[i];
                if(isRange(nextX, nextY) && map[nextX][nextY] != 'X' && !visited[nextX][nextY]){
                    queue.add(nextX);
                    queue.add(nextY);
                    visited[nextX][nextY] = true;
                    // System.out.println(nextX + " " + nextY);
                    time[nextX][nextY] = time[x][y] + 1;
                }
            }
        }
        return -1;
    }
    public boolean isRange(int x, int y){
        return x >= 0 && x < n && y >= 0 && y < m;
    }
}