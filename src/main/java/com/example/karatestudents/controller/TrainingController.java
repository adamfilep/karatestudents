package com.example.karatestudents.controller;

import com.example.karatestudents.model.Training;
import com.example.karatestudents.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public void saveTraining(Training training) {
        trainingService.saveTraining(training);
    }

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable("id") Long id) {
        return trainingService.getTrainingById(id);
    }

    @PutMapping("/{id}")
    public void updateTraining(@PathVariable("id") Long id, @RequestBody Training updatedTraining) {
        trainingService.updateTraining(id, updatedTraining);
    }

    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable("id") Long id) {
        trainingService.deleteTrainingById(id);
    }

}
