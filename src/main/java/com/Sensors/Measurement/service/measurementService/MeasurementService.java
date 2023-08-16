package com.Sensors.Measurement.service.measurementService;


import com.Sensors.Measurement.entity.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeasurementService {

    Measurement saveMeasurement(Measurement measurement);
    List<Measurement> findMeasurementBySensorType(String type);
    Measurement getMeasurementById(Long id);
    Page<Measurement> paginationMeasurement(List<Measurement> list, Pageable p);
    List<Long> findReadingValuesBySensorType(List<Measurement> measurements);
    List<Long> get10MaxValues(List<Long> values);
    List<Long> get10MinValues(List<Long> values);
    double calculateMeanvalue(List<Long> values);
}
