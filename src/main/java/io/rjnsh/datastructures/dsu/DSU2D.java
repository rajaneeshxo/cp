package io.rjnsh.datastructures.dsu;

import java.util.Arrays;

public class DSU2D {
    private static final int[][] DIR = new int[][] {
        {0, 1},
        {1, 0}
    };

    public int findMaxFish(int[][] grid) {
        int ans = 0;
        if (grid == null || grid.length == 0) {
            return ans;
        }

        final int R = grid.length;
        final int C = grid[0].length;

        // init nodes from grid
        UF uf = new UF(R * C);
        for (int row = 0;row < R;row++) {
            for (int col = 0;col < C;col++) {
                if (grid[row][col] == 0) {
                    continue;
                }

                uf.addNode(row * C + col, grid[row][col]);
            }
        }

        for (int row = 0;row < R;row++) {
            for (int col = 0;col < C;col++) {
                if (grid[row][col] == 0) {
                    continue;
                }

                int currCell = row * C + col;
                for (int[] dir :  DIR) {
                    int nx = row + dir[0];
                    int ny = col + dir[1];

                    if (nx < 0 || nx >= R || ny < 0 || ny >= C || grid[nx][ny] == 0) {
                        continue;
                    }

                    uf.union(currCell, nx * C + ny);
                }
            }
        }

        // get the components
        for (int i = 0;i < R * C;i++) {
            if (uf.parent[i] == i) {
                ans = Math.max(uf.fishes[i] , ans);
            }
        }

        return ans;
    }
}

class UF {
    int[] parent;
    int[] size;
    int[] fishes;
    
    public UF (final int N) {
        this.parent = new int[N];
        this.size = new int[N];
        this.fishes = new int[N];
        Arrays.fill(parent, N);
    }

    public void addNode(int node, int fishCount) {
        parent[node] = node;
        fishes[node] = fishCount;
        size[node] = 1;
    }

    public int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return parent[x] = find(parent[x]);
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

        size[px] += size[py];

        fishes[px] += fishes[py];
        parent[py] = px;
        return true;
    }
}