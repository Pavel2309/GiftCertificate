package com.epam.esm.model.entity;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Certificate implements Serializable {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<Tag> tags;
}
