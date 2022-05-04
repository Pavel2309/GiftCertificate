package com.epam.esm.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private List<Long> certificatesId;
    private BigDecimal price;
    private Timestamp purchaseDate;
}
