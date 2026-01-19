package ch.exxas.spring.server.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

import ch.exxas.spring.server.data.entities.Student;
import ch.exxas.spring.server.domain.StudentDomain;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/services/internal")
public class StudentController {
    @Autowired
    private StudentDomain studentDomain;

    public static class StudentCreateRequest {
        @NotBlank(message = "Student name is required")
        private String name;
        private UUID classId;

        public StudentCreateRequest() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public UUID getClassId() { return classId; }
        public void setClassId(UUID classId) { this.classId = classId; }
    }

    public static class StudentUpdateRequest {
        @NotBlank(message = "Student name is required")
        private String name;
        private UUID classId;

        public StudentUpdateRequest() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public UUID getClassId() { return classId; }
        public void setClassId(UUID classId) { this.classId = classId; }
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentDomain.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentDomain.getStudentById(id);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/students/search")
    public ResponseEntity<Student> getStudentByName(@RequestParam String name) {
        Optional<Student> student = studentDomain.getStudentByName(name);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/students/class/{classId}")
    public ResponseEntity<List<Student>> getStudentsByClassId(@PathVariable UUID classId) {
        List<Student> students = studentDomain.getStudentsByClassId(classId);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        try {
            Student student = studentDomain.createStudent(request.getName(), request.getClassId());
            return ResponseEntity.status(HttpStatus.CREATED).body(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentUpdateRequest request) {
        Optional<Student> existingOpt = studentDomain.getStudentById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student existing = existingOpt.get();
        existing.setName(request.getName());
        if (request.getClassId() != null) {
            studentDomain.assignStudentToClass(id, request.getClassId());
        } else {
            studentDomain.removeStudentFromClass(id);
        }
        Student updated = studentDomain.updateStudent(existing);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/students/{id}/assign-class/{classId}")
    public ResponseEntity<Student> assignStudentToClass(@PathVariable UUID id, @PathVariable UUID classId) {
        Optional<Student> existingOpt = studentDomain.getStudentById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student updated = studentDomain.assignStudentToClass(id, classId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/students/{id}/remove-class")
    public ResponseEntity<Student> removeStudentFromClass(@PathVariable UUID id) {
        Optional<Student> existingOpt = studentDomain.getStudentById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student updated = studentDomain.removeStudentFromClass(id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        Optional<Student> existingOpt = studentDomain.getStudentById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentDomain.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
