package pl.mperor.lab.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAsync
@SpringBootTest
class SpringAsyncConfigTest {

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private AsyncService asyncService;

    @Test
    void shouldHaveDefaultExecutorConfiguration() {
        assertThat(executor).isNotNull();
        assertThat(executor.getCorePoolSize()).isEqualTo(8);
        assertThat(executor.getMaxPoolSize()).isEqualTo(Integer.MAX_VALUE);
        assertThat(executor.getQueueCapacity()).isEqualTo(Integer.MAX_VALUE);
        assertThat(executor.getKeepAliveSeconds()).isEqualTo(60);
        assertThat(executor.getThreadNamePrefix()).isEqualTo("task-");
    }

    @Test
    void shouldExecuteTasksInParallel() {
        long start = System.currentTimeMillis();

        var completableFutures = IntStream.range(0, 4)
                .mapToObj(_ -> asyncService.delayedMethod(Duration.ofMillis(200)))
                .toList();
        var tasks = completableFutures.stream()
                .map(CompletableFuture::join)
                .toList();

        long duration = System.currentTimeMillis() - start;

        // 4 x 200ms sequentially = ~800ms
        // In parallel = ~200ms, so we assert under 400ms
        assertThat(duration).isLessThan(400);
        assertThat(tasks).allMatch(task -> task.startsWith("task-"));
    }

    @Test
    void shouldToRunAsynchronouslyWithoutAsyncAnnotation() {
        var done = asyncService.delayedMethod(Duration.ofMillis(10), executor)
                .join();
        assertThat(done).startsWith("task-");
    }
}