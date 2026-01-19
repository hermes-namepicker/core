package ch.exxas.spring.server.data.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id")
    private SchoolClass clazz;

    public Student() {}

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, SchoolClass clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public UUID getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolClass getClazz() {
        return clazz;
    }

    public void setClazz(SchoolClass clazz) {
        this.clazz = clazz;
    }
}
