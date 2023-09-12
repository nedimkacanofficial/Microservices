package com.tpe.controller;

import com.tpe.CarDTO;
import com.tpe.controller.request.CarRequest;
import com.tpe.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> carDTOList = this.carService.getAllCars();
        return new ResponseEntity<>(carDTOList,HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<CarDTO> getCar(@PathVariable Long id) {
        CarDTO carDTO = this.carService.getCar(id);
        return new ResponseEntity<>(carDTO,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Map<String,String>> saveCar(@RequestBody CarRequest carRequest) {
        this.carService.saveCar(carRequest);
        Map<String,String> map = new HashMap<>();
        map.put("message","The car has been successfully registered.");
        map.put("success","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
