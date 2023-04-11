package com.example.karatestudents.controller;

import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.dto.TrainerDto;
import com.example.karatestudents.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Save new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New trainer saved")
    })
    @PostMapping
    public void saveTrainer(@Valid @RequestBody TrainerDto trainerDto) {
        trainerService.saveTrainer(trainerDto);
    }

    @Operation(summary = "Get all trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainers found")
    })
    @GetMapping
    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @Operation(summary = "Get trainer with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer found")
    })
    @GetMapping("/{id}")
    public Trainer getTrainerById(@PathVariable("id") Long id) {
        return trainerService.getTrainerById(id);
    }

    @Operation(summary = "Update trainer with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated")
    })
    @PutMapping("/{id}")
    public void updateTrainer(@PathVariable("id") Long id, @Valid @RequestBody TrainerDto updatedTrainerDto) {
        trainerService.updateTrainer(id, updatedTrainerDto);
    }

    @Operation(summary = "Delete trainer with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer deleted")
    })
    @DeleteMapping("/{id}")
    public void deleteTrainer(@PathVariable("id") Long id) {
        trainerService.deleteTrainerById(id);
    }
}