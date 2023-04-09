package com.example.karatestudents.model.dto;

import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrainerDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @ValueOfEnum(enumClass = Rank.class, message = "Input is not a rank")
    private String rank;
}
