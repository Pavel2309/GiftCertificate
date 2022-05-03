package com.epam.esm.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Order {
    private Long id;
    private User user;
    private List<Certificate> certificates;
    private BigDecimal price;
    private Timestamp purchaseDate;
}
