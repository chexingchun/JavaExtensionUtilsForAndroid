/*
 * Copyright  2018.  wonium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.wonium.extension.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: ThreadPoolUtil.java
 * @Description: 类描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/12 21:06
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/12 21:06
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum ThreadPoolUtil {
    /**
     * 实例对象
     */
    INSTANCE;
    /**
     * 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
     */

    private static BlockingQueue workQueue = new ArrayBlockingQueue(30);
    /**
     * 线程池
     */
    private static ThreadPoolExecutor threadPool;

    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "myThreadPool thread:" + integer.getAndIncrement());
        }
    };

    static {
        int corePoolSize = 15;
        int maxPoolSize = 50;
        int keepAliveTime = 1000 * 30;
        threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory);
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
    /**
     * 定时任务
     *
     * @param runnable
     * @param initialDelay 首次执行间隔时间
     * @param period       之后每次间隔时间
     * @param timeUnit     时间单位
     */

    public ScheduledExecutorService schedule(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit) {
        ScheduledExecutorService  scheduledExecutorService = new ScheduledThreadPoolExecutor(2, threadFactory);
        scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
        return scheduledExecutorService;
    }

    /**
     * 取消定时任务
     */
    public void cancel(ScheduledExecutorService service) {
        if (service!=null){
            service.shutdownNow();
        }
    }

}
