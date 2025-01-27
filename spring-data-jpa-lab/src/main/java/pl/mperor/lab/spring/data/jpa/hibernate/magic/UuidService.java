package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UuidService {

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
}
