package pl.mperor.lab.spring.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpringBeansConfigTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ScopedBeans.BeanInstantiationTracker tracker;

    @Qualifier("defaultPlusPriorityOrder")
    @Autowired
    private OrderableBeans.Orderable defaultOrder;
    @Autowired
    private List<OrderableBeans.Orderable> orderables;

    @Test
    public void shouldInitializeSingletonAndPrototypeScopesCorrectly() {
        assertThat(context.containsBean("singleton")).isTrue();
        assertThat(context.containsBean("prototype")).isTrue();

        assertThat(tracker.containsInitialized("singleton")).isTrue();
        assertThat(tracker.containsInitialized("prototype")).isFalse();

        assertThat(context.getBean(ScopedBeans.Singleton.class)).isSameAs(context.getBean(ScopedBeans.Singleton.class));
        assertThat(context.getBean(ScopedBeans.Prototype.class)).isNotSameAs(context.getBean(ScopedBeans.Prototype.class));
    }

    @Test
    public void shouldOrderBeansByPriority() {
        assertThat(defaultOrder)
                .extracting(OrderableBeans.Orderable::getBeanName)
                .isEqualTo("defaultPlusPriorityOrder");

        assertThat(orderables)
                .extracting(OrderableBeans.Orderable::getBeanName)
                .containsExactly("minusPriorityOrder", "zeroPriorityOrder", "defaultPlusPriorityOrder");
    }

}
