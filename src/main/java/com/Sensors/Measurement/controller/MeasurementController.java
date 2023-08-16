package com.Sensors.Measurement.controller;


import com.Sensors.Measurement.entity.Measurement;
import com.Sensors.Measurement.entity.Sensor;
import com.Sensors.Measurement.service.measurementService.MeasurementService;
import com.Sensors.Measurement.service.sensorService.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private SensorService sensorService;

    @PostMapping("/measurement/add")
    public ResponseEntity<Measurement> createMeasurement(@RequestBody Measurement measurement, @RequestParam Long id) {
        Sensor sensor = sensorService.getSensorById(id);
        measurement.setSensorId(sensor);
        return new ResponseEntity<>(measurementService.saveMeasurement(measurement), HttpStatus.OK);
    }
    @GetMapping("/measurement/type")
    public ResponseEntity<Page<Measurement>> retrieveBySensorType(@RequestParam("type") String type, Pageable p){
        return  ResponseEntity.ok(measurementService.paginationMeasurement(measurementService.findMeasurementBySensorType(type), p));
    }
    @GetMapping("/measurement/location")
    public ResponseEntity<Page<Measurement>> paginateRetrieveBySensorLocation (@RequestParam("location") String location,Pageable p) {
        List<Measurement> measurementsLocationList = sensorService.findSensorIdByLocation(location);
        Page<Measurement> page = new PageImpl<>(measurementsLocationList);
        return ResponseEntity.ok(page);
    }
    @PutMapping("/measurement/modify/{id}")
    public ResponseEntity modifyMeasurement(@PathVariable Long id,@RequestBody Measurement measurement){
            Measurement storedMeasurement = measurementService.getMeasurementById(id);
            if (storedMeasurement == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found");
            }
            else {
                storedMeasurement.setReadingType(measurement.getReadingType());
                storedMeasurement.setReadingValue(measurement.getReadingValue());
                storedMeasurement.setReadingDate(measurement.getReadingDate());
                storedMeasurement.setTime(measurement.getTime());
                measurementService.saveMeasurement(storedMeasurement);
                return ResponseEntity.status(HttpStatus.OK).body("Measurement modifed successfully");
            }
    }
    @GetMapping("/measurement/maxValue")
    public List<Long> get10MaxValuesBySensorType(@RequestParam("type") String type){
       return measurementService.get10MaxValues(measurementService.findReadingValuesBySensorType(measurementService.findMeasurementBySensorType(type)));
    }
    @GetMapping("/measurement/minValue")
    public List<Long> get10MinValuesBySensorType(@RequestParam("type") String type){
        return measurementService.get10MinValues(measurementService.findReadingValuesBySensorType(measurementService.findMeasurementBySensorType(type)));
    }

    @GetMapping("/measurement/meanValue")
    public double getMeanValueBySensorType(@RequestParam("type") String type){
        return measurementService.calculateMeanvalue(measurementService.findReadingValuesBySensorType(measurementService.findMeasurementBySensorType(type)));
    }

}