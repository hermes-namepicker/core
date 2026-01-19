package ch.exxas.spring.server.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.exxas.spring.server.data.entities.SchoolClass;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class ClassRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public SchoolClass save(SchoolClass clazz) {
        if (clazz.getUid() == null) {
            entityManager.persist(clazz);
            return clazz;
        } else {
            return entityManager.merge(clazz);
        }
    }

    public Optional<SchoolClass> findById(UUID id) {
        SchoolClass clazz = entityManager.find(SchoolClass.class, id);
        return Optional.ofNullable(clazz);
    }

    public List<SchoolClass> findAll() {
        TypedQuery<SchoolClass> query = entityManager.createQuery("SELECT c FROM Class c", SchoolClass.class);
        return query.getResultList();
    }

    public SchoolClass update(SchoolClass clazz) {
        return entityManager.merge(clazz);
    }

    public void deleteById(UUID id) {
        SchoolClass clazz = entityManager.find(SchoolClass.class, id);
        if (clazz != null) {
            entityManager.remove(clazz);
        }
    }

    public Optional<SchoolClass> findByClassName(String className) {
        TypedQuery<SchoolClass> query = entityManager.createQuery("SELECT c FROM Class c WHERE c.className = :className", SchoolClass.class);
        query.setParameter("className", className);
        List<SchoolClass> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<SchoolClass> findByIdWithStudents(UUID id) {
        TypedQuery<SchoolClass> query = entityManager.createQuery(
            "SELECT c FROM Class c LEFT JOIN FETCH c.students WHERE c.uid = :id", SchoolClass.class);
        query.setParameter("id", id);
        List<SchoolClass> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
