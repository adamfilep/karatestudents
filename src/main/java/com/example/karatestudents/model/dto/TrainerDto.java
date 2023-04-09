package com.example.karatestudents.model.dto;

import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class TrainerDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @ValueOfEnum(enumClass = Rank.class, message = "Input is not a rank")
    private String rank;
}
