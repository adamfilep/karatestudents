package com.example.karatestudents.repository;

import com.example.karatestudents.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
