package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.StudentsDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static final Logger log = LogManager.getLogger(StudentService.class);

    private final Map<Integer, String> studentInfo = new HashMap<>();
    private final Map<Integer, Map<String, Integer>> studentSubjects = new HashMap<>();

    public Map<String, Object> addStudent(StudentsDto studentDTO) {
        validateStudentDto(studentDTO);

        if (isStudentExist(studentDTO.getId())) {
            log.warn("Student with ID {} already exists.", studentDTO.getId());
            throw new IllegalArgumentException("Student with the given ID already exists.");
        }

        addStudentInfo(studentDTO);

        if (studentDTO.getSubjects() == null || studentDTO.getSubjects().isEmpty()) {
            log.info("No subjects provided for student ID {}", studentDTO.getId());
        } else {
            addSubjects(studentDTO.getId(), studentDTO.getSubjects());
        }

        log.info("Student added successfully: ID={}, Name={}", studentDTO.getId(), studentDTO.getName());

        return buildStudentDetails(studentDTO.getId(), studentDTO.getName(), studentDTO.getSubjects());
    }

    public Map<String, Integer> addSubjects(int id, Map<String, Integer> subjects) {
        if (!isStudentExist(id)) {
            log.warn("Student with ID {} does not exist.", id);
            throw new IllegalArgumentException("Student with the given ID does not exist.");
        }

        if (subjects == null || subjects.isEmpty()) {
            log.warn("Subjects cannot be null or empty for student with ID {}", id);
            throw new IllegalArgumentException("Subjects cannot be null or empty.");
        }

        validateSubjects(subjects);

        log.info("Adding subjects for student ID: {}", id);
        studentSubjects.merge(id, subjects, (oldSubjects, newSubjects) -> {
            oldSubjects.putAll(newSubjects);
            return oldSubjects;
        });

        return studentSubjects.get(id);
    }

    public List<Map<String, Object>> getAllStudents() {
        return studentInfo.entrySet().stream()
                .map(entry -> buildStudentDetails(entry.getKey(), entry.getValue(), studentSubjects.get(entry.getKey())))
                .collect(Collectors.toList());
    }

    private boolean isStudentExist(int id) {
        return studentInfo.containsKey(id);
    }

    private void addStudentInfo(StudentsDto studentDTO) {
        studentInfo.put(studentDTO.getId(), studentDTO.getName());
    }

    private void validateStudentDto(StudentsDto studentDTO) {


    }

    private void validateSubjects(Map<String, Integer> subjects) {
        for (Map.Entry<String, Integer> entry : subjects.entrySet()) {
            if (entry.getValue() < 0  || entry.getValue() > 100) {
                throw new IllegalArgumentException("Subject marks should be between 0 to 100 for " + entry.getKey());
            }
        }
    }

    private Map<String, Object> buildStudentDetails(int id, String name, Map<String, Integer> subjects) {
        Map<String, Object> studentDetails = new HashMap<>();
        studentDetails.put("id", id);
        studentDetails.put("name", name);
        studentDetails.put("subjects", subjects == null || subjects.isEmpty() ? "N/A" : subjects);
        return studentDetails;
    }
}
