package com.example.karatestudents.controller;

import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.dto.TrainerDto;
import com.example.karatestudents.service.TrainerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@Tag(name = "Operations on trainers")
public class TrainerController {

    private TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public void saveTrainer(@Valid @RequestBody TrainerDto trainerDto) {
        trainerService.saveTrainer(trainerDto);
    }

    @GetMapping
    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @GetMapping("/{id}")
    public Trainer getTrainerById(@PathVariable("id") Long id) {
        return trainerService.getTrainerById(id);
    }

    @PutMapping("/{id}")
    public void updateTrainer(@PathVariable("id") Long id, @Valid @RequestBody TrainerDto updatedTrainerDto) {
        trainerService.updateTrainer(id, updatedTrainerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainer(@PathVariable("id") Long id) {
        trainerService.deleteTrainerById(id);
    }
}