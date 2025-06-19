package pl.mperor.lab.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
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

    @Test
    void shouldReturnWelcomeWhenNameNotBlank() {
        tester.post()
                .uri("/hello/{name}", "NotBlank")
                .exchange()
                .assertThat().hasStatusOk()
                .bodyText().isEqualTo("🤚 Welcome NotBlank!");
    }

    @Test
    void shouldReturn400WhenNotValidContent() {
        tester.post()
                .uri("/hello/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "josh",
                            "email": "not a valid email"
                        }""")
                .exchange()
                .assertThat().hasStatus4xxClientError();
    }
}