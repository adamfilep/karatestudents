package com.example.karatestudents.controller;

import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.TrainingDto;
import com.example.karatestudents.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@Tag(name = "Operations on trainings")
public class TrainingController {

    private TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public void saveTraining(@Valid @RequestBody TrainingDto trainingDto) {
        trainingService.saveTraining(trainingDto);
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
    public void updateTraining(@PathVariable("id") Long id, @Valid @RequestBody TrainingDto updatedTrainingDto) {
        trainingService.updateTraining(id, updatedTrainingDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable("id") Long id) {
        trainingService.deleteTrainingById(id);
    }

}
