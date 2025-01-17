package pl.mperor.lab.spring.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEventPublisher publisher) throws Exception {
        var authManager = new ProviderManager(new RobotAuthenticationProvider(List.of("beep-boop", "boop-beep")));
        authManager.setAuthenticationEventPublisher(publisher);

        return http
                .authorizeHttpRequests(authorizeHttp -> {
                    authorizeHttp.requestMatchers("/").permitAll();
                    authorizeHttp.requestMatchers("/error").permitAll();
                    authorizeHttp.requestMatchers("/favicon.ico").permitAll();
                    authorizeHttp.anyRequest().authenticated();
                })
                .addFilterBefore(new RobotFilter(authManager), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(new HackerAuthenticationProvider())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password("{noop}password")
                        .authorities("ROLE user")
                        .build()
        );
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> System.out.println("🎉 SUCCESS [%s] %s".formatted(
                event.getAuthentication().getClass().getSimpleName(),
                event.getAuthentication().getName())
        );
    }
}
