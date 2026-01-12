package ch.exxas.spring.server.data.repositories;

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
public class ClassRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ch.exxas.spring.server.data.entities.Class save(ch.exxas.spring.server.data.entities.Class clazz) {
        if (clazz.getUid() == null) {
            entityManager.persist(clazz);
            return clazz;
        } else {
            return entityManager.merge(clazz);
        }
    }

    public Optional<ch.exxas.spring.server.data.entities.Class> findById(UUID id) {
        ch.exxas.spring.server.data.entities.Class clazz = entityManager.find(ch.exxas.spring.server.data.entities.Class.class, id);
        return Optional.ofNullable(clazz);
    }

    public List<ch.exxas.spring.server.data.entities.Class> findAll() {
        TypedQuery<ch.exxas.spring.server.data.entities.Class> query = entityManager.createQuery("SELECT c FROM Class c", ch.exxas.spring.server.data.entities.Class.class);
        return query.getResultList();
    }

    public ch.exxas.spring.server.data.entities.Class update(ch.exxas.spring.server.data.entities.Class clazz) {
        return entityManager.merge(clazz);
    }

    public void deleteById(UUID id) {
        ch.exxas.spring.server.data.entities.Class clazz = entityManager.find(ch.exxas.spring.server.data.entities.Class.class, id);
        if (clazz != null) {
            entityManager.remove(clazz);
        }
    }

    public Optional<ch.exxas.spring.server.data.entities.Class> findByClassName(String className) {
        TypedQuery<ch.exxas.spring.server.data.entities.Class> query = entityManager.createQuery("SELECT c FROM Class c WHERE c.className = :className", ch.exxas.spring.server.data.entities.Class.class);
        query.setParameter("className", className);
        List<ch.exxas.spring.server.data.entities.Class> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<ch.exxas.spring.server.data.entities.Class> findByIdWithStudents(UUID id) {
        TypedQuery<ch.exxas.spring.server.data.entities.Class> query = entityManager.createQuery(
            "SELECT c FROM Class c LEFT JOIN FETCH c.students WHERE c.uid = :id", ch.exxas.spring.server.data.entities.Class.class);
        query.setParameter("id", id);
        List<ch.exxas.spring.server.data.entities.Class> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
