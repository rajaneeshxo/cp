package io.rjnsh.datastructures.stack.impl;

import java.util.Queue;

import lombok.Getter;

@Getter
public class StackWithQueue<E extends Number> {
    private Queue<E> queue;
    // only supported operations are add and remove (enque to the rear and deque from the front)
    public StackWithQueue(Queue<E> queue) {
        this.queue = queue;
    }

    public boolean push(E element) {
        if (element == null) {
            return false;
        }
        
        // existing elements in q -> new element after enqueue
        // after push, stack should look like, new element ->  existing elements in queue

        int currentSize = queue.size();
        queue.add(element);
        while (currentSize-- > 0) {
            queue.add(queue.remove());
        }

        return true;
    }

    public E pop() {
        if (queue.isEmpty()) {
            return null;
        }

        return queue.remove();
    }

    public int size() {
        return queue.size();
    }
    

}
