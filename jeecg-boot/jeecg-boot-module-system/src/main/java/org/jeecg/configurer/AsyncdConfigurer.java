package org.jeecg.configurer;

import org.jeecg.threads.DefinedThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置类
 * @author 路遥
 */
@Configuration
public class AsyncdConfigurer implements AsyncConfigurer
{
    @Override
    public Executor getAsyncExecutor()
    {
        ThreadFactory threadFactory = new DefinedThreadFactory("async-pool");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 100, 60, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
        // 线程池中corePoolSize线程空闲时间达到keepAliveTime时是否关闭
        executor.allowCoreThreadTimeOut(true);

        return executor;
    }
}
