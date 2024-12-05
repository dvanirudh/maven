package com.example.controller;

import com.example.dto.StudentsDto;
import com.example.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentService studentService;

    @BeforeEach
    public void setup() { mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }

    @Test
    public void addStudent_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        StudentsDto studentDto = new StudentsDto();
        studentDto.setFirstName("John");
        studentDto.setLastName("Wick");
        studentDto.setBirthDate(LocalDate.of(2000, 1, 1));
        
        Map<String, Integer> subjects = new HashMap<>();
        subjects.put("Math", 90);
        subjects.put("Science", 80);
        studentDto.setSubjects(subjects);
        String studentJson = objectMapper.writeValueAsString(studentDto);

        // Act & Assert
        mockMvc.perform(post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Students data Created successfully."))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.subjects.Math").value(90))
                .andExpect(jsonPath("$.data.subjects.Science").value(80));
    }

    @Test
    public void getAllStudents_ShouldReturnStudentList() throws Exception {
        // Arrange: Create sample student data
        StudentsDto studentDto1 = new StudentsDto();
        studentDto1.setFirstName("John");
        studentDto1.setLastName("Wick");
        studentDto1.setBirthDate(LocalDate.of(2000, 1, 1));
        Map<String, Integer> subjects1 = new HashMap<>();
        subjects1.put("Math", 90);
        subjects1.put("Science", 80);
        studentDto1.setSubjects(subjects1);
    
        StudentsDto studentDto2 = new StudentsDto();
        studentDto2.setFirstName("Jane");
        studentDto2.setLastName("Wick");
        studentDto2.setBirthDate(LocalDate.of(2002, 2, 2));
        Map<String, Integer> subjects2 = new HashMap<>();
        subjects2.put("Math", 85);
        subjects2.put("Science", 88);
        studentDto2.setSubjects(subjects2);
    
        studentService.addStudent(studentDto1);
        studentService.addStudent(studentDto2);
    

        // Act & Assert:
        mockMvc.perform(get("/students/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.message").value("Students data Compileed Successfully."))
//                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[1].firstName").value("Jane"));  
    }
    
    @Test
    public void updateStudent_ShouldReturnUpdatedSubjects() throws Exception {
        // Arrange:
        StudentsDto studentDto = new StudentsDto();
        studentDto.setFirstName("John");
        studentDto.setLastName("Wick");
        studentDto.setBirthDate(LocalDate.of(2000, 1, 1));
        Map<String, Integer> subjects = new HashMap<>();
        subjects.put("Math", 90);
        subjects.put("Science", 80);
        studentDto.setSubjects(subjects);
    
        StudentsDto savedStudent = studentService.addStudent(studentDto);
        Long studentId = savedStudent.getId();

        Map<String, Integer> updatedSubjects = new HashMap<>();
        updatedSubjects.put("Math", 95);
        updatedSubjects.put("Science", 85);

        studentDto.setSubjects(updatedSubjects);
        String stringJs = objectMapper.writeValueAsString(updatedSubjects);
    

        //Act and Assert
        mockMvc.perform(put("/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringJs))
                .andExpect(status().isCreated())  
                .andExpect(jsonPath("$.message").value("Subjects data Updated Successfully."))  
                .andExpect(jsonPath("$.data.Math").value(95))  
                .andExpect(jsonPath("$.data.Science").value(85));  
    }
}