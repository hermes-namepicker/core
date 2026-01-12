package ch.exxas.spring.server.data.repositories;

import ch.exxas.spring.server.data.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class StudentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Student save(Student student) {
        if (student.getUid() == null) {
            entityManager.persist(student);
            return student;
        } else {
            return entityManager.merge(student);
        }
    }

    public Optional<Student> findById(UUID id) {
        Student student = entityManager.find(Student.class, id);
        return Optional.ofNullable(student);
    }

    public List<Student> findAll() {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    public Student update(Student student) {
        return entityManager.merge(student);
    }

    public void deleteById(UUID id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student);
        }
    }

    public Optional<Student> findByName(String name) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class);
        query.setParameter("name", name);
        List<Student> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Student> findByClassId(UUID classId) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.clazz.uid = :classId", Student.class);
        query.setParameter("classId", classId);
        return query.getResultList();
    }

    public Student assignToClass(UUID studentId, UUID classId) {
        Student student = entityManager.find(Student.class, studentId);
        if (student != null && classId != null) {
            ch.exxas.spring.server.data.entities.Class clazz = entityManager.find(ch.exxas.spring.server.data.entities.Class.class, classId);
            student.setClazz(clazz);
            return entityManager.merge(student);
        }
        return student;
    }

    public Student removeFromClass(UUID studentId) {
        Student student = entityManager.find(Student.class, studentId);
        if (student != null) {
            student.setClazz(null);
            return entityManager.merge(student);
        }
        return student;
    }
}
