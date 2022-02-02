package com.ds.Assignment_1.service;

import com.ds.Assignment_1.dto.ReadConsumptionsDto;
import com.ds.Assignment_1.dto.ReadDeviceDto;
import com.ds.Assignment_1.model.Consumption;
import com.ds.Assignment_1.model.Sensor;
import com.ds.Assignment_1.repository.ConsumptionRepository;
import com.ds.Assignment_1.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumptionService {
    @Autowired
    private ConsumptionRepository consumptionRepository;
    @Autowired
    private SensorRepository sensorRepository;

    public void createConsumption(Consumption consumption){
        consumptionRepository.save(consumption);
    }

    public List<ReadConsumptionsDto> getConsumptionsBySensor(Long sensorId){
        Sensor sensor = sensorRepository.findSensorById(sensorId);
        List<Consumption> consumptions = consumptionRepository.getAllBySensor(sensor);

        List<ReadConsumptionsDto> readConsumptionsDtos = consumptions.stream().map(consumption ->
        {
            return new ReadConsumptionsDto(
                    sensor.getId(),
                    consumption.getId(),
                    consumption.getEnergyConsumption(),
                    consumption.getTimestamp()
            );
        }).collect(Collectors.toList());
        return readConsumptionsDtos;
    }
}
