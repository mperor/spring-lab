package pl.mperor.lab.spring.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturnJwtWithValidUserCredentials() throws Exception {
        this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedWithInvalidUserCredentials() throws Exception {
        this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isUnauthorized());
    }
}