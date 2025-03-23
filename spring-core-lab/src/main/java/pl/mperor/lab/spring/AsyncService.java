package pl.mperor.lab.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    public CompletableFuture<String> delayedMethod(Duration duration, Executor executor) {
        return CompletableFuture.supplyAsync(() -> task(duration), executor);
    }

    @Async
    public CompletableFuture<String> delayedMethod(Duration duration) {
        return CompletableFuture.completedFuture(task(duration));
    }

    private static String task(Duration duration) {
        try {
            var threadName = Thread.currentThread().getName();
            log.info("Time to sleep 😴 [{}] ...", threadName);
            Thread.sleep(duration);
            return threadName;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
