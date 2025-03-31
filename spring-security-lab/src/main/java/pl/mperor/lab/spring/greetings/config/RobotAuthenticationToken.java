package pl.mperor.lab.spring.greetings.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collections;
import java.util.List;

public class RobotAuthenticationToken extends AbstractAuthenticationToken {

    private final String password;

    public RobotAuthenticationToken(List<GrantedAuthority> authorities, String password) {
        super(authorities);
        this.password = password;
        super.setAuthenticated(password == null);
    }

    public static RobotAuthenticationToken unauthenticated(String password) {
        return new RobotAuthenticationToken(Collections.emptyList(), password);
    }

    public static RobotAuthenticationToken authenticated() {
        return new RobotAuthenticationToken(AuthorityUtils.createAuthorityList("ROLE_ROBOT"), null);
    }

    @Override
    public Object getPrincipal() {
        return "Ms Robot 🤖";
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Can't touch this 🎵");
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public String getPassword() {
        return password;
    }
}
