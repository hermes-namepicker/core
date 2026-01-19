package ch.exxas.spring.server.domain;

import ch.exxas.spring.server.data.entities.Student;
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

    public Student createStudent(String name) {
        Student student = new Student(name);
        return studentRepository.save(student);
    }

    public Student createStudent(String name, UUID classId) {
        Student student = new Student(name);
        if (classId != null) {
            return studentRepository.assignToClass(studentRepository.save(student).getUid(), classId);
        }
        return studentRepository.save(student);
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
