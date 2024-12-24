package com.tech.students.service;

import com.tech.students.domain.Student;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private static final Map<Long, Student> studentList = new HashMap<>();

    public ResponseEntity<Student> getStudentById(Long id) {
        Student student = studentList.get(id);
        if (student == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    public ResponseEntity<List<Student>> getAllStudents() {
        var students = studentList.values().stream().toList();
        return ResponseEntity.ok().body(students);
    }

    public ResponseEntity<Student> save(Student studentReq) {
        Student student = studentList.get(studentReq.getId());
        if (student !=  null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (studentList.containsKey(studentReq.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        studentList.put(studentReq.getId(), studentReq);
        return ResponseEntity.status(HttpStatus.OK).body(studentReq);
    }

    public ResponseEntity<Student> update(Long id, Student studentReq) {
        boolean existsStudent = studentList.containsKey(id);
        if (existsStudent) {
            studentList.put(studentReq.getId(), studentReq);
            return ResponseEntity.status(HttpStatus.OK).body(studentReq);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(studentReq);
    }

    public ResponseEntity<String> delete(Long id) {
        Student student = studentList.get(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        studentList.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student Deleted successfully");
    }
}
