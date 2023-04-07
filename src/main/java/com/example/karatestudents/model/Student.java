package com.example.karatestudents.model;

import com.example.karatestudents.model.enums.Rank;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private LocalDate startedKarate;

    @Enumerated(EnumType.STRING)
    @Column(name = "std_rank")
    private Rank rank;

    private boolean isStudent;

    @ManyToMany
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    private List<Training> trainingList;
}
