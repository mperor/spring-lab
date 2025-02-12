package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
class UuidService {

    private final UuidRepository uuidRepository;
    private final UuidPairRepository pairRepository;

    public UuidService(UuidRepository uuidRepository, UuidPairRepository pairRepository) {
        this.uuidRepository = uuidRepository;
        this.pairRepository = pairRepository;
    }

    void combinePairs(UUID leftUuid, UUID rightUuid) {
        var left = findOrThrows(leftUuid);
        var right = findOrThrows(rightUuid);
        var pair = new UuidPairEntity();
        pair.setLeft(left);
        pair.setRight(right);
        pairRepository.save(pair);
    }

    private UuidEntity findOrThrows(UUID uuid) {
        return uuidRepository.findById(uuid).orElseThrow();
    }

    UuidEntity save(String name) {
        var entity = new UuidEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setName(name);
        return uuidRepository.save(entity);
    }

    List<UuidEntity> findAll() {
        return uuidRepository.findAll();
    }

    List<UuidPairEntity> findPairs() {
        return pairRepository.findAll();
    }

    // Spring rolls back only for unchecked exceptions (RuntimeException and Error) by default.
    // Use @Transactional(rollbackFor = Exception.class) to rollback on checked exceptions.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void saveWithException(String name, Supplier<Exception> exceptionToThrow) throws Exception {
        save(name);
        throw exceptionToThrow.get();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    void saveWithRollbackForException(String name, Supplier<Exception> exceptionToThrow) throws Exception {
        save(name);
        throw exceptionToThrow.get();
    }

    long count() {
        return uuidRepository.count();
    }
}
