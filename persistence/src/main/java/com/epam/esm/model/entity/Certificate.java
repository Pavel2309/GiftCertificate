package com.epam.esm.model.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Certificate implements Serializable {
    private Long id;
    @Size(max = 128, message = "a title can't exceed 128 characters")
    @NotBlank(message = "a title can't be blank")
    private String title;
    @Size(max = 256, message = "a description can't exceed 256 characters")
    private String description;
    @Positive(message = "a price can't be negative")
    @NotNull(message = "a price can't be null")
    private BigDecimal price;
    @Positive(message = "a duration can't be negative")
    @NotNull(message = "a duration can't be null")
    private Integer duration;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Set<Tag> tags;
}
