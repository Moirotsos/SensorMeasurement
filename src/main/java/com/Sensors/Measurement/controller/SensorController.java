package com.Sensors.Measurement.controller;


import com.Sensors.Measurement.entity.Sensor;
import com.Sensors.Measurement.service.sensorService.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SensorController {
    @Autowired
    private SensorService sensorService;
    @PostMapping("/sensor/add")
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor){
        return new ResponseEntity<>(sensorService.saveSensor(sensor), HttpStatus.OK);
    }
    @GetMapping("/sensor/findall")
    public ResponseEntity<Page<Sensor>> findAllSensors(Pageable p){
        return ResponseEntity.ok(sensorService.findAllSensor(p));
    }
    @PutMapping("/sensor/modify/{id}")
    public ResponseEntity modifySensor(@PathVariable Long id, @RequestBody Sensor sensor){

            Sensor storedSensor = sensorService.getSensorById(id);
            if (storedSensor == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found");
            }
            else {
                storedSensor.setType(sensor.getType());
                storedSensor.setVendorName(sensor.getVendorName());
                storedSensor.setVendorEmail(sensor.getVendorEmail());
                storedSensor.setDescription(sensor.getDescription());
                storedSensor.setLocation(sensor.getLocation());
                storedSensor.setPrice(sensor.getPrice());
                sensorService.saveSensor(storedSensor);
                return ResponseEntity.status(HttpStatus.OK).body("Sensor modifed successfully");
            }
    }
    @DeleteMapping("/sensor/delete/{id}")
    public ResponseEntity deleteSensor(@PathVariable Long id){

            sensorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Sensor deleted successfully");

    }
}
