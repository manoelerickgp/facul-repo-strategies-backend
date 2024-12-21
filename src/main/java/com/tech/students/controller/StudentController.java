package com.tech.students.controller;

import com.tech.students.domain.Student;
import com.tech.students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/students")
public class StudentController {


    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        return service.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<Student> save(@RequestBody Student student) {
        return service.save(student);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return service.update(student);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return service.delete(id);
    }
}
