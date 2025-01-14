package pl.mperor.lab.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    public String greetUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Welcome ~[%s]~".formatted(authentication.getName());
    }
}
