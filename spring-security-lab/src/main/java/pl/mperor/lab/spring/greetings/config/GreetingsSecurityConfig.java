package pl.mperor.lab.spring.greetings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class GreetingsSecurityConfig {

    @Bean
    public SecurityFilterChain greetingsSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .authorizeHttpRequests(authorizeHttp -> {
                    authorizeHttp.requestMatchers("/").permitAll();
                    authorizeHttp.requestMatchers("/error").permitAll();
                    authorizeHttp.requestMatchers("/favicon.ico").permitAll();
                    authorizeHttp.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .with(new RobotLoginConfigurer()
                        .password("beep-boop")
                        .password("boop-beep"), Customizer.withDefaults()
                )
                .authenticationProvider(new HackerAuthenticationProvider())
                .addFilterBefore(new ForbiddenFilter(), AuthenticationFilter.class)
                .build();
    }
}
