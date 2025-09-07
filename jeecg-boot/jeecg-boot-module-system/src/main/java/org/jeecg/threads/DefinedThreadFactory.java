package org.jeecg.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefinedThreadFactory implements ThreadFactory
{
    private final AtomicInteger atomicInteger = new AtomicInteger(1);
    private final String name;

    public DefinedThreadFactory(String name) {
        this.name = name;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(this.name + "-" + String.format("%03d", this.atomicInteger.getAndIncrement()));
        return thread;
    }
}
