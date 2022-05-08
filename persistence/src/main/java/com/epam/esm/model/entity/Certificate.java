package com.epam.esm.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "certificates")
@NoArgsConstructor
public class Certificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToMany
    @JoinTable(name = "certificates_has_tags",
            joinColumns = @JoinColumn(name = "certificates_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Set<@Valid Tag> tags = new HashSet<>();
}
