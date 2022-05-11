package com.epam.esm.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tags")
@NoArgsConstructor
public class Tag extends CommonEntity<Long> {

    @Size(max = 32, message = "a tag's length can't exceed 32 characters")
    @NotBlank(message = "a tag's title can't be blank")
    private String title;
}
