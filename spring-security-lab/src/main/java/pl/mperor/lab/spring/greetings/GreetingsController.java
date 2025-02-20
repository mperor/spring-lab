package pl.mperor.lab.spring.greetings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    private final GreetingService service;

    public GreetingsController(GreetingService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String publicPage() {
        return "Public page 🟢";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "Private page 🔑(Secret room)! " + service.greetUser();
    }
}
