package pl.mperor.lab.spring.data.jpa.connection.issues.sample;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InternalService {

    private final DummyRepository dummyRepository;

    public InternalService(DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Transactional//(propagation = Propagation.REQUIRES_NEW)
    public void runInNewTransaction() {
        try {
            dummyRepository.findAll();
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
