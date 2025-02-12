package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UuidPairRepository extends JpaRepository<UuidPairEntity, Long> {
}
