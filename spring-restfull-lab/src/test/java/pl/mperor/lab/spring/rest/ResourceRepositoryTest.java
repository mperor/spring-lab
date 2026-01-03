package pl.mperor.lab.spring.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceRepositoryTest {

    @Autowired
    MockMvcTester tester;

    @Test
    public void shouldAllowToCreateResourceAsRest() {
        tester.get().uri("/resources")
                .exchange()
                .assertThat()
                .hasStatusOk();

        var result = tester.post()
                .uri("/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "description" : "1️⃣",
                            "ready" : true
                        }
                        """)
                .exchange();
        result.assertThat().hasStatus(HttpStatus.CREATED);

        var id = result.getResponse().getHeader("location");
        tester.get().uri("/resources/{id}", id)
                .exchange()
                .assertThat()
                .hasStatusOk();
    }

}