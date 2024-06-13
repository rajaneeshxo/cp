package io.rjnsh.datastructures.stack.monotonic;

import java.util.Deque;
import java.util.LinkedList;

public class NextSmallerToRight {
    public int[] nextSmallerToRight(int[] nums) {
        final int N = nums.length;
        int[] ans = new int[N];

        Deque<Integer> stack = new LinkedList<>();
        for (int idx = 0;idx < N;idx++) {
            int currElement = nums[idx];
            //  0 1 3 7 8 1
            while (!stack.isEmpty() && currElement < nums[stack.peek()]) {
                int prevIdx = stack.pop();
                ans[prevIdx] = currElement;
            }

            stack.push(idx);
        }

        while (!stack.isEmpty()) {
            ans[stack.pop()] = -1;
        }

        return ans;
    }
}
