package pl.mperor.lab.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpringCoreLabApplicationTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Beans.BeanInstantiationTracker tracker;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testBeansConfiguration() {
        assertThat(context.containsBean("singleton")).isTrue();
        assertThat(context.containsBean("prototype")).isTrue();

        assertThat(tracker.containsInitialized("singleton")).isTrue();
        assertThat(tracker.containsInitialized("prototype")).isFalse();

        assertThat(context.getBean(Beans.Singleton.class)).isSameAs(context.getBean(Beans.Singleton.class));
        assertThat(context.getBean(Beans.Prototype.class)).isNotSameAs(context.getBean(Beans.Prototype.class));
    }
}