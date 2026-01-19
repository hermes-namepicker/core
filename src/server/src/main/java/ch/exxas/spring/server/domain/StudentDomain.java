package ch.exxas.spring.server.domain;

import ch.exxas.spring.server.data.entities.Student;
import ch.exxas.spring.server.data.entities.SchoolClass;
import ch.exxas.spring.server.data.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentDomain {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassDomain classDomain;

    public Student createStudent(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }
        return studentRepository.save(new Student(name.trim()));
    }

    public Student createStudent(String name, UUID classId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }

        if (classId != null) {
            // Fetch the class entity first
            Optional<SchoolClass> clazz = classDomain.getClassById(classId);
            if (clazz.isPresent()) {
                Student student = new Student(name.trim(), clazz.get());
                return studentRepository.save(student);
            } else {
                throw new IllegalArgumentException("Class with ID " + classId + " not found");
            }
        }
        return studentRepository.save(new Student(name.trim()));
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    public List<Student> getStudentsByClassId(UUID classId) {
        return studentRepository.findByClassId(classId);
    }

    public Student updateStudent(Student student) {
        return studentRepository.update(student);
    }

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    public Student assignStudentToClass(UUID studentId, UUID classId) {
        return studentRepository.assignToClass(studentId, classId);
    }

    public Student removeStudentFromClass(UUID studentId) {
        return studentRepository.removeFromClass(studentId);
    }
}
