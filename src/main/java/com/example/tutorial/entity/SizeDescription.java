package com.example.tutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "size_description")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "attribute", length = 100)
    private String attribute;

    @Column(name = "value", length = 10)
    private String value;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private ProductSize size;
}
