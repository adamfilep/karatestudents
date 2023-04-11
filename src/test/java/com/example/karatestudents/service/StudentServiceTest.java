package com.example.karatestudents.service;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void saveStudent_shouldSaveStudentTest() {
        StudentDto studentDto = new StudentDto(
                "John Smith",
                LocalDate.of(1998, 7, 5),
                LocalDate.of(2005, 11, 10),
                "WHITE_BELT",
                true
        );
        studentService.saveStudent(studentDto);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void getAllStudentsTest() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setDateOfBirth(LocalDate.of(2000, 1, 1));
        student1.setStartedKarate(LocalDate.of(2010, 1, 1));
        student1.setRank(Rank.BROWN_BELT);
        student1.setStudent(false);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Deer");
        student2.setDateOfBirth(LocalDate.of(1999, 2, 2));
        student2.setStartedKarate(LocalDate.of(2005, 1, 1));
        student2.setRank(Rank.BLUE_BELT);
        student2.setStudent(true);

        List<Student> expectedList = List.of(student1, student2);

        when(studentRepository.findAll()).thenReturn(expectedList);

        List<Student> actualList = studentService.getAllStudents();

        verify(studentRepository, times(1)).findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getStudentById_inputExistingId() {
        Long studentId = 1L;
        Student expectedStudent = new Student();
        expectedStudent.setId(studentId);
        expectedStudent.setName("John Doe");
        expectedStudent.setDateOfBirth(LocalDate.of(2001, 1, 1));
        expectedStudent.setStartedKarate(LocalDate.of(2009, 1, 1));
        expectedStudent.setRank(Rank.WHITE_BELT);
        expectedStudent.setStudent(true);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        Student actualStudent = studentService.getStudentById(studentId);

        verify(studentRepository, times(1)).findById(studentId);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void getStudentById_shouldThrowEntityNotFoundException_whenStudentDoesNotExistTest() {
        Long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(studentId));

        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void updateStudent_shouldUpdateStudentWhenExists() {
        Long id = 1L;
        StudentDto updatedStudentDto = new StudentDto(
                "John Smith",
                LocalDate.of(1998, 7, 5),
                LocalDate.of(2005, 11, 10),
                "WHITE_BELT",
                true
        );

        Student student = new Student();
        student.setId(id);
        student.setName("Jim John");
        student.setDateOfBirth(LocalDate.of(2001, 1, 1));
        student.setStartedKarate(LocalDate.of(2009, 1, 1));
        student.setRank(Rank.BROWN_BELT);
        student.setStudent(false);

        when(studentRepository.getReferenceById(id)).thenReturn(student);

        studentService.updateStudent(id, updatedStudentDto);

        verify(studentRepository, times(1)).save(student);

        assertEquals(updatedStudentDto.getName(), student.getName());
        assertEquals(updatedStudentDto.getDateOfBirth(), student.getDateOfBirth());
        assertEquals(updatedStudentDto.getStartedKarate(), student.getStartedKarate());
        assertEquals(Rank.WHITE_BELT, student.getRank());
        assertTrue(student.isStudent());
    }

    @Test
    void deleteStudentById_deleteStudentWhenFound() {
        Long id = 1L;
        Student student = new Student();
        student.setId(id);

        when(studentRepository.getReferenceById(id)).thenReturn(student);

        studentService.deleteStudentById(id);

        verify(studentRepository, times(1)).deleteById(id);
    }
}