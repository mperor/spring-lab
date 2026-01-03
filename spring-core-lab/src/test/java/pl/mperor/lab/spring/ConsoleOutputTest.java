package pl.mperor.lab.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class ConsoleOutputTest {

    @Test
    void shouldCaptureConsoleOutput(CapturedOutput output) {
        System.out.println("Hello world!");
        System.err.println("Error occurred");

        assertThat(output.getOut()).contains("Hello world!");
        assertThat(output.getErr()).contains("Error occurred");
    }
}
