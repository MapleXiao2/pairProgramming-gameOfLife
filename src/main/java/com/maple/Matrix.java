package com.maple;

public class Matrix {

    public static int[][] m = new int[3][3];

    public static int getSum(int[][] a, int pos_y, int pos_x) {
        int sum = 0;
        for(int i=pos_y-1;i<=pos_y +1;i++) {
            for (int j = pos_x - 1; j <= pos_x + 1; j++) {
                sum += m[i-pos_y+1][j-pos_x+1] * a[i][j];
            }
        }
        return sum;
    }

    public static int[][] test(int[][] a, int width, int length) {
        int[][] result = new int[width][length];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(i == 1&&j == 1)
                    continue;
                m[i][j] = 1;
            }
        }
        int[][] b = new int[width+2][length+2];
        for(int i=0;i<width;i++) {
            for (int j = 0; j < length; j++) {
                b[i + 1][j + 1] = a[i][j];
            }
        }
        for(int i=0;i<width;i++){
            for(int j=0;j<length;j++){
                result[i][j] = getSum(b,i+1,j+1);
            }
        }
        return result;
    }


    public static Cell[][] initial() {
        Cell[][] cells = new Cell[Constants.WIDTH][Constants.LENGTH];
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                boolean isLive = Math.random() > 0.5;
                cells[i][j] = new Cell(i, j, isLive, 0);
            }
        }
        int[][] livingNumArray = updateLivingNum(cells);
        updateLivingStatus(cells,livingNumArray);
        return cells;
    }

    public static int[][] updateLivingNum(Cell[][] cells) {
        int[][] a = new int[Constants.WIDTH][Constants.LENGTH];
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                a[i][j] = cells[i][j].isLive() ? 1 : 0;
            }
        }
        return test(a,Constants.WIDTH,Constants.LENGTH);
    }

    public static void updateLivingStatus(Cell[][] cells,int[][] livingNumArray){
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                cells[i][j].setLivingNum(livingNumArray[i][j]);
                cells[i][j].update();
            }
        }
    }

    public static void updateCells(Cell[][] cells){
        int[][] livingNumArray = updateLivingNum(cells);
        updateLivingStatus(cells,livingNumArray);
    }
}
