package com.Sensors.Measurement.service.sensorService;

import com.Sensors.Measurement.Repository.SensorRepository;
import com.Sensors.Measurement.entity.Measurement;
import com.Sensors.Measurement.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SensorServiceImpl implements SensorService{

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public Page<Sensor> findAllSensor(Pageable p) {
        return sensorRepository.findAll(p);
    }

    @Override
    public Sensor getSensorById(Long id) {
        return sensorRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public List<Measurement> findSensorIdByLocation(String location) {
        return  sensorRepository
                .findSensorByLocationEquals(location)
                .stream()
                .map(Sensor::getMeasurements)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
