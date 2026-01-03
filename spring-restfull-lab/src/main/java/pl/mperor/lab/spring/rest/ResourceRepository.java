package pl.mperor.lab.spring.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "resources")
interface ResourceRepository extends JpaRepository<ResourceEntity, Integer> {

    List<ResourceEntity> findByReady(boolean ready);
}
