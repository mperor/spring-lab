package pl.mperor.lab.spring.data.jpa.connection.issues.sample;

import jakarta.persistence.*;

@Entity
@Table(name = "dummies")
class DummyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
