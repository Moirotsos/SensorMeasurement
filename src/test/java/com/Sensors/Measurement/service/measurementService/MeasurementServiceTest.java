package com.Sensors.Measurement.service.measurementService;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MeasurementServiceTest {

    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private MeasurementRepository measurementRepository;
    @InjectMocks
    private MeasurementServiceImpl measurementService;
    private Measurement measurement;
    private Sensor sensor;
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
        measurement.setReadingDate(null);
        measurement.setTime(null);
    }

    @Test
    void paginationMeasurement() {
        Page<Measurement> listMeasurement = new PageImpl<>(List.of(measurement));
        Page<Measurement> measurementsBytype = measurementService.paginationMeasurement(List.of(measurement), PageRequest.of(0, 10));
        assertEquals(listMeasurement, measurementsBytype);
      }


    @Test
    void saveMeasurement() {
        when(measurementRepository.save(measurement)).thenReturn(null);
        this.measurementService.saveMeasurement(measurement);
    }

    @Test
    void getMeasurementById() {
        //When
        when(measurementRepository.findById(1L)).thenReturn(Optional.of(measurement));
        Measurement returnMeasurement = this.measurementService.getMeasurementById(1L);
        //Then
        assertEquals(measurement, returnMeasurement);
        verify(this.measurementRepository).findById(1L);
    }

    @Test
    void findMeasurementBySensorType() {
        when(measurementRepository.findMeasurementByReadingTypeEquals("Temperature")).thenReturn(List.of(measurement));
        List<Measurement> listMeasurement = measurementService.findMeasurementBySensorType("Temperature");

        assertEquals(1, listMeasurement.size());
        verify(measurementRepository).findMeasurementByReadingTypeEquals("Temperature");

    }

    @Test
    void findReadingValuesBySensorType() {
        List<Measurement> listMeasurement = Arrays.asList(measurement);
        List <Long> readings = measurementService.findReadingValuesBySensorType(listMeasurement);
        assertEquals(54L, readings.get(0));
    }



    @Test
    void get10MaxValues() {
        List<Long> values = new ArrayList<Long>();
        values.add(32L);
        values.add(43L);
        values.add(70L);
        values.add(20L);
        values.add(18L);
        values.add(54L);
        values.add(76L);
        values.add(31L);
        values.add(21L);
        values.add(43L);
        values.add(56L);
        List <Long> max = measurementService.get10MaxValues(values);
        assertEquals(10,max.size());
    }

    @Test
    void get10MinValues() {
        List<Long> values = new ArrayList<Long>();
        values.add(32L);
        values.add(43L);
        values.add(70L);
        values.add(20L);
        values.add(18L);
        values.add(54L);
        values.add(76L);
        values.add(31L);
        values.add(21L);
        values.add(43L);
        values.add(56L);
        List <Long> min = measurementService.get10MinValues(values);
        assertEquals(10,min.size());
    }



}