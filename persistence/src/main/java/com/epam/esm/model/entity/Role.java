package com.epam.esm.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
public class Role extends CommonEntity<Long> {
    @Enumerated(EnumType.STRING)
    public UserRole title;

}
