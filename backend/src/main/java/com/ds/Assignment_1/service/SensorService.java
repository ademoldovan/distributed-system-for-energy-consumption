package com.ds.Assignment_1.service;

import com.ds.Assignment_1.dto.ReadSensorDto;
import com.ds.Assignment_1.model.Sensor;
import com.ds.Assignment_1.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public Sensor getById(Long id) {
        return sensorRepository.findSensorById(id);
    }

    public List<ReadSensorDto> getAll() {
        List<Sensor> sensors = sensorRepository.findAll();
        List<ReadSensorDto> readSensorDto = sensors.stream().map(sensor ->
        {
            return new ReadSensorDto(
                    sensor.getId(),
                    sensor.getDescription(),
                    sensor.getMaximum_value()
            );
        }).collect(Collectors.toList());
        return readSensorDto;
    }

    public void createSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public void deleteById(Long id) {
        sensorRepository.deleteById(id);
    }
}
