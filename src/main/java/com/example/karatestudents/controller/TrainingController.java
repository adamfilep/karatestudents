package com.example.karatestudents.controller;

import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.TrainingDto;
import com.example.karatestudents.service.TrainingService;
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
@RequestMapping("/trainings")
@Tag(name = "Operations on trainings")
public class TrainingController {

    private TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(summary = "Save new training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New training saved"),
            @ApiResponse(responseCode = "400", description = "Request JSON was not correct")
    })
    @PostMapping
    public ResponseEntity<Training> saveTraining(@Valid @RequestBody TrainingDto trainingDto) {
        try {
            trainingService.saveTraining(trainingDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all trainings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainings found")
    })
    @GetMapping
    public ResponseEntity<List<Training>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTrainings());
    }

    @Operation(summary = "Get training with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(trainingService.getTrainingById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update training with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training updated"),
            @ApiResponse(responseCode = "400", description = "Request JSON or id was not correct")

    })
    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable("id") Long id, @Valid @RequestBody TrainingDto updatedTrainingDto) {
        try {
            trainingService.updateTraining(id, updatedTrainingDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete training with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Training deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Training to be deleted not found")

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Training> deleteTraining(@PathVariable("id") Long id) {
        try {
            trainingService.deleteTrainingById(id);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add trainer to training by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer added to training"),
            @ApiResponse(responseCode = "404", description = "trainingId or trainerId not found")
    })
    @PostMapping("/addtrainer")
    public ResponseEntity<Training> addTrainerToTraining(@RequestParam("training_id") Long trainingId, @RequestParam("trainer_id") Long trainerId) {
        try {
            trainingService.addTrainerToTraining(trainingId, trainerId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add student to training by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student added to training"),
            @ApiResponse(responseCode = "404", description = "trainingId or studentId not found")
    })
    @PostMapping("/addstudent")
    public ResponseEntity<Training> addStudentToTraining(@RequestParam("training_id") Long trainingId, @RequestParam("student_id") Long studentId) {
        try {
            trainingService.addStudentToTraining(trainingId, studentId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
