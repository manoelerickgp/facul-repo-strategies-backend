package com.tech.students.service;

import com.tech.students.domain.Student;
import com.tech.students.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }


    public ResponseEntity<Student> getStudentById(Long id) {
        if (repository.existsById(id))
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id).get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<Page<Student>> getAllStudents(PageRequest pageRequest) {
        var students = repository.findAll(pageRequest);
        return ResponseEntity.ok().body(students);
    }

    public ResponseEntity<Student> save(Student studentReq) {
        Student studentSaved = repository.save(studentReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentSaved);
    }

    public ResponseEntity<Student> update(Long id, Student studentReq) {
        var student = repository.findById(id);
        if (student.isPresent()) {
            Student studentSaved = student.get();
            var studentUpdated = mapStudentRequestToStudent(studentReq, studentSaved);
            studentUpdated = repository.save(studentUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(studentUpdated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(studentReq);
    }

    public ResponseEntity<String> delete(Long id) {
        var student = repository.findById(id);
        if (student.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Student Deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
    }

    private Student mapStudentRequestToStudent(Student request, Student studentSaved) {
       studentSaved.setName(request.getName());
       studentSaved.setEmail(request.getEmail());
       studentSaved.setBirthDate(request.getBirthDate());
       return studentSaved;
    }
}
