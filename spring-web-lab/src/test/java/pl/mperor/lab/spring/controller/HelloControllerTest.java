package pl.mperor.lab.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest
class HelloControllerTest {

    @Autowired
    MockMvcTester tester;

    @Test
    void shouldReturnHelloWorld() {
        tester.get()
                .uri("/hello")
                .exchange()
                .assertThat().hasStatusOk()
                .bodyText().isEqualTo("🗺️ Hello World!");
    }
}