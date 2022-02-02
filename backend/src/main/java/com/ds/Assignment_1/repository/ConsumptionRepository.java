package com.ds.Assignment_1.repository;

import com.ds.Assignment_1.model.Consumption;
import com.ds.Assignment_1.model.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption,Long> {
    Consumption save(Consumption consumption);
    List<Consumption> getAllBySensor(Sensor sensor);
}
