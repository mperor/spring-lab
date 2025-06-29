package pl.mperor.lab.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ScopedBeans {

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