package com.example.karatestudents.model.dto;

import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public class StudentDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @PastOrPresent(message = "Started karate cannot be in the future")
    private LocalDate startedKarate;

    @ValueOfEnum(enumClass = Rank.class, message = "Input is not a rank")
    private String rank;

    @NotNull(message = "isStudent cannot be null")
    private boolean isStudent;
}
