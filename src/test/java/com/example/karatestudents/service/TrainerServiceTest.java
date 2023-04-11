package com.example.karatestudents.service;

import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.dto.TrainerDto;
import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void saveTrainer_shouldSaveTrainerTest() {
        TrainerDto trainerDto = new TrainerDto(
                "John Johnson",
                "SECOND_DAN"
        );
        trainerService.saveTrainer(trainerDto);
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void getAllTrainersTest() {
        Trainer trainer1 = Trainer.builder()
                .id(1L)
                .name("John Johnshon")
                .rank(Rank.SECOND_DAN)
                .build();

        Trainer trainer2 = Trainer.builder()
                .id(2L)
                .name("Jane Doe")
                .rank(Rank.THIRD_DAN)
                .build();

        List<Trainer> expectedList = List.of(trainer1, trainer2);

        when(trainerRepository.findAll()).thenReturn(expectedList);

        List<Trainer> actualList = trainerService.getAllTrainers();

        verify(trainerRepository, times(1)).findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getTrainerById_inputExistingIdTest() {
        Long trainerId = 1L;

        Trainer expectedTrainer = Trainer.builder()
                .id(trainerId)
                .name("John Doe")
                .rank(Rank.THIRD_DAN)
                .build();

        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(expectedTrainer));

        Trainer actualTrainer = trainerService.getTrainerById(trainerId);

        verify(trainerRepository, times(1)).findById(trainerId);

        assertEquals(expectedTrainer, actualTrainer);
    }

    @Test
    void getTrainerById_shouldThrowEntityNotFoundException_whenTrainerDoesNotExistTest() {
        Long trainerId = 1L;

        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.getTrainerById(trainerId));

        verify(trainerRepository, times(1)).findById(trainerId);

    }

    @Test
    void updateTrainer_shouldUpdateTrainerWhenExistsTest() {

        Long trainerId = 1L;

        TrainerDto updatedTrainerDto = new TrainerDto(
                "John Joe",
                "SECOND_DAN"
        );

        Trainer trainer = Trainer.builder()
                .name("Erika Jordan")
                .rank(Rank.BROWN_BELT)
                .build();

        when(trainerRepository.getReferenceById(trainerId)).thenReturn(trainer);

        trainerService.updateTrainer(trainerId, updatedTrainerDto);

        verify(trainerRepository, times(1)).save(trainer);

        assertEquals(updatedTrainerDto.getName(), trainer.getName());
        assertEquals(Rank.valueOf(updatedTrainerDto.getRank()), trainer.getRank());
    }

    @Test
    void deleteTrainerById_deleteTrainerWhenFoundTest() {
        Long id = 1L;
        Trainer trainer = new Trainer();

        when(trainerRepository.getReferenceById(id)).thenReturn(trainer);

        trainerService.deleteTrainerById(id);

        verify(trainerRepository, times(1)).deleteById(id);
    }
}