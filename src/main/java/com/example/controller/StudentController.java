package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.StudentsDto;
import com.example.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addStudent(@Valid @RequestBody StudentsDto student) {
        Map<String, Object> response = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(), "Student added successfully", response));
    }

    @PatchMapping("/{id}/addSubjects")
    public ResponseEntity<ApiResponse> addSubjects(@PathVariable int id, @RequestBody Map<String, Integer> subjects) {
        Map<String, Integer> updatedSubjects = studentService.addSubjects(id, subjects);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK.value(), "Subjects updated successfully", updatedSubjects));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK.value(), "Students retrieved successfully", studentService.getAllStudents()));
    }


}
