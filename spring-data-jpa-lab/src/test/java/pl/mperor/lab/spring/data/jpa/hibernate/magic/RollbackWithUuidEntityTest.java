package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.SoftAssertionsProvider.ThrowingRunnable;

@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = {UuidRepository.class})
@EntityScan(basePackageClasses = {UuidEntity.class})
@DataJpaTest
@ComponentScan(basePackageClasses = UuidService.class)
public class RollbackWithUuidEntityTest {

    @Autowired
    private UuidService service;

    @Test
    public void testRollbackFailedOnCheckedException() {
        long initialCount = service.count();
        assertExceptionWasThrown(() -> service.saveWithException("to-rollback", () -> new Exception("Simulated checked exception")));
        long finalCount = service.count();
        Assertions.assertEquals(initialCount + 1, finalCount);
    }

    @Test
    public void testRollbackDoneOnCheckedException() {
        long initialCount = service.count();
        assertExceptionWasThrown(() -> service.saveWithRollbackForException("to-rollback", () -> new Exception("Simulated checked exception")));
        long finalCount = service.count();
        Assertions.assertEquals(initialCount, finalCount, "Transaction should be rolled back, so count should be unchanged");
    }

    private void assertExceptionWasThrown(ThrowingRunnable runnable) {
        try {
            runnable.run();
            Assertions.fail("Expected exception was not thrown");
        } catch (Exception e) {
            System.out.println("Expected exception: " + e.getMessage());
        }
    }
}