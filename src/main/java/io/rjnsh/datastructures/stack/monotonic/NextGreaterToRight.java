package io.rjnsh.datastructures.stack.monotonic;


import java.util.Deque;
import java.util.LinkedList;

public class NextGreaterToRight {
    public int[] nextGreaterToRight(int[] nums) {
        if (nums == null) {
            return nums;
        }

        final int N = nums.length;
        int[] ans = new int[N];

        Deque<Integer> stack = new LinkedList<>();
        for (int idx = 0;idx < N;idx++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[idx]) {
                int prevIdx = stack.pop();
                ans[prevIdx] = nums[idx];
            }

            stack.push(idx);
        }

        // check if there are any pending indexes for which there is ngr
        while (!stack.isEmpty()) {
            ans[stack.pop()] = -1;
        }

        return ans;
    }
}
