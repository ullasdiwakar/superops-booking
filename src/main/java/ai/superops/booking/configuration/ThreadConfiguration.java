package ai.superops.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class ThreadConfiguration {
    @Bean
    public ScheduledThreadPoolExecutor threadPoolTaskExecutor() {
        return (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
    }

}
