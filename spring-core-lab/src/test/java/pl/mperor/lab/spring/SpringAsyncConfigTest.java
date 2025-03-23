package pl.mperor.lab.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringAsyncConfigTest {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Test
    void shouldHaveDefaultExecutorConfiguration() {
        assertThat(executor).isNotNull();
        assertThat(executor.getCorePoolSize()).isEqualTo(8);
        assertThat(executor.getMaxPoolSize()).isEqualTo(Integer.MAX_VALUE);
        assertThat(executor.getQueueCapacity()).isEqualTo(Integer.MAX_VALUE);
        assertThat(executor.getKeepAliveSeconds()).isEqualTo(60);
        assertThat(executor.getThreadNamePrefix()).isEqualTo("task-");
    }

}