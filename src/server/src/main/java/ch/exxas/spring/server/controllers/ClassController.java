package ch.exxas.spring.server.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.*;
import ch.exxas.spring.server.domain.ClassDomain;
import ch.exxas.spring.server.data.entities.Class;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/services/internal")
public class ClassController {
    @Autowired
    private ClassDomain classDomain;

    public static class ClassCreateRequest {
        private String className;

        public ClassCreateRequest() {}

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
    }

    @GetMapping("/classes")
    public ResponseEntity<List<Class>> getAllClasses() {
        List<Class> classes = classDomain.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<Class> getClassById(@PathVariable UUID id) {
        Optional<Class> clazz = classDomain.getClassById(id);
        return clazz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/classes/name/{name}")
    public ResponseEntity<Class> getClassByName(@PathVariable String name) {
        Optional<Class> clazz = classDomain.getClassByName(name);
        return clazz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/classes/{id}/with-students")
    public ResponseEntity<Class> getClassByIdWithStudents(@PathVariable UUID id) {
        Optional<Class> clazz = classDomain.getClassByIdWithStudents(id);
        return clazz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/classes")
    public ResponseEntity<Class> createClass(@RequestBody ClassCreateRequest request) {
        Class clazz = classDomain.createClass(request.getClassName());
        return ResponseEntity.status(HttpStatus.CREATED).body(clazz);
    }

    @PutMapping("/classes/{id}")
    public ResponseEntity<Class> updateClass(@PathVariable UUID id, @RequestBody Class updateData) {
        Optional<Class> existingOpt = classDomain.getClassById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Class existing = existingOpt.get();
        existing.setClassName(updateData.getClassName());
        Class updated = classDomain.updateClass(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable UUID id) {
        Optional<Class> existingOpt = classDomain.getClassById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        classDomain.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
