package ch.exxas.spring.server.domain;

import ch.exxas.spring.server.data.entities.SchoolClass;
import ch.exxas.spring.server.data.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassDomain {
    @Autowired
    private ClassRepository classRepository;

    public SchoolClass createClass(String className) {
        SchoolClass clazz = new SchoolClass(className);
        return classRepository.save(clazz);
    }

    public Optional<SchoolClass> getClassById(UUID id) {
        return classRepository.findById(id);
    }

    public List<SchoolClass> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<SchoolClass> getClassByName(String className) {
        return classRepository.findByClassName(className);
    }

    public Optional<SchoolClass> getClassByIdWithStudents(UUID id) {
        return classRepository.findByIdWithStudents(id);
    }

    public SchoolClass updateClass(SchoolClass clazz) {
        return classRepository.update(clazz);
    }

    public void deleteClass(UUID id) {
        classRepository.deleteById(id);
    }
}
