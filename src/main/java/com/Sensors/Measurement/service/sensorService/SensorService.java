package com.Sensors.Measurement.service.sensorService;

import com.Sensors.Measurement.entity.Measurement;
import com.Sensors.Measurement.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SensorService {
    Sensor saveSensor(Sensor sensor);
    Page<Sensor> findAllSensor(Pageable p);
    Sensor getSensorById(Long id);
    void deleteById(Long id);
    List<Measurement> findSensorIdByLocation(String location);


}
