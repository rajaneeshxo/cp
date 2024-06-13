package io.rjnsh.datastructures.stack.monotonic;

import java.util.Deque;
import java.util.LinkedList;

public class NextSmallerToLeft {
    public int[] nextSmallerToLeft(int[] nums) {

        if (nums == null) return nums;

        final int N = nums.length;
        int[] ans = new int[N];

        Deque<Integer> stack = new LinkedList<>();
        for (int idx = 0;idx < N;idx++) {
            // monotonic increasing queue for which smaller elements need to be found
            int currElement = nums[idx];

            while (!stack.isEmpty() && stack.peek() >= currElement) {
                // invalidate stack, remove greater elements to the left
                stack.pop();
            }

            ans[idx] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(currElement);
        }
        
        return ans;
    }
}
