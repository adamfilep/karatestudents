package com.example.karatestudents.model.dto;

import com.example.karatestudents.model.enums.Day;
import com.example.karatestudents.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
@Data
public class TrainingDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @ValueOfEnum(enumClass = Day.class, message = "Input is not a day")
    private String trainingDay;

    @Pattern(regexp = "^(?:[01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Incorrect time format, the correct format is hh:MM:ss")
    private LocalTime startsAt;

    @Pattern(regexp = "^(?:[01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Incorrect time format, the correct format is hh:MM:ss")
    private LocalTime endsAt;

    @NotBlank(message = "Location cannot be blank")
    private String location;
}
