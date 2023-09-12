package com.tpe.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,nullable = false)
    private String brand;
    @Column(length = 30,nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer doors;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Double pricePerHour;
}
