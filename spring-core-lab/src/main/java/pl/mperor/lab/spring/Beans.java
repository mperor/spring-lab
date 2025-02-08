package pl.mperor.lab.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Beans {

    record Singleton() {
    }

    @Bean
    Singleton singleton() {
        return new Singleton();
    }

    record Prototype() {
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    Prototype prototype() {
        return new Prototype();
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

    @Bean
    @Order
    Orderable plusPriorityOrder() {
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

    @Bean
    static BeanInstantiationTracker tracker() {
        return new BeanInstantiationTracker();
    }

    static class BeanInstantiationTracker implements BeanPostProcessor {
        private final List<String> beanNames = new ArrayList<>();

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            beanNames.add(beanName);
            System.out.println(beanName + " " + bean);
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        public boolean containsInitialized(String name) {
            return beanNames.contains(name);
        }
    }
}