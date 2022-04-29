package com.epam.esm.model.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Tag {
    private Long id;
    @Size(max = 32, message = "a tag's length can't exceed 32 characters")
    @NotBlank(message = "a tag's title can't be blank")
    private String title;
}
