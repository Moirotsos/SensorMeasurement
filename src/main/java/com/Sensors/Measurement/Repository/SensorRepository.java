package com.Sensors.Measurement.Repository;

import com.Sensors.Measurement.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor,Long> {
    List<Sensor> findSensorByLocationEquals(String type);
}
