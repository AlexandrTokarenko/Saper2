package com.example.saper.matrix;


public class GridView {

    private int[][] grid = new int[12][12];

    public GridView() {

        fillGrid();
    }

    public int[][] getGrid() {

        return grid;
    }

    // элементы массива инициализирует значением 10
    private void fillGrid() {

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                grid[i][j] = 10;
            }
        }
    }
}
