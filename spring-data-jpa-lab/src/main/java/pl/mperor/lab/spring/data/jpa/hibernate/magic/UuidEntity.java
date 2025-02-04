package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

// @DynamicUpdate // ensures only changed fields are included in the SQL
@Entity
@Table(name = "uuid_entities")
class UuidEntity {

    @Id
    private UUID uuid;
    private String name;
    @Version // or implements Persistable<ID>
    private Integer version;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CreatorEntity creator;

    UUID getUuid() {
        return uuid;
    }

    void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    CreatorEntity getCreator() {
        return creator;
    }

    void setCreator(CreatorEntity creator) {
        this.creator = creator;
    }
}
