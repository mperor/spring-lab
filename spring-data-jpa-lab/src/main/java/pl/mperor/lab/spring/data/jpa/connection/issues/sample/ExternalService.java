package pl.mperor.lab.spring.data.jpa.connection.issues.sample;

import org.springframework.stereotype.Service;

@Service
class ExternalService {

    void externalCall() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
