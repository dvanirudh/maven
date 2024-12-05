package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.StudentsDto;
import com.example.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addStudent(@RequestBody StudentsDto studentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(), "Students data Created successfully.",  studentService.addStudent(studentDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long id, @RequestBody Map<String, Integer> subjectDTO) {
        Map<String, Integer> response = studentService.updateStudent(id, subjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(), "Subjects data Updated Successfully.", response));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStudents() {
        List<StudentsDto> response = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK.value(), "Students data Compileed Successfully.",  response));
    }

}
