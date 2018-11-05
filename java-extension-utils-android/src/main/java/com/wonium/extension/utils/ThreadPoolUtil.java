package com.wonium.extension.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public enum ThreadPoolUtil {
    INSTANCE;
    //阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
    private static BlockingQueue workQueue = new ArrayBlockingQueue(30);
    //线程池
    private static ThreadPoolExecutor threadPool;

    //线程工厂
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "myThreadPool thread:" + integer.getAndIncrement());
        }
    };

    static {
        int CORE_POOL_SIZE = 15;
        int MAX_POOL_SIZE = 50;
        int KEEP_ALIVE_TIME = 1000 * 30;
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory);
    }


    public void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public Future<?> submit(Runnable runnable) {
        return threadPool.submit(runnable);
    }

    public void execute(FutureTask futureTask) {
        threadPool.execute(futureTask);
    }

    public void cancel(FutureTask futureTask) {
        futureTask.cancel(true);
    }


}
