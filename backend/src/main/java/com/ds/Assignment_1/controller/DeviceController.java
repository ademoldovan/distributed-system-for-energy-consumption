package com.ds.Assignment_1.controller;

import com.ds.Assignment_1.dto.*;
import com.ds.Assignment_1.model.Client;
import com.ds.Assignment_1.model.Consumption;
import com.ds.Assignment_1.model.Device;
import com.ds.Assignment_1.model.Sensor;
import com.ds.Assignment_1.service.ClientService;
import com.ds.Assignment_1.service.ConsumptionService;
import com.ds.Assignment_1.service.SensorService;
import com.ds.Assignment_1.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Device")
@CrossOrigin
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ConsumptionService consumptionService;

    @GetMapping("/getDevices")
    public ResponseEntity getDevices(){
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.getAll());
    }
    @GetMapping("/getDeviceById")
    public ResponseEntity getDeviceById(Long id){
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.getById(id));
    }
    @PostMapping("/CreateDevice")
    public void createDevice(@RequestBody CreateDeviceDto device){
        Sensor sensor = sensorService.getById(device.getSensor());
        Client client = clientService.getById(device.getClient());
        Device d = new Device(device.getAddress(), device.getDescription(), device.getMaximum_energy(), device.getAverage(),sensor, client);
        deviceService.createDevice(d);
    }
    @PostMapping("/DeleteDevice")
    public void deleteDevice(@RequestBody DeleteDto deleteDto){
        deviceService.deleteById(deleteDto.getId());
    }
    @PostMapping("/UpdateDevice")
    public void updateDevice(@RequestBody UpdateDeviceDto updateDeviceDto){
        Sensor sensor;
        Client client;
        Device device = deviceService.getById(updateDeviceDto.getId());
        if(updateDeviceDto.getSensor() != null){
            sensor = sensorService.getById(updateDeviceDto.getSensor());}
        else{
            sensor = device.getSensor();}
        if(updateDeviceDto.getClient() != null){
            client = clientService.getById(updateDeviceDto.getClient());}
        else{
            client = device.getClient();}
        Device d = new Device(updateDeviceDto.getId(),updateDeviceDto.getAddress(), updateDeviceDto.getDescription(), updateDeviceDto.getMaximum_energy(), updateDeviceDto.getAverage(), sensor,client);
        deviceService.createDevice(d);
    }
    @GetMapping("/GetAvailableSensors")
    public ResponseEntity getAvailableSensors(){
        List<ReadSensorDto> sensors = sensorService.getAll();
        List<ReadDeviceDto> devices = deviceService.getAll();
        ArrayList<Long> id = new ArrayList<Long>();
        ArrayList<ReadSensorDto> availableSensors = new ArrayList<ReadSensorDto>();
        for(ReadDeviceDto i: devices){
            id.add(i.getSensor_id());
        }
        for(ReadSensorDto i: sensors){
            if(!id.contains(i.getId())){
                availableSensors.add(i);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(availableSensors);
    }
    @GetMapping("/getDevicesByUser/{username}")
    public ResponseEntity getDevicesByUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.getDevicesByUser(username));
    }

}
