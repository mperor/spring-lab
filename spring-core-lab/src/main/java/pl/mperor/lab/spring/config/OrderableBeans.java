package pl.mperor.lab.spring.config;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class OrderableBeans {
    @Bean
    Orderable defaultPlusPriorityOrder() {
        return new Orderable();
    }

    @Bean
    @Order(0)
    Orderable zeroPriorityOrder() {
        return new Orderable();
    }

    @Bean
    @Order(Integer.MIN_VALUE)
    Orderable minusPriorityOrder() {
        return new Orderable();
    }

    static class Orderable implements BeanNameAware {
        String beanName;

        @Override
        public void setBeanName(String name) {
            this.beanName = name;
        }

        String getBeanName() {
            return beanName;
        }
    }
}
