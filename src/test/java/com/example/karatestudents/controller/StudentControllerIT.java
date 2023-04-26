package com.example.karatestudents.controller;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @SpyBean
    private StudentService studentService;

    @LocalServerPort
    private Integer port;

    private String studentUrl;

    @BeforeEach
    void setup() {
        studentUrl = "http://localhost:" + port + "/students";
    }

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

        // HTTP post request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDto> entity = new HttpEntity<>(studentDto, headers);

        // get a response
        ResponseEntity<Student> postResponse = restTemplate.postForEntity(studentUrl, entity, Student.class);

        // assert that the response status is CREATED
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        // verify that the saveStudent method of the StudentService was called with the correct arguments
        ArgumentCaptor<StudentDto> argumentCaptor = ArgumentCaptor.forClass(StudentDto.class);
        verify(studentService, times(1)).saveStudent(argumentCaptor.capture());

        StudentDto savedStudentDto = argumentCaptor.getValue();
        assertSameStudent(studentDto, savedStudentDto);
    }

    @Test
    public void testSaveStudentWithFaultyJson() {

        // create a new studentDto
        StudentDto studentDto = StudentDto.builder()
                .name("")
                .dateOfBirth(LocalDate.of(1980, 1,1))
                .startedKarate(LocalDate.of(2000,1,1))
                .rank("WHITE_BELT")
                .isStudent(true)
                .build();

        // HTTP post request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDto> entity = new HttpEntity<>(studentDto, headers);

        // get a response
        ResponseEntity<Error> response = restTemplate.postForEntity(studentUrl, entity, Error.class);

        // assert that the response status is BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAllStudents() {
        StudentDto student1 = StudentDto.builder()
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1998,7,5))
                .startedKarate(LocalDate.of(2006,11,1))
                .rank("BROWN_BELT")
                .isStudent(true)
                .build();

        StudentDto student2 = StudentDto.builder()
                .name("Jane Hunter")
                .dateOfBirth(LocalDate.of(2000,3,2))
                .startedKarate(LocalDate.of(2020,11,1))
                .rank("YELLOW_BELT")
                .isStudent(true)
                .build();

        postStudentDto(studentUrl, student1);
        postStudentDto(studentUrl, student2);

        final ResponseEntity<StudentDto[]> response = restTemplate.getForEntity(studentUrl, StudentDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final StudentDto[] testStudents = response.getBody();
        assert testStudents != null;

        assertEquals(2, testStudents.length);
        assertEquals("John Doe", testStudents[0].getName());
        assertEquals("Jane Hunter", testStudents[1].getName());
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

    private void postStudentDto(String url, StudentDto studentDto) {
        final HttpEntity<StudentDto> httpEntity = createHttpEntityWithMediaTypeJson(studentDto);
        final ResponseEntity<StudentDto> postResponse = restTemplate.postForEntity(url, httpEntity, StudentDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    }

    private HttpEntity<StudentDto> createHttpEntityWithMediaTypeJson(StudentDto studentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(studentDto, headers);
    }
}