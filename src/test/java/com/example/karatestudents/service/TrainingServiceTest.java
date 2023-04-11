package com.example.karatestudents.service;

import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.TrainingDto;
import com.example.karatestudents.model.enums.Day;
import com.example.karatestudents.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    TrainingRepository trainingRepository;

    @InjectMocks
    TrainingService trainingService;

    @Test
    void saveTraining_shouldSaveTrainingTest() {
        TrainingDto trainingDto = new TrainingDto(
                "Random name",
                "MONDAY",
                LocalTime.of(10, 10),
                LocalTime.of(11, 10),
                "Random Location"
        );

        trainingService.saveTraining(trainingDto);
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void getAllTrainingsTest() {
        Training training1 = Training.builder()
                .id(1L)
                .name("Random name")
                .trainingDay(Day.WEDNESDAY)
                .startsAt(LocalTime.of(10, 0))
                .endsAt(LocalTime.of(11, 0))
                .location("Random Location")
                .build();

        Training training2 = Training.builder()
                .id(2L)
                .name("Random name")
                .trainingDay(Day.TUESDAY)
                .startsAt(LocalTime.of(10, 0))
                .endsAt(LocalTime.of(11, 0))
                .location("Random Location")
                .build();

        List<Training> expectedList = List.of(training1, training2);

        when(trainingRepository.findAll()).thenReturn(expectedList);

        List<Training> actualList = trainingService.getAllTrainings();

        verify(trainingRepository, times(1)).findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getTrainingById_inputExistingIdTest() {
        Long trainingId = 1L;

        Training expectedTraining = Training.builder()
                .id(trainingId)
                .name("Random name")
                .trainingDay(Day.TUESDAY)
                .startsAt(LocalTime.of(10, 0))
                .endsAt(LocalTime.of(11, 0))
                .location("Random Location")
                .build();

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(expectedTraining));

        Training actualTraining = trainingService.getTrainingById(trainingId);

        verify(trainingRepository, times(1)).findById(trainingId);

        assertEquals(expectedTraining, actualTraining);
    }

    @Test
    public void getTrainingById_shouldThrowEntityNotFoundException_whenTrainingDoesNotExistTest() {
        Long trainingId = 1L;

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainingService.getTrainingById(trainingId));

        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void updateTraining_ShouldUpdateTrainingWhenExistsTest() {
        Long trainingId = 1L;

        TrainingDto updatedTrainingDto = new TrainingDto(
                "Random name",
                "MONDAY",
                LocalTime.of(10, 10),
                LocalTime.of(11, 10),
                "Random Location"
        );

        Training training = Training.builder()
                .id(1L)
                .name("Already here")
                .trainingDay(Day.WEDNESDAY)
                .startsAt(LocalTime.of(10, 0))
                .endsAt(LocalTime.of(11, 0))
                .location("Random Location 1")
                .build();

        when(trainingRepository.getReferenceById(trainingId)).thenReturn(training);

        trainingService.updateTraining(trainingId, updatedTrainingDto);

        verify(trainingRepository, times(1)).save(training);

        assertEquals(updatedTrainingDto.getName(), training.getName());
        assertEquals(Day.valueOf(updatedTrainingDto.getTrainingDay()), training.getTrainingDay());
        assertEquals(updatedTrainingDto.getStartsAt(), training.getStartsAt());
        assertEquals(updatedTrainingDto.getEndsAt(), training.getEndsAt());
        assertEquals(updatedTrainingDto.getLocation(), training.getLocation());
    }

    @Test
    void deleteTrainingByIdTest() {
        Long trainingId = 1L;
        Training training = new Training();
        Trainer trainer = Mockito.mock(Trainer.class);
        training.setTrainer(trainer);

        when(trainingRepository.getReferenceById(trainingId)).thenReturn(training);

        trainingService.deleteTrainingById(trainingId);

        verify(trainingRepository, times(1)).deleteById(trainingId);
    }

    @Test
    void addTrainerToTrainingTest() {
        Long trainingId = 1L;
        Long trainerId = 2L;

        trainingService.addTrainerToTraining(trainingId, trainerId);

        verify(trainingRepository, times(1)).addTrainerToTraining(trainingId, trainerId);
    }

    @Test
    void addStudentToTrainingTest() {
        Long trainingId = 1L;
        Long studentId = 2L;

        trainingService.addStudentToTraining(trainingId, studentId);

        verify(trainingRepository, times(1)).addStudentToTraining(trainingId, studentId);
    }
}