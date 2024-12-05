package com.example.service;

import com.example.dto.StudentsDto;
import com.example.entity.Student;
import com.example.entity.Subject;
import com.example.repository.StudentRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addStudent_ShouldAddStudentAndReturnDto() {

        //Arrange :
        StudentsDto inputDto = new StudentsDto();
        inputDto.setFirstName("John");
        inputDto.setLastName("Wick");
        inputDto.setBirthDate(LocalDate.of(2000, 1, 1));

        Map<String, Integer> subjects = new HashMap<>();
        subjects.put("Math", 90);
        subjects.put("Science", 80);
        inputDto.setSubjects(subjects);

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Wick");
        student.setBirthDate(LocalDate.of(2000, 1, 1));
        student.setSubjects(new ArrayList<>());

        //Act :
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentsDto resultDto = studentService.addStudent(inputDto);

        //Assert :
        assertNotNull(resultDto);
        assertEquals("John", resultDto.getFirstName());
        assertEquals("Wick", resultDto.getLastName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void getAllStudent_ShouldGetAndReturnStudentDtoList() {
        // Arrange:
        StudentsDto inputDto = new StudentsDto();
        inputDto.setFirstName("John");
        inputDto.setLastName("Wick");
        inputDto.setBirthDate(LocalDate.of(2000, 1, 1));

        Map<String, Integer> subjects = new HashMap<>();
        subjects.put("Math", 90);
        subjects.put("Science", 80);
        inputDto.setSubjects(subjects);

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Wick");
        student.setBirthDate(LocalDate.of(2000, 1, 1));
        student.setSubjects(new ArrayList<>());

        //Act:
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        List<StudentsDto> result = studentService.getAllStudents();

        //Assert:
        Assertions.assertEquals(1, result.size());
        StudentsDto firstResult = result.get(0);
        Assertions.assertEquals("Wick", firstResult.getLastName());
        Assertions.assertEquals("John", firstResult.getFirstName());
    }


    @Test
    public void updateStudent_ShouldUpdateSubjectsAndReturnUpdatedMap() {

        Long studentId = 1L;
        Map<String, Integer> updatedSubjectsMap = new HashMap<>();
        updatedSubjectsMap.put("Math", 95);
        updatedSubjectsMap.put("English", 85);

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("John");
        student.setLastName("Wick");
        student.setSubjects(new ArrayList<>());

        Subject subject = new Subject();
        subject.setSubjectName("Math");
        subject.setMarks(90);
        subject.setStudent(student);
        student.getSubjects().add(subject);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Map<String, Integer> result = studentService.updateStudent(studentId, updatedSubjectsMap);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(95, result.get("Math"));
        assertEquals(85, result.get("English"));
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(student);
    }


    @Test
    public void getAllStudent_ShouldthrowExceptionWhenStudentDataNotFound(){
        Map<String, Integer> SubjectsMap = new HashMap<>();
        SubjectsMap.put("Math", 85);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                studentService.getAllStudents());

        assertEquals("No students found", exception.getMessage());
        verify(studentRepository, times(1)).findAll();

    }

    @Test
    public void updateStudent_ShouldThrowExceptionIfStudentNotFound() {
        //Arrange:
        Long studentId = 99L;
        Map<String, Integer> updatedSubjectsMap = new HashMap<>();
        updatedSubjectsMap.put("Math", 85);
        
        //Act:
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                studentService.updateStudent(studentId, updatedSubjectsMap));

        //Assert:        
        assertEquals("Student not found with id: " + studentId, exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);

    }

    @Test
    public void updateStudent_ShouldThrowExceptionWhenStudentIdAndSubjectDataIsNull() {
        //Act:
        Long studentId = null;
        Map<String, Integer> updatedSubjectsMap = new HashMap<>();
        updatedSubjectsMap.put(null,null);
        updatedSubjectsMap.put("maths", null);

        //Arrange:
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        studentService.updateStudent(studentId, updatedSubjectsMap));
        
        //Assert:
        assertEquals("Student ID or subjects map cannot be null or empty", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void addStudent_ShouldThrowExceptionWhenSubjectsAreNull() {
      
        //Arrange:
        StudentsDto inputDto = new StudentsDto();
        inputDto.setFirstName("John");
        inputDto.setLastName("Wick");
        inputDto.setBirthDate(LocalDate.of(2000, 1, 1));
        inputDto.setSubjects(null);

        //Act:
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                studentService.addStudent(inputDto));

        //Assert:        
        assertEquals("Student data or subjects cannot be null or empty", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void addStudent_ShouldThrowExceptionIfStudentDataIsInvalid() {
      StudentsDto inputDto = new StudentsDto();

      IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
              studentService.addStudent(inputDto));

      assertEquals("Student data or subjects cannot be null or empty", exception.getMessage());
      verify(studentRepository, never()).save(any(Student.class));
      
  }

    @Test
    public void convertToDTO_ShouldMapSubjectsCorrectly() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Wick");
        student.setBirthDate(LocalDate.of(2000, 1, 1));

        Subject maths = new Subject();
        maths.setSubjectName("Maths");
        maths.setMarks(90);
        maths.setStudent(student);

        Subject science = new Subject();
        science.setSubjectName("Science");
        science.setMarks(80);
        science.setStudent(student);

        //Act
        List<Subject> subjects = Arrays.asList(maths, science);
        student.setSubjects(subjects);


        StudentsDto result = studentService.convertToDTO(student);

        //Assert
        assertEquals(2, result.getSubjects().size());
        assertEquals(90, result.getSubjects().get("Maths"));
        assertEquals(80, result.getSubjects().get("Science"));
    }

}

