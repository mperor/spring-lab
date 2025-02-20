package pl.mperor.lab.spring.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturnUnauthorizedWithNoJwt() throws Exception {
        this.mvc.perform(get("/api/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWithInvalidJwt() throws Exception {
        this.mvc.perform(get("/api/hello").header(HttpHeaders.AUTHORIZATION, "Bearer FAKEINCORRECTTOKEN"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnWelcomeMessageWithValidJwt() throws Exception {
        var token = this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("user", "password")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(token).isNotEmpty();

        MvcResult response = this.mvc.perform(get("/api/hello").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Hello World 🌐", response.getResponse().getContentAsString());
    }
}