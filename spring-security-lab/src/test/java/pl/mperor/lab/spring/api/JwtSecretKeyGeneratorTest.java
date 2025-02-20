package pl.mperor.lab.spring.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.function.Function;

public class JwtSecretKeyGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(JwtSecretKeyGeneratorTest.class);

    private Function<Integer, String> generator = (bytes) -> {
        byte[] key = new byte[bytes];
        new SecureRandom().nextBytes(key);
        return bytesToHex(key);
    };

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Test
    public void testGenerateNewJwtSecretKey() {
        var jwtKey = generator.apply(32); // 256-bit key
        logger.info("Generated JWT Key: {}", jwtKey);
        Assertions.assertThat(jwtKey)
                .isNotNull()
                .hasSize(64)  // 32 bytes in HEX = 64 characters
                .matches("^[0-9a-fA-F]+$"); // only hexadecimal characters
    }
}
