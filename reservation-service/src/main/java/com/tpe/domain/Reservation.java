package com.tpe.domain;

import com.tpe.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "t_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Long carId;
    @Column(nullable = false)
    private LocalDateTime pickUpTime;
    @Column(nullable = false)
    private LocalDateTime dropOffTime;
    @Column(length = 150, nullable = false)
    private String pickUpLocation;
    @Column(length = 150, nullable = false)
    private String dropOffLocation;
    @Column(nullable = false)
    private ReservationStatus status;
    @Column(nullable = false)
    private Double totalPrice;
    public Long getTotalHours(LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
        return ChronoUnit.HOURS.between(pickUpTime,dropOffTime);
    }
}
