package pl.mperor.lab.spring.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.IntStream;

class BCryptWorkFactorTest {

    @Test
    void shouldVerifyBCryptWorkFactorPerformance() {
        IntStream.rangeClosed(10, 14)
                .forEach(BCryptWorkFactorTest::generate);
    }

    private static void generate(int workfactor) {
        var encoder = new BCryptPasswordEncoder(workfactor);
        long start = System.currentTimeMillis();
        encoder.encode("password");
        long end = System.currentTimeMillis();
        System.out.println("It took " + (end - start) + "[ms] for workfactor of " + workfactor);
    }
}
