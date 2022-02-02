package com.ds.Assignment_1.repository;

import com.ds.Assignment_1.model.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SensorRepository extends CrudRepository<Sensor,Long>{
    Sensor findSensorById(Long id);
    List<Sensor> findAll();
    Sensor save(Sensor sensor);
    void deleteById(Long id);
}
