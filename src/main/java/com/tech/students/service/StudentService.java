package com.tech.students.service;

import com.tech.students.domain.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private static Map<Long, Student> studentList = new HashMap<>();

    private ResponseEntity<Student> getStudentById(Long id) {
        Student student = studentList.get(id);
        if (student == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    private List<Student> getAllStudents() {
        return new ArrayList<>(studentList.values());
    }

    private ResponseEntity<Student> save(Long id, Student studentReq) {
        Student student = studentList.get(studentReq.getId());
        if (student !=  null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (studentList.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        studentList.put(id, studentReq);
        return ResponseEntity.status(HttpStatus.OK).body(studentReq);
    }

    private ResponseEntity<Student> update(Long id, Student studentReq) {
        boolean existsStudent = studentList.containsKey(id);
        if (existsStudent) {
            studentList.put(studentReq.getId(), studentReq);
            return ResponseEntity.status(HttpStatus.OK).body(studentReq);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentReq);
    }

    private ResponseEntity<String> delete(Long id) {
        boolean existsStudent = studentList.containsKey(id);
        if (!existsStudent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        studentList.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student Deleted  successfully");
    }
}
