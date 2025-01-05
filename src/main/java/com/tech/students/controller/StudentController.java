package com.tech.students.controller;

import com.tech.students.domain.Student;
import com.tech.students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<Student>> findAllStudents(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer itemsPerPage
    ) {

        return service.getAllStudents(PageRequest.of(page, itemsPerPage));
    }

    @PostMapping
    public ResponseEntity<Student> save(@RequestBody Student student) {
        return service.save(student);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        return service.update(id, student);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
