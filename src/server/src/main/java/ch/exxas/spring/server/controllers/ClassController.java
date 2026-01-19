package ch.exxas.spring.server.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.*;

import ch.exxas.spring.server.data.entities.SchoolClass;
import ch.exxas.spring.server.domain.ClassDomain;

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
    public ResponseEntity<List<SchoolClass>> getAllClasses() {
        List<SchoolClass> classes = classDomain.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<SchoolClass> getClassById(@PathVariable UUID id) {
        Optional<SchoolClass> clazz = classDomain.getClassById(id);
        return clazz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/classes/name/{name}")
    public ResponseEntity<SchoolClass> getClassByName(@PathVariable String name) {
        Optional<SchoolClass> clazz = classDomain.getClassByName(name);
        return clazz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/classes/{id}/with-students")
    public ResponseEntity<SchoolClass> getClassByIdWithStudents(@PathVariable UUID id) {
        Optional<SchoolClass> clazz = classDomain.getClassByIdWithStudents(id);
        return clazz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/classes")
    public ResponseEntity<SchoolClass> createClass(@RequestBody ClassCreateRequest request) {
        SchoolClass clazz = classDomain.createClass(request.getClassName());
        return ResponseEntity.status(HttpStatus.CREATED).body(clazz);
    }

    @PutMapping("/classes/{id}")
    public ResponseEntity<SchoolClass> updateClass(@PathVariable UUID id, @RequestBody SchoolClass updateData) {
        Optional<SchoolClass> existingOpt = classDomain.getClassById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SchoolClass existing = existingOpt.get();
        existing.setClassName(updateData.getClassName());
        SchoolClass updated = classDomain.updateClass(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable UUID id) {
        Optional<SchoolClass> existingOpt = classDomain.getClassById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        classDomain.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
