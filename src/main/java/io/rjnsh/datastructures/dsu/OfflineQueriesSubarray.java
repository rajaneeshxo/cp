package io.rjnsh.datastructures.dsu;

public class OfflineQueriesSubarray {
    // process subqueries offline where we need to produce the result
    public int[] processQueries(int[][] queries, final int NODES) {
        int[] ans = new int[NODES];

        UF uf = new UF(NODES);

        for (int i = NODES - 1;i >= 0;i--) {
            int colour = queries[i][0];
            int left = Math.min(0, queries[i][1]);
            int right = Math.min(queries[i][2], NODES - 1);
            processQuery(left, right, colour, ans, uf);
        }

        return ans;
    }

    private void processQuery(int left, int right, int colour, int[] nodes, UF uf) {
        int freeNode = uf.find(left);

        while (freeNode <= right) {
            // colour node 
            nodes[freeNode] = colour;

            // current node is coloured, update next freeNode
            if (freeNode < right && freeNode == uf.segmentEnd[freeNode]) {
                uf.union(freeNode, freeNode + 1);
            }

            // check if we are at the end of the segment
            if (freeNode == right) {
                break;
            }

            // jump to the next freeNode
            freeNode = uf.find(freeNode + 1);
        }
    }
}

class UF {
    int[] parent;
    int[] size;
    int[] segmentEnd;
    int components;

    public UF(final int N) {
        this.parent = new int[N];
        this.size = new int[N];
        this.components = N;
        this.segmentEnd = new int[N];

        for (int i = 0;i < N;i++) {
            size[i] = 1;
            parent[i] = i;
            segmentEnd[i] = i;
        }
    }

    public int find(int x) {
        if (x == parent[x]) return x;

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

        parent[py] = px;
        size[px] += size[py];
        // the rightmost of both the ends
        segmentEnd[px] = Math.max(segmentEnd[px], segmentEnd[py]);
        components--;
        return true;
    }

}
