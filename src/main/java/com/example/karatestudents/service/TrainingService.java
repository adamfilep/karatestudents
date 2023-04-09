package com.example.karatestudents.service;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.TrainingDto;
import com.example.karatestudents.model.enums.Day;
import com.example.karatestudents.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    private TrainingRepository trainingRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public void saveTraining(TrainingDto trainingDto) {
        Training training = Training.builder()
                .name(trainingDto.getName())
                .trainingDay(Day.valueOf(trainingDto.getTrainingDay()))
                .startsAt(trainingDto.getStartsAt())
                .endsAt(trainingDto.getEndsAt())
                .location(trainingDto.getLocation())
                .build();
        trainingRepository.save(training);
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateTraining(Long id, TrainingDto updatedTrainingDto) {
        Training training = trainingRepository.getReferenceById(id);
        training.setName(updatedTrainingDto.getName());
        training.setTrainingDay(Day.valueOf(updatedTrainingDto.getTrainingDay()));
        training.setStartsAt(updatedTrainingDto.getStartsAt());
        training.setEndsAt(updatedTrainingDto.getEndsAt());
        training.setLocation(updatedTrainingDto.getLocation());
        trainingRepository.save(training);
    }

    @Transactional
    public void deleteTrainingById(Long id) {
        Training training = trainingRepository.getReferenceById(id);
        List<Student> studentList = training.getStudentList();
        Trainer trainer = training.getTrainer();
        if (studentList != null) {
            for (Student student : studentList) {
                student.getTrainingList().remove(training);
            }
        }
        trainer.getTrainingList().remove(training);
        trainingRepository.deleteById(id);
    }
}
