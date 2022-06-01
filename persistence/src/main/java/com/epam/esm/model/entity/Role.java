package com.epam.esm.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role extends CommonEntity<Long> {
    @NotNull
    public String title;
}
