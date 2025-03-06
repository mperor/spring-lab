package pl.mperor.lab.spring.jackson;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.Map;

@JsonTest
public class JacksonTest {

    @Autowired
    private JacksonTester<Sample> sampleTester;
    @Autowired
    private JacksonTester<Map<String, Sample>> wrappedTester;

    record Sample(int integer, String string) {}

    @Test
    void testSampleMarshalling() throws IOException {
        var sample = new Sample(1, "Text");
        JsonContent<Sample> content = sampleTester.write(sample);
        Assertions.assertThat(content).isEqualToJson("""
                {
                    "integer" : 1,
                    "string" : "Text"
                }
                """
        );

        var wrapped = Map.of("sample", sample);
        Assertions.assertThat(wrappedTester.write(wrapped)).isEqualToJson("""
                {
                    "sample" : {
                       "integer" : 1,
                        "string" : "Text"
                    }
                }
                """
        );
    }

}
