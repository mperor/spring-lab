package pl.mperor.lab.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttp -> {
                    authorizeHttp.requestMatchers("/").permitAll();
                    authorizeHttp.requestMatchers("/error").permitAll();
                    authorizeHttp.requestMatchers("/favicon.ico").permitAll();
                    authorizeHttp.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .oauth2Login(oauth2 ->
                        oauth2.withObjectPostProcessor(
                                new ObjectPostProcessor<AuthenticationProvider>() {
                                    @Override
                                    public <O extends AuthenticationProvider> O postProcess(O object) {
                                        return (O) new RateLimitedAuthenticationProvider(object);
                                    }
                                }
                        ))
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
}
