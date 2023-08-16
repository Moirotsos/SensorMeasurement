package com.Sensors.Measurement.service.measurementService;


import com.Sensors.Measurement.Repository.MeasurementRepository;
import com.Sensors.Measurement.Repository.SensorRepository;
import com.Sensors.Measurement.entity.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementServiceImpl implements MeasurementService{

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MeasurementRepository measurementRepository;


    @Override
    public Measurement saveMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> findMeasurementBySensorType(String type) {
        return measurementRepository
                .findMeasurementByReadingTypeEquals(type);
    }
    @Override
    public Measurement getMeasurementById(Long id) {
        return measurementRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Measurement> paginationMeasurement(List<Measurement> list, Pageable p) {
        Page <Measurement> page = new PageImpl<>(list);
        return page;
    }

    @Override
    public List<Long> findReadingValuesBySensorType(List<Measurement> measurements) {
        List<Long> values = measurements
                .stream()
                .map(Measurement::getReadingValue)
                .collect(Collectors.toList());

        return values;
    }

    @Override
    public List<Long> get10MaxValues(List<Long> values) {
        List<Long> sortedvalues = values
                .stream()
                .sorted((a, b) -> b.compareTo(a))
                .collect(Collectors.toList());
        return sortedvalues
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> get10MinValues(List<Long> values) {
        List<Long> sortedvalues = values
                .stream()
                .sorted((a, b) -> a.compareTo(b))
                .collect(Collectors.toList());
        return sortedvalues
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }
}
