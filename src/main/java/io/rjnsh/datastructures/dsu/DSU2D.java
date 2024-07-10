package io.rjnsh.datastructures.dsu;

public class DSU2D {
    int[] parent;
    int[] size;
    int components;
    private static final int[][] DIRS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public DSU2D(final int[][] grid) {
        final int ROWS = grid.length;
        final int COLS = grid[0].length;
        this.parent = new int[ROWS * COLS];
        this.size = new int[ROWS * COLS];
        initFromGrid(grid, ROWS, COLS);
    }

    private void initFromGrid(int[][] grid, int ROWS, int COLS) {
        for (int r = 0;r < ROWS;r++) {
            for (int c = 0;c < COLS;c++) {
                int currCell = r * COLS + c;
                if (grid[r][c] == -1) {
                    parent[currCell] = -1;
                    continue;
                }

                makeSet(currCell);
                // aggregate, count overall
            }
        }
    }

    private void makeSet(int x) {
        parent[x] = x;
        size[x] = 1;
        components++;
    }

    public int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    public boolean areConnected(int x, int y) {
        int parX = find(x);
        int parY = find(y);

        return parX == parY;
    }

    public boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);

        if (px == py) {
            return false;
        }

        if (size[px] < size[py]) {
            int temp = px;
            px = py;
            py = temp;
        }

        parent[py] = px;
        size[px] += size[py];
        return true;
    }
}
