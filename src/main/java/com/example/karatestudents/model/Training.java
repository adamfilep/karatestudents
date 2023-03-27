package com.example.karatestudents.model;

import com.example.karatestudents.model.enums.Day;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Day day;

    private LocalTime startsAt;

    private LocalTime endsAt;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @JsonManagedReference
    private Trainer trainer;

    private String location;

    @ManyToMany
    @JoinTable(name = "training_student",
        joinColumns = @JoinColumn(name = "training_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonBackReference
    private List<Student> studentList;
}
