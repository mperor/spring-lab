package pl.mperor.lab.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/")
    public String publicPage() {
        return "Public page 🟢";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "Private page 🔑️!";
    }

}
