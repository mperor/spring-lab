package pl.mperor.lab.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.client.RestClient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringMockMvcTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MockMvcTester tester;

    @Test
    public void shouldReturnHealthStatusUp() throws Exception {
        mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        var restClientBody = RestClient.builder()
                .requestFactory(new MockMvcClientHttpRequestFactory(mvc)).build()
                .get()
                .uri("/actuator/health")
                .retrieve()
                .body(String.class);
        Assertions.assertThat(restClientBody).contains("{\"status\":\"UP\"}");

        tester.get().uri("/actuator/health")
                .exchange()
                .assertThat()
                .hasStatusOk()
                .bodyJson().extractingPath("$.status").isEqualTo("UP");
    }

}