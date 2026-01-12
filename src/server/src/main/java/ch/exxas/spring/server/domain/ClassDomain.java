package ch.exxas.spring.server.domain;

import ch.exxas.spring.server.data.entities.Class;
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

    public Class createClass(String className) {
        Class clazz = new Class(className);
        return classRepository.save(clazz);
    }

    public Optional<Class> getClassById(UUID id) {
        return classRepository.findById(id);
    }

    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<Class> getClassByName(String className) {
        return classRepository.findByClassName(className);
    }

    public Optional<Class> getClassByIdWithStudents(UUID id) {
        return classRepository.findByIdWithStudents(id);
    }

    public Class updateClass(Class clazz) {
        return classRepository.update(clazz);
    }

    public void deleteClass(UUID id) {
        classRepository.deleteById(id);
    }
}
