package com.example.service;

import com.example.entity.Student;
import com.example.entity.Subject;
import com.example.dto.StudentsDto;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentsDto addStudent(StudentsDto studentDTO) {
        if (studentDTO == null || studentDTO.getSubjects() == null || studentDTO.getSubjects().isEmpty()) {
            throw new IllegalArgumentException("Student data or subjects cannot be null or empty");
        }

            Student student = new Student();
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setBirthDate(studentDTO.getBirthDate());

            Map<String, Integer> subjects = studentDTO.getSubjects();
            for (Map.Entry<String, Integer> entry : subjects.entrySet()) {
                Subject subject = new Subject();
                subject.setSubjectName(entry.getKey());
                subject.setMarks(entry.getValue());
                subject.setStudent(student);
                student.getSubjects().add(subject);
            }

            student = studentRepository.save(student);

            return convertToDTO(student);

    }

  
    public List<StudentsDto> getAllStudents() {
            System.out.println("Its running on cache");
            List<Student> students = studentRepository.findAll();
            if (students.isEmpty()) {
                throw new RuntimeException("No students found");
            }

            List<StudentsDto> studentDTOs = new ArrayList<>();
            for (Student student : students) {
                studentDTOs.add(convertToDTO(student));
            }
            return studentDTOs;

    }

    public Map<String, Integer> updateStudent(Long studentId, Map<String, Integer> subjectsMap) {
        if (studentId == null || subjectsMap == null || subjectsMap.isEmpty()) {
            throw new IllegalArgumentException("Student ID or subjects map cannot be null or empty");
        }

            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

            for (Map.Entry<String, Integer> entry : subjectsMap.entrySet()) {
                String subjectName = entry.getKey();
                int marks = entry.getValue();

                Optional<Subject> existingSubject = existingStudent.getSubjects().stream()
                        .filter(subject -> subject.getSubjectName().equals(subjectName))
                        .findFirst();

                if (existingSubject.isPresent()) {
                    existingSubject.get().setMarks(marks);
                } else {
                    Subject newSubject = new Subject();
                    newSubject.setSubjectName(subjectName);
                    newSubject.setMarks(marks);
                    newSubject.setStudent(existingStudent);
                    existingStudent.getSubjects().add(newSubject);
                }
            }

            studentRepository.save(existingStudent);

            Map<String, Integer> updatedSubjects = new LinkedHashMap<>();
            for (Subject subject : existingStudent.getSubjects()) {
                updatedSubjects.put(subject.getSubjectName(), subject.getMarks());
            }

            return updatedSubjects;

    }

    public StudentsDto convertToDTO(Student student) {
        StudentsDto dto = new StudentsDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setBirthDate(student.getBirthDate());

        Map<String, Integer> subjectMap = new LinkedHashMap<>();
        for (Subject subject : student.getSubjects()) {
            subjectMap.put(subject.getSubjectName(), subject.getMarks());
        }
        dto.setSubjects(subjectMap);

        return dto;
    }
}
