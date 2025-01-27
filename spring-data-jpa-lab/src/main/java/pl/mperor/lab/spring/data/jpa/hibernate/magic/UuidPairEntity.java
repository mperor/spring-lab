package pl.mperor.lab.spring.data.jpa.hibernate.magic;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "uuid_pairs")
class UuidPairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UpdateTimestamp
    private LocalDateTime modified;
    @OneToOne
    private UuidEntity left;
    @OneToOne
    private UuidEntity right;

    Long getId() {
        return id;
    }

    LocalDateTime getModified() {
        return modified;
    }

    UuidEntity getLeft() {
        return left;
    }

    void setLeft(UuidEntity left) {
        this.left = left;
    }

    UuidEntity getRight() {
        return right;
    }

    void setRight(UuidEntity right) {
        this.right = right;
    }
}
