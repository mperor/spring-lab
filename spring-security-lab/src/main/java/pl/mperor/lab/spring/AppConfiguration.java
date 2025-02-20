package pl.mperor.lab.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password("{noop}password")
                        .authorities("READ","ROLE_USER")
                        .build()
        );
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> System.out.printf(
                "🎉 SUCCESS [%s] 🆔⇒%s 🔐⇒%s%n",
                event.getAuthentication().getClass().getSimpleName(),
                event.getAuthentication().getName(),
                event.getAuthentication().getAuthorities()
        );
    }
}
