package com.ds.Assignment_1.repository;

import com.ds.Assignment_1.model.Client;
import com.ds.Assignment_1.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device,Long> {
    Device findDeviceById(Long id);
    List<Device> findDeviceByClient(Client client);
    List<Device> findAll();
    Device save(Device device);
    void deleteById(Long id);
}
