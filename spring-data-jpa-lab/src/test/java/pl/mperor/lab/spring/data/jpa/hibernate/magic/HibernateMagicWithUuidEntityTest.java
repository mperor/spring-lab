package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = {UuidRepository.class, UuidPairRepository.class})
@EntityScan(basePackageClasses = {UuidEntity.class, UuidPairEntity.class, CreatorEntity.class})
@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.session_factory.statement_inspector=pl.mperor.lab.spring.data.jpa.hibernate.magic.SqlStatementInterceptor",
})
@ComponentScan(basePackageClasses = UuidService.class)
public class HibernateMagicWithUuidEntityTest {

    @Autowired
    private UuidService service;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        SqlStatementInterceptor.resetQueryCount();
    }

    @Test
    public void testSaveAndGetNewUuid_useOnlyTwoQueries() {
        service.save("New");
        var all = service.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(2, SqlStatementInterceptor.getQueryCount());
    }

    // @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    public void testCombineNewPair_useOnlyThreeQueriesInTransactional() {
        System.out.println("given ...");
        var left = service.save("Left");
        var right = service.save("Right");

        System.out.println("when ...");
        service.combinePairs(left.getUuid(), right.getUuid());

        System.out.println("then ...");
        Assertions.assertEquals(3, SqlStatementInterceptor.getQueryCount());
        Assertions.assertEquals(1, service.findPairs().size());
    }

    @Test
    public void testGetUuidEntitiesWithCreators_solveNPlus1SelectProblem() {
        System.out.println("given ...");
        var one = service.save("One");
        one.setCreator(new CreatorEntity("One Creator"));
        var two = service.save("Two");
        two.setCreator(new CreatorEntity("Two Creator"));
        var three = service.save("Three");
        three.setCreator(new CreatorEntity("Three Creator"));

        entityManager.flush();
        entityManager.clear();
        SqlStatementInterceptor.resetQueryCount();

        System.out.println("when ...");
        var all = service.findAll();

        System.out.println("then ...");
        all.stream()
                .map(UuidEntity::getCreator)
                .map(CreatorEntity::getName)
                .forEach(Assertions::assertNotNull);
        Assertions.assertEquals(1, SqlStatementInterceptor.getQueryCount());
    }
}