package pl.mperor.lab.spring.data.jpa.connection.issues.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DummyRepository extends JpaRepository<DummyEntity, Long> {
}
