package com.example.karatestudents.controller;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    final String STUDENT_URL = "/students";

    @Test
    void saveStudentTest() {

        // create a new studentDto
        StudentDto studentDto = StudentDto.builder()
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1980, 1,1))
                .startedKarate(LocalDate.of(2000,1,1))
                .rank("WHITE_BELT")
                .isStudent(true)
                .build();

        String json = null;

        // serialize the studentDto to JSON
        try {
            json = objectMapper.writeValueAsString(studentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // make HTTP POST request to save the student
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<Student> response = restTemplate.postForEntity(STUDENT_URL, entity, Student.class);

        // assert that the response status is CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // verify that the saveStudent method of the StudentService was called with the correct arguments
        ArgumentCaptor<StudentDto> argumentCaptor = ArgumentCaptor.forClass(StudentDto.class);
        verify(studentService, times(1)).saveStudent(argumentCaptor.capture());

        StudentDto savedStudentDto = argumentCaptor.getValue();
        assertSameStudent(studentDto, savedStudentDto);
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void getStudentById() {
    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteStudent() {
    }

    private void assertSameStudent(StudentDto expected, StudentDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth());
        assertEquals(expected.getStartedKarate(), actual.getStartedKarate());
        assertEquals(expected.getRank(), actual.getRank());
        assertEquals(expected.isStudent(), actual.isStudent());
    }
}