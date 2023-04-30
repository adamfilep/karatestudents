package com.example.karatestudents.controller;

import com.example.karatestudents.model.Trainer;
import com.example.karatestudents.model.dto.TrainerDto;
import com.example.karatestudents.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @ApiResponse(responseCode = "200", description = "New trainer saved"),
            @ApiResponse(responseCode = "400", description = "Request JSON was not correct")
    })
    @PostMapping
    public ResponseEntity<Trainer> saveTrainer(@Valid @RequestBody TrainerDto trainerDto) {
        try {
            trainerService.saveTrainer(trainerDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainers found")
    })
    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @Operation(summary = "Get trainer with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer found"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(trainerService.getTrainerById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update trainer with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated"),
            @ApiResponse(responseCode = "400", description = "Request JSON or id was not correct")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable("id") Long id, @Valid @RequestBody TrainerDto updatedTrainerDto) {
        try {
            trainerService.updateTrainer(id, updatedTrainerDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete trainer with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Trainer to be deleted not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Trainer> deleteTrainer(@PathVariable("id") Long id) {
        try {
            trainerService.deleteTrainerById(id);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}