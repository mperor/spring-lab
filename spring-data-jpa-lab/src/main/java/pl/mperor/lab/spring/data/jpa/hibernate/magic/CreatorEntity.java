package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "creators")
class CreatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @UpdateTimestamp
    private LocalDateTime modified;

    CreatorEntity() {
    }

    CreatorEntity(String name) {
        this.name = name;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
