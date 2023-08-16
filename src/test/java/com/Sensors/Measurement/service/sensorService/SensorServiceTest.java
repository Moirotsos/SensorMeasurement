package com.Sensors.Measurement.service.sensorService;

import com.Sensors.Measurement.Repository.MeasurementRepository;
import com.Sensors.Measurement.Repository.SensorRepository;
import com.Sensors.Measurement.entity.Measurement;
import com.Sensors.Measurement.entity.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;
    @InjectMocks
    private SensorServiceImpl sensorService;

    private Sensor sensor;
    private Measurement measurement;
    @BeforeEach
    public void setup(){
        this.sensor = new Sensor();
        sensor.setSensorId(1L);
        sensor.setType("Temperature Sensor");
        sensor.setVendorEmail("moi@hua.gr");
        sensor.setVendorName("Kostas");
        sensor.setDescription("thermokrasia");
        sensor.setLocation("athens");
        sensor.setPrice("234$");
        this.measurement = new Measurement();
        measurement.setId(1L);
        measurement.setSensorId(sensor);
        measurement.setReadingType("Temperature");
        measurement.setReadingValue(54L);
        List<Measurement> list= Arrays.asList(measurement);
        sensor.setMeasurements(list);
    }


    @Test
    void saveSensor() {
        this.sensorService.saveSensor(sensor);
    }


    @Test
    void findAllSensor() {
        Page <Sensor> mockPage = new PageImpl<>(List.of(sensor));
        when(sensorRepository.findAll(PageRequest.of(0, 10))).thenReturn(mockPage);
        Page<Sensor> sensors = this.sensorService.findAllSensor(PageRequest.of(0, 10));
        //Then
        assertEquals(mockPage, sensors);
        verify(this.sensorRepository).findAll(PageRequest.of(0, 10));
    }


    @Test
    void getSensorById() {
        //When
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        Sensor returnSensor = this.sensorService.getSensorById(1L);
        //Then
        assertEquals(sensor.getSensorId(), returnSensor.getSensorId());
        verify(this.sensorRepository).findById(1L);
    }

    @Test
    void deleteById() {
        this.sensorService.deleteById(1L);
    }

    @Test
    void findSensorIdByLocation() {
        when(sensorRepository.findSensorByLocationEquals("athens")).thenReturn(List.of(sensor));
        List<Measurement> measurements = sensorService.findSensorIdByLocation("athens");
        //Then
        assertEquals(1,measurements.size());
        verify(this.sensorRepository).findSensorByLocationEquals("athens");
    }
}