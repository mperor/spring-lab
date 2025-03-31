package pl.mperor.lab.spring.greetings.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class HackerAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        if ("hacker".equals(username)) {
            var hacker = User.withUsername("hacker")
                    .password("~~ignored~~") // 😎 hacker don't need password
                    .roles("admin", "user")
                    .build();
            return UsernamePasswordAuthenticationToken.authenticated(
                    hacker,
                    null,
                    hacker.getAuthorities()
            );
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
