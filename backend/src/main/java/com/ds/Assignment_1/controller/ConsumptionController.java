package com.ds.Assignment_1.controller;

import com.ds.Assignment_1.service.ClientService;
import com.ds.Assignment_1.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Consumption")
@CrossOrigin
public class ConsumptionController {
    @Autowired
    private ConsumptionService consumptionService;

    @GetMapping("/getSensorConsumption/{sensorId}")
    public ResponseEntity getDevicesByUser(@PathVariable Long sensorId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumptionService.getConsumptionsBySensor(sensorId));
    }

}
