package com.tpe.controller;

import com.netflix.discovery.EurekaClient;
import com.tpe.ReservationDTO;
import com.tpe.controller.request.ReservationRequest;
import com.tpe.enums.ReservationStatus;
import com.tpe.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping(path = "/{carId}")
    public ResponseEntity<Map<String,String>> saveReservation(@RequestBody ReservationRequest reservationRequest, @PathVariable Long carId) {
        this.reservationService.saveReservation(reservationRequest,carId);
        Map<String,String> map = new HashMap<>();
        map.put("message","The reservation has been successfully registered.");
        map.put("success","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservation() {
        List<ReservationDTO> reservationDTOList = this.reservationService.getAllReservation();
        return new ResponseEntity<>(reservationDTOList,HttpStatus.OK);
    }
}
