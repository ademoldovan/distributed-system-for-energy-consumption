package com.ds.Assignment_1.controller;

import com.ds.Assignment_1.dto.CreateSensorDto;
import com.ds.Assignment_1.dto.DeleteDto;
import com.ds.Assignment_1.dto.UpdateSensorDto;
import com.ds.Assignment_1.model.Sensor;
import com.ds.Assignment_1.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Sensor")
@CrossOrigin
public class SensorController {
    @Autowired
    private SensorService sensorService;

    @GetMapping("/getSensors")
    public ResponseEntity getSensors(){
        return ResponseEntity.status(HttpStatus.OK).body(
                sensorService.getAll());
    }
    @GetMapping("/getSensorById")
    public ResponseEntity getSensorById(Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(sensorService.getById(id));
    }
    @PostMapping("/CreateSensor")
    public void createSensor(@RequestBody CreateSensorDto sensor){
        Sensor s = new Sensor(sensor.getDescription(), sensor.getMaximum_value());
        sensorService.createSensor(s);
    }
    @PostMapping("/DeleteSensor")
    public void deleteSensor(@RequestBody DeleteDto sensor){
        sensorService.deleteById(sensor.getId());
    }
    @PostMapping("/UpdateSensor")
    public void updateSensor(@RequestBody UpdateSensorDto updateSensorDto){
        Sensor sensor = new Sensor(updateSensorDto.getId(), updateSensorDto.getDescription(), updateSensorDto.getMaximum_value());
        sensorService.createSensor(sensor);
    }
}
