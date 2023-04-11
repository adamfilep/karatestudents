package com.example.karatestudents.controller;

import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.TrainingDto;
import com.example.karatestudents.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Save new training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New training saved")
    })
    @PostMapping
    public void saveTraining(@Valid @RequestBody TrainingDto trainingDto) {
        trainingService.saveTraining(trainingDto);
    }

    @Operation(summary = "Get all trainings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainings found")
    })
    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @Operation(summary = "Get training with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training found")
    })
    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable("id") Long id) {
        return trainingService.getTrainingById(id);
    }

    @Operation(summary = "Update training with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training updated")
    })
    @PutMapping("/{id}")
    public void updateTraining(@PathVariable("id") Long id, @Valid @RequestBody TrainingDto updatedTrainingDto) {
        trainingService.updateTraining(id, updatedTrainingDto);
    }

    @Operation(summary = "Delete training with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training deleted")
    })
    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable("id") Long id) {
        trainingService.deleteTrainingById(id);
    }

    @Operation(summary = "Add trainer to training by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer added to training")
    })
    @PostMapping("/addtrainer")
    public void addTrainerToTraining(@RequestParam("training_id") Long trainingId, @RequestParam("trainer_id") Long trainerId) {
        trainingService.addTrainerToTraining(trainingId, trainerId);
    }

    @Operation(summary = "DAdd student to training by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student added to training")
    })
    @PostMapping("/addstudent")
    public void addStudentToTraining(@RequestParam("training_id") Long trainingId, @RequestParam("student_id") Long studentId) {
        trainingService.addStudentToTraining(trainingId, studentId);
    }

}
