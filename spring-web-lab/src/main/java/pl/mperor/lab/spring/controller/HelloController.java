package pl.mperor.lab.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
class HelloController {

    @GetMapping
    String hello() {
        return "🗺️ Hello World!";
    }

}
