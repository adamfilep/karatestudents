package com.example.karatestudents.model;

import com.example.karatestudents.model.enums.Rank;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "trn_rank")
    private Rank rank;

    @OneToMany(mappedBy = "trainer")
    @JsonBackReference
    private List<Training> trainingList;
}
