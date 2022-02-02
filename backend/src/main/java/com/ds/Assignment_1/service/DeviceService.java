package com.ds.Assignment_1.service;

import com.ds.Assignment_1.dto.ReadDeviceDto;
import com.ds.Assignment_1.model.*;
import com.ds.Assignment_1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;
    private ClientRepository clientRepository;
    private ActorRepository actorRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository1,
                         ClientRepository clientRepository1,
                         ActorRepository actorRepository1) {
        this.deviceRepository = deviceRepository1;
        this.clientRepository = clientRepository1;
        this.actorRepository = actorRepository1;
    }

    public Device getById(Long id) {
        return deviceRepository.findDeviceById(id);
    }

    public ReadDeviceDto getDeviceById(Long id) {
        Device device = deviceRepository.findDeviceById(id);
        return new ReadDeviceDto(device.getId(), device.getAddress(),device.getDescription(),
                device.getMaximum_energy(),device.getAverage(), device.getSensor().getId(), device.getClient().getId());
    }

    public List<ReadDeviceDto> getAll() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(device ->
        {
            return new ReadDeviceDto(
                    device.getId(),
                    device.getAddress(),
                    device.getDescription(),
                    device.getMaximum_energy(),
                    device.getAverage(),
                    device.getSensor().getId(),
                    device.getClient().getId()
            );
        }).collect(Collectors.toList());
    }

    public void createDevice(Device device) {
        deviceRepository.save(device);
    }

    public void deleteById(Long id) {
        deviceRepository.deleteById(id);
    }

    public List<ReadDeviceDto> getDevicesByUser(String username){
        Actor user = actorRepository.findActorByUsername(username);
        Client client = clientRepository.findClientByUser(user);
        List<Device> devices = deviceRepository.findDeviceByClient(client);

        List<ReadDeviceDto> readDeviceDtos = devices.stream().map(device ->
        {
            return new ReadDeviceDto(
                    device.getId(),
                    device.getAddress(),
                    device.getDescription(),
                    device.getMaximum_energy(),
                    device.getAverage(),
                    device.getSensor().getId(),
                    device.getClient().getId()
            );
        }).collect(Collectors.toList());
        return readDeviceDtos;
    }

}
