package com.epam.esm.model.entity;

import com.epam.esm.model.listener.AuditListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
@EntityListeners(AuditListener.class)
@NoArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
public abstract class CommonEntity<T> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @JsonIgnore
    @Column(name = "operation", nullable = false)
    private String operation;

    @JsonIgnore
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;
}
