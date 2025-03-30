package pl.mperor.lab.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWithTomcatTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnHealthStatusUp() {
        var body = RestClient.create()
                .get()
                .uri("http://localhost:{port}/actuator/health", port)
                .retrieve()
                .body(String.class);
        Assertions.assertThat(body).contains("{\"status\":\"UP\"}");

        var restTemplateBody = restTemplate.getForObject(URI.create("/actuator/health"), String.class);
        Assertions.assertThat(restTemplateBody).contains("{\"status\":\"UP\"}");
    }

}