package com.tpe.service;

import com.tpe.CarDTO;
import com.tpe.controller.request.CarRequest;
import com.tpe.domain.Car;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    public List<CarDTO> getAllCars() {
        List<Car> carList = this.carRepository.findAll();
        return carList.stream().map(car -> this.modelMapper.map(car,CarDTO.class)).collect(Collectors.toList());
    }

    public void saveCar(CarRequest carRequest) {
        Car car = this.modelMapper.map(carRequest, Car.class);
        this.carRepository.save(car);
    }

    public CarDTO getCar(Long id) {
        Car car = this.carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        CarDTO carDTO = this.modelMapper.map(car,CarDTO.class);
        return carDTO;
    }
}
