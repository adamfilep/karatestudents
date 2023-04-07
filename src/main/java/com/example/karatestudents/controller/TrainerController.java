package com.example.karatestudents.controller;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.service.StudentService;
import com.example.karatestudents.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public void saveTrainer(Trainer student) {
        trainerService.saveTrainer(student);
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
    public void updateTrainer(@PathVariable("id") Long id, @RequestBody Trainer updatedTrainer) {
        trainerService.updateTrainer(id, updatedTrainer);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainer(@PathVariable("id") Long id) {
        trainerService.deleteTrainerById(id);
    }
}