package com.example.karatestudents.integration;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.model.dto.TrainingDto;
import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.service.TrainingService;
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

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TrainingIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @SpyBean
    private TrainingService trainingService;

    @LocalServerPort
    private Integer port;

    private String trainingUrl;

    @BeforeEach
    void setup() {
        trainingUrl = "http://localhost:" + port + "/trainings";
    }

    @Test
    void saveTrainingTest() {

        TrainingDto trainingDto = TrainingDto.builder()
                .name("Test training")
                .trainingDay("MONDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainingDto> entity = new HttpEntity<>(trainingDto, headers);

        ResponseEntity<Training> postResponse = restTemplate.postForEntity(trainingUrl, entity, Training.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        ArgumentCaptor<TrainingDto> argumentCaptor = ArgumentCaptor.forClass(TrainingDto.class);
        verify(trainingService, times(1)).saveTraining(argumentCaptor.capture());

        TrainingDto savedTrainingDto = argumentCaptor.getValue();

        assertSameTraining(trainingDto, savedTrainingDto);
    }

    @Test
    void getAllTrainingsTest() {

        TrainingDto trainingDto1 = TrainingDto.builder()
                .name("Test training 1")
                .trainingDay("MONDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        TrainingDto trainingDto2 = TrainingDto.builder()
                .name("Test training 2")
                .trainingDay("WEDNESDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        postTrainingDto(trainingUrl, trainingDto1);
        postTrainingDto(trainingUrl, trainingDto2);

        final ResponseEntity<TrainingDto[]> response = restTemplate.getForEntity(trainingUrl, TrainingDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final TrainingDto[] testTrainings = response.getBody();
        assert testTrainings != null;

        assertEquals(2, testTrainings.length);
        assertEquals("Test training 1", testTrainings[0].getName());
        assertEquals("Test training 2", testTrainings[1].getName());
    }

    @Test
    void getTrainingByIdTest() {

        TrainingDto trainingDto = TrainingDto.builder()
                .name("Test training 1")
                .trainingDay("MONDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        postTrainingDto(trainingUrl, trainingDto);

        trainingUrl += "/1";

        final ResponseEntity<TrainingDto> response = restTemplate.getForEntity(trainingUrl, TrainingDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        TrainingDto actualTraining = response.getBody();
        assert actualTraining != null;

        assertEquals("Test training 1", actualTraining.getName());
        assertEquals("MONDAY", actualTraining.getTrainingDay());
    }

    @Test
    void updateTrainingTest() {

        TrainingDto oldTraining = TrainingDto.builder()
                .name("Test training 1")
                .trainingDay("MONDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        postTrainingDto(trainingUrl, oldTraining);

        TrainingDto updatedTraining = TrainingDto.builder()
                .name("Updated training")
                .trainingDay("MONDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainingDto> requestEntity = new HttpEntity<>(updatedTraining, headers);

        ResponseEntity<TrainingDto> response = restTemplate.exchange(trainingUrl + "/" + 1, HttpMethod.PUT, requestEntity, TrainingDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Training updatedTrainingInDb = trainingService.getTrainingById(1L);

        assertEquals("Updated training", updatedTrainingInDb.getName());
    }

    @Test
    void deleteTrainingTest() {

        TrainingDto trainingDto = TrainingDto.builder()
                .name("Test training 1")
                .trainingDay("MONDAY")
                .startsAt(LocalTime.of(17, 30))
                .endsAt(LocalTime.of(18, 30))
                .location("Central")
                .build();

        postTrainingDto(trainingUrl, trainingDto);

        assertEquals(1, trainingService.getAllTrainings().size());

        ResponseEntity<Void> response = restTemplate.exchange(trainingUrl + "/" + 1, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertEquals(0, trainingService.getAllTrainings().size());
    }

    private void assertSameTraining(TrainingDto expected, TrainingDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getTrainingDay(), actual.getTrainingDay());
        assertEquals(expected.getStartsAt(), actual.getStartsAt());
        assertEquals(expected.getEndsAt(), actual.getEndsAt());
        assertEquals(expected.getLocation(), actual.getLocation());
    }

    private void postTrainingDto(String url, TrainingDto trainingDto) {
        final HttpEntity<TrainingDto> httpEntity = createHttpEntityWithMediaTypeJson(trainingDto);
        final ResponseEntity<TrainingDto> postResponse = restTemplate.postForEntity(url, httpEntity, TrainingDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    }

    private HttpEntity<TrainingDto> createHttpEntityWithMediaTypeJson(TrainingDto trainingDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(trainingDto, headers);
    }
}
