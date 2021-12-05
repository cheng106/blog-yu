package idv.cheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author cheng
 * @since 2021/12/5 16:30
 **/
@Configuration
@EnableAsync // 開啟多執行緒
public class ThreadPoolConfig {
    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心執行緒數量
        executor.setCorePoolSize(5);
        // 設定最大執行緒數量
        executor.setMaxPoolSize(20);
        // 設定佇列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 設定執行緒活躍時間
        executor.setKeepAliveSeconds(60);
        // 設定執行緒預設名稱
        executor.setThreadNamePrefix("apiExecutor");
        // 等待所有任務結束後再關閉執行緒池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}
