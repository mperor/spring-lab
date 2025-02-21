package pl.mperor.lab.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SpringWebLabApplicationTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldReturnHealthStatusUp() {
        client.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }

}