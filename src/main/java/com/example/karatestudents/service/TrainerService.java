package com.example.karatestudents.service;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.Training;
import com.example.karatestudents.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateTrainer(Long id, Trainer updatedTrainer) {
        Trainer trainer = trainerRepository.getReferenceById(id);
        trainer.setName(updatedTrainer.getName());
        trainer.setRank(updatedTrainer.getRank());
        trainer.setTrainingList(updatedTrainer.getTrainingList());
        trainerRepository.save(trainer);
    }

    @Transactional
    public void deleteTrainerById(Long id) {
        Trainer trainer = trainerRepository.getReferenceById(id);
        List<Training> trainingList = trainer.getTrainingList();
        if (trainingList != null) {
            for (Training training : trainingList) {
                training.setTrainer(null);
            }
        }
        trainerRepository.deleteById(id);
    }
}
