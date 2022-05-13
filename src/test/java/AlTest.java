public class AlTest {
    int[][] m = new int[3][3];

    int getSum(int[][] a, int pos_y, int pos_x) {
        int sum = 0;
        for (int i = pos_y - 1; i < pos_y + 1; i++) {
            for (int j = pos_x - 1; j < pos_x + 1; j++) {
                sum += m[i][j] * a[i][j];
            }
        }
        return sum;
    }

    int[][] test(int[][] a, int width, int length) {
        int[][] result = new int[width][length];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1)
                    continue;
                this.m[i][j] = 1;
            }
        }
        int[][] b = new int[width + 2][length + 2];
        for (int i = 0; i < width; i++) {
            System.arraycopy(a[i], 0, b[i + 1], 1, length);
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                result[i][j] = getSum(b, i, j);
            }
        }
        return result;
    }
}
