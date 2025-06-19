package pl.mperor.lab.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hello")
@RestController
class HelloController {

    @GetMapping
    String hello() {
        return "🗺️ Hello World!";
    }

    @PostMapping("/{name}")
    String welcomeName(@PathVariable @NotBlank String name) {
        return "🤚 Welcome %s!".formatted(name);
    }

    @PostMapping("/user")
    String welcomeUser(@RequestBody @Valid HelloUser user) {
        return "🤚 Welcome 👤 %s (%s)!".formatted(user.name, user.email);
    }

    record HelloUser(@NotNull @Size(min = 3) String name,
                     @NotNull @Email String email,
                     @Min(0) Integer age,
                     @Pattern(regexp = "^\\d{3}[\\s-]?\\d{3}[\\s-]?\\d{3}$") String phoneNumber) {
    }
}