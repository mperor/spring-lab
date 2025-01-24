package pl.mperor.lab.spring.data.jpa.connection.issues.sample;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
class SampleService {

    private final DummyRepository dummyRepository;
    private final ExternalService externalService;
    private final InternalService internalService;
    private final TransactionTemplate transactionTemplate;

    SampleService(DummyRepository dummyRepository, ExternalService externalService, InternalService internalService, TransactionTemplate transactionTemplate) {
        this.dummyRepository = dummyRepository;
        this.externalService = externalService;
        this.internalService = internalService;
        this.transactionTemplate = transactionTemplate;
    }

    List<DummyEntity> getDummies() {
        return dummyRepository.findAll();
    }

    @Transactional
    List<DummyEntity> dummiesBeforeExternalServiceCall() {
        externalService.externalCall();
        return getDummies();
    }

    // @Transactional
    List<DummyEntity> dummiesAfterExternalServiceCall() {
        var dummies = transactionTemplate.execute(_ -> getDummies());
        externalService.externalCall();
        return dummies;
    }

    // @Transactional
    List<DummyEntity> dummiesWithNestedTransaction() {
        var dummies = transactionTemplate.execute(_ -> getDummies());
        internalService.runInNewTransaction();
        return dummies;
    }
}
