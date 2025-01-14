package pl.mperor.lab.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GreetingsController {

    @GetMapping("/")
    public String publicPage() {
        return "Public page 🟢";
    }

    @GetMapping("/private")
    public String privatePage(Authentication authentication) {
        return """
                Private page 🔑️!
                Welcome to the VIP room ~[%s]~
                """.formatted(getName(authentication));
    }

    private String getName(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(authentication::getName);
    }

}
