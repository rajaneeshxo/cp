package io.rjnsh.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Mutex {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Aggregator aggregator = new Aggregator(new ReentrantLock(), 0L);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0;i < 100;i++) {
            tasks.add(new SampleTask(i, aggregator));
        }
        
        List<Future<Integer>> resultFutures = executorService.invokeAll(tasks);
        for (int i = 0;i < resultFutures.size();i++) {
            System.out.println("task " + resultFutures.get(i).get() + " complete");
        }

        executorService.shutdown();

        System.out.println("after aggregation " + String.valueOf(aggregator.getSum()));
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class SampleTask implements Callable<Integer>{
    private int id;
    private static final Random RAND = new Random();
    private Aggregator aggregator;
    @Override
    public Integer call() throws Exception {
        System.out.println("id is " + id);
        Thread.sleep(RAND.nextInt(1000));
        aggregator.aggregate(id);
        return id;
    }

}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Aggregator {
    private Lock lock;
    private long sum;
    
    public void aggregate(int id) {
        lock.lock();
        sum += id;
        lock.unlock();
    }
}


