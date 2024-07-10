package io.rjnsh.datastructures.dsu;

import java.util.Arrays;

public class DSU {
    int[] parent;
    int[] size;
    int components;

    public DSU(final int N) {
        this.components = N;

        this.size = new int[N];
        this.parent = new int[N];
        Arrays.fill(size, 1);
        Arrays.fill(parent, -1);
    }

    public int getComponents() {
        return this.components;
    }

    public int find(int x) {
        if (-1 == parent[x]) {
            return x;
        }

        parent[x] = find(parent[x]);
        return parent[x];
    }

    public boolean union(int x, int y) {
        validateNodeIds(x, y);
        int parentX = find(x);
        int parentY = find(y);

        if (parentX == parentY) {
            return false;
        }

        if (size[parentX] < size[parentY]) {
            int temp = parentX;
            parentX = parentY;
            parentY = temp;
        }

        parent[parentY] = parentX;
        
        size[parentX] += size[parentY];
        components--;
        return true;
    }

    public boolean areConnected(int x, int y) {
        return find(x) == find(y);
    }

    public void validateNodeIds(int... nodes) {
        for (int nodeId : nodes) {
            if (nodeId < 0 || nodeId >= size.length) {
                throw new IllegalArgumentException("invalid node id " + nodeId);
            }
        }
    }

    public static void main(String[] args) {
        DSU dsu = new DSU(5);

        int[][] edges = new int[][]{{1,2}, {3,4}, {4,2}};

        for (int[] edge : edges) {
            dsu.union(edge[0], edge[1]);
        }

        System.out.println(dsu.areConnected(3, 0));
        System.out.println(dsu.areConnected(2, 3));

        System.out.println(dsu.getComponents());
    }
    
}
