package com.example.karatestudents.repository;

import com.example.karatestudents.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE training SET trainer_id=?2 WHERE id=?1", nativeQuery = true)
    void addTrainerToTraining(Long trainingId, Long trainerId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO training_student(training_id, student_id) VALUES (?1, ?2)", nativeQuery = true)
    void addStudentToTraining(Long trainingId, Long studentId);
}
