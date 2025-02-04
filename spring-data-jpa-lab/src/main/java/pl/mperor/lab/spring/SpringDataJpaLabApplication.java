package pl.mperor.lab.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SpringDataJpaLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaLabApplication.class, args);
    }

}
