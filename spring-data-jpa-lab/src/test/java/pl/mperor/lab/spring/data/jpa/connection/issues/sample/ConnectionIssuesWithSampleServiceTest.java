package pl.mperor.lab.spring.data.jpa.connection.issues.sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest(properties = {
        // Manages lazy loading during view rendering
        "spring.jpa.open-in-view=false",
        // Automatic transaction commit (transaction opening/closing)
        "spring.datasource.hikari.auto-commit=false",
})
@ComponentScan(basePackages = "pl.mperor.lab.spring.data.jpa.connection.issues.sample")
public class ConnectionIssuesWithSampleServiceTest {

    @Autowired
    private DummyRepository repository;
    @Autowired
    private SampleService sampleService;

    private final DummyEntity dummy = createDummy("Dummy 1");

    private static DummyEntity createDummy(String name) {
        var dummy = new DummyEntity();
        dummy.setName(name);
        return dummy;
    }

    @BeforeEach
    void setUp() {
        repository.save(dummy);
    }

    @Test
    void testGetDummies() {
        Assertions.assertEquals(dummy, sampleService.getDummies().getFirst());
    }

    @Test
    void testDummiesBeforeExternalServiceCall() {
        Assertions.assertEquals(dummy, sampleService.dummiesBeforeExternalServiceCall().getFirst());
    }

    @Test
    void testDummiesAfterExternalServiceCall() {
        Assertions.assertEquals(dummy, sampleService.dummiesAfterExternalServiceCall().getFirst());
    }

    @Test
    void testDummiesWithNestedTransaction() {
        Assertions.assertEquals(dummy, sampleService.dummiesWithNestedTransaction().getFirst());
    }
}