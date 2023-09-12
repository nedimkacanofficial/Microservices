package com.tpe.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.tpe.CarDTO;
import com.tpe.ReservationDTO;
import com.tpe.controller.request.ReservationRequest;
import com.tpe.domain.Reservation;
import com.tpe.enums.ReservationStatus;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    public void saveReservation(ReservationRequest reservationRequest,Long carId) {
        InstanceInfo instanceInfo = this.eurekaClient.getApplication("car-service").getInstances().get(0);
        String baseUrl = instanceInfo.getHomePageUrl();
        String path = "/car/";
        String servicePath = baseUrl + path + carId.toString();
        ResponseEntity<CarDTO> carDTOResponseEntity = this.restTemplate.getForEntity(servicePath, CarDTO.class);
        if (!(carDTOResponseEntity.getStatusCode()== HttpStatus.OK)) {
            throw new ResourceNotFoundException("Car not found with id: " + carId);
        }
        Reservation reservation = new Reservation();
        reservation.setCarId(carDTOResponseEntity.getBody().getId());
        reservation.setPickUpTime(reservationRequest.getPickUpTime());
        reservation.setDropOffTime(reservationRequest.getDropOffTime());
        reservation.setPickUpLocation(reservationRequest.getPickUpLocation());
        reservation.setDropOffLocation(reservationRequest.getDropOffLocation());
        reservation.setTotalPrice(totalPrice(reservationRequest.getPickUpTime(),reservationRequest.getDropOffTime(),carDTOResponseEntity.getBody()));
        reservation.setStatus(ReservationStatus.CREATED);
        this.reservationRepository.save(reservation);
    }
    private Double totalPrice(LocalDateTime pickUpTime,LocalDateTime dropOffTime,CarDTO carDTO) {
        Long hours= (new Reservation()).getTotalHours(pickUpTime,dropOffTime);
        return carDTO.getPricePerHour()*hours;
    }

    public List<ReservationDTO> getAllReservation() {
        List<Reservation> reservationList = this.reservationRepository.findAll();
        return reservationList.stream().map(reservation -> this.modelMapper.map(reservation,ReservationDTO.class)).collect(Collectors.toList());
    }
}
