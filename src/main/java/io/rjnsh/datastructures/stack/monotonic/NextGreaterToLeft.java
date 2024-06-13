package io.rjnsh.datastructures.stack.monotonic;

import java.util.Deque;
import java.util.LinkedList;

public class NextGreaterToLeft {
    public int[] nextGreaterToLeft(int[] nums) {
        final int N = nums.length;
        int[] ans = new int[N];

        Deque<Integer> stack = new LinkedList<>();
        for (int idx = 0;idx < N;idx++) {
            int currElement = nums[idx];

            // for the current element get the prev greater to left
            // invalidate all smaller or equal elements to the left of the current element as the current element would be nge for the elements that are not processed yet on the right
            while (!stack.isEmpty() && currElement <=  stack.peek()) {
                stack.pop();
            }

            ans[idx] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(currElement);
        }

        // all elements would have got the ngl set as per the above iter, as we are setting ngl at every index
        return ans;
    }
}
