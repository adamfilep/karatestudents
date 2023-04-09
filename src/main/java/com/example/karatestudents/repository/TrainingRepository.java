package com.example.karatestudents.repository;

import com.example.karatestudents.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
