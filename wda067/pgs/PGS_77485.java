import java.util.ArrayList;
import java.util.List;

class PGS_77485 {

    public List<Integer> solution(int rows, int cols, int[][] queries) {
        int[][] matrix = new int[rows + 1][cols + 1];
        List<Integer> result = new ArrayList<>();

        //행렬 초기화
        int index = 1;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                matrix[i][j] = index++;
            }
        }

        for (int[] query : queries) {
            int r1 = query[0];
            int c1 = query[1];
            int r2 = query[2];
            int c2 = query[3];

            //2차원 배열 깊은 복사
            int[][] temp = new int[rows + 1][cols + 1];
            for (int i = 0; i <= rows; i++) {
                temp[i] = matrix[i].clone();
            }
            int min = Integer.MAX_VALUE;

            for (int i = c1 + 1; i <= c2; i++) {
                matrix[r1][i] = temp[r1][i - 1];
                min = Math.min(min, matrix[r1][i]);
            }
            for (int i = r1 + 1; i <= r2; i++) {
                matrix[i][c2] = temp[i - 1][c2];
                min = Math.min(min, matrix[i][c2]);
            }
            for (int i = c2 - 1; i >= c1; i--) {
                matrix[r2][i] = temp[r2][i + 1];
                min = Math.min(min, matrix[r2][i]);
            }
            for (int i = r2 - 1; i >= r1; i--) {
                matrix[i][c1] = temp[i + 1][c1];
                min = Math.min(min, matrix[i][c1]);
            }

            result.add(min);
        }

        return result;
    }
}