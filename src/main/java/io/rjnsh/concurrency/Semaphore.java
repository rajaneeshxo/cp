package io.rjnsh.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.Getter;


public class Semaphore {
    
    private static final int MAX_ACQUIRE_COUNT = 5;
    private Lock lock;
    
    @Getter
    private int currAcquireCount;
    
    public Semaphore() {
        this.lock = new ReentrantLock();
        this.currAcquireCount = 0;
    }

    public boolean acquire() {
        try { 
            lock.lock();
            if (currAcquireCount < MAX_ACQUIRE_COUNT) {
                currAcquireCount++;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean release() {
        try {
            lock.lock();
            if (currAcquireCount < 1) {
                return false;
            }
            currAcquireCount--;
            return true;
        } finally {
            lock.unlock();
        }
    }
}
