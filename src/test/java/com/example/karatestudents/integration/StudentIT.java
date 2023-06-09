package com.example.karatestudents.integration;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.model.enums.Rank;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentIT {

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

        StudentDto studentDto = StudentDto.builder()
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1980, 1,1))
                .startedKarate(LocalDate.of(2000,1,1))
                .rank("WHITE_BELT")
                .isStudent(true)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDto> entity = new HttpEntity<>(studentDto, headers);

        ResponseEntity<Student> postResponse = restTemplate.postForEntity(studentUrl, entity, Student.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        ArgumentCaptor<StudentDto> argumentCaptor = ArgumentCaptor.forClass(StudentDto.class);
        verify(studentService, times(1)).saveStudent(argumentCaptor.capture());

        StudentDto savedStudentDto = argumentCaptor.getValue();
        assertSameStudent(studentDto, savedStudentDto);
    }

    @Test
    public void testSaveStudentWithFaultyJsonTest() {

        StudentDto studentDto = StudentDto.builder()
                .name("")
                .dateOfBirth(LocalDate.of(1980, 1,1))
                .startedKarate(LocalDate.of(2000,1,1))
                .rank("WHITE_BELT")
                .isStudent(true)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDto> entity = new HttpEntity<>(studentDto, headers);

        ResponseEntity<Error> response = restTemplate.postForEntity(studentUrl, entity, Error.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAllStudentsTest() {
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
    void getStudentByIdTest() {

        StudentDto student = StudentDto.builder()
                .name("Jane Hunter")
                .dateOfBirth(LocalDate.of(2000,3,2))
                .startedKarate(LocalDate.of(2020,11,1))
                .rank("YELLOW_BELT")
                .isStudent(true)
                .build();

        postStudentDto(studentUrl, student);

        studentUrl += "/1";

        final ResponseEntity<StudentDto> response = restTemplate.getForEntity(studentUrl, StudentDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        StudentDto actualStudent = response.getBody();
        assert actualStudent != null;

        assertEquals("Jane Hunter", actualStudent.getName());
        assertEquals("YELLOW_BELT", actualStudent.getRank());
    }

    @Test
    void updateStudentTest() {
        StudentDto oldStudent = StudentDto.builder()
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1998,7,5))
                .startedKarate(LocalDate.of(2006,11,1))
                .rank("BROWN_BELT")
                .isStudent(true)
                .build();

        postStudentDto(studentUrl, oldStudent);

        StudentDto updatedStudent = StudentDto.builder()
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1998,7,5))
                .startedKarate(LocalDate.of(2006,11,1))
                .rank("FIRST_DAN")
                .isStudent(false)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDto> requestEntity = new HttpEntity<>(updatedStudent, headers);

        ResponseEntity<StudentDto> response = restTemplate.exchange(studentUrl + "/" + 1, HttpMethod.PUT, requestEntity, StudentDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Student updatedStudentInDb = studentService.getStudentById(1L);

        assertEquals(Rank.valueOf("FIRST_DAN"), updatedStudentInDb.getRank());
        assertFalse(updatedStudentInDb.isStudent());
    }

    @Test
    void deleteStudentTest() {
        StudentDto studentDto = StudentDto.builder()
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1998,7,5))
                .startedKarate(LocalDate.of(2006,11,1))
                .rank("BROWN_BELT")
                .isStudent(true)
                .build();

        studentService.saveStudent(studentDto);

        assertEquals(1, studentService.getAllStudents().size());

        ResponseEntity<Void> response = restTemplate.exchange(studentUrl + "/" + 1, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertEquals(0, studentService.getAllStudents().size());
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